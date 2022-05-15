package com.aryido.s3.operator.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.aryido.common.proto.Event.KinesisData;
import com.aryido.s3.operator.service.IS3Service;
import com.aryido.s3.operator.util.ParquetFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.Path;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

/**
 * @author YunYang Lee
 */
@Slf4j
public class S3KinesisDataServiceImpl implements IS3Service<KinesisData> {
	private final AmazonS3 s3client;

	public S3KinesisDataServiceImpl( AmazonS3 s3client ) {
		this.s3client = s3client;
	}

	@Override
	public Optional<KinesisData> getDataBy( String bucketName, String uid ) throws IOException {
		if ( !s3client.doesBucketExistV2( bucketName ) ) {
			log.error( "There is no bucket {}", bucketName );
			return Optional.empty();
		}
		S3Object object = s3client.getObject( new GetObjectRequest( bucketName, uid + ".parquet" ) );
		try (InputStream ip = object.getObjectContent()) {
			java.nio.file.Path writtenPath = Files.write( Files.createTempFile( uid, ".pb" ), ip.readAllBytes() );
			return Optional.of( ParquetFileUtils.readParquetFile( new Path( writtenPath.toUri() ) ) );
		}
	}

	@Override
	public void putData( String bucketName, KinesisData data ) throws IOException {
		if ( !s3client.doesBucketExistV2( bucketName ) ) {
			log.error( "There is no bucket {}", bucketName );
			return;
		}
		java.nio.file.Path tempFilePath = Files.createTempFile( data.getUid(), ".parquet" );
		Path hadoopPath = new Path( tempFilePath.toUri() );
		Path path = ParquetFileUtils.writeParquetFile( hadoopPath, data );
		try (InputStream is = new FileInputStream( path.toUri()
				.getPath() )) {
			s3client.putObject(
					new PutObjectRequest( bucketName, data.getUid() + ".parquet", is, new ObjectMetadata() ) );
			log.info( "Success putting data into bucket {}", bucketName );
		} catch (AmazonServiceException ase) {
			log.error( ase.getErrorMessage() );
		} catch (AmazonClientException ace) {
			log.error( ace.getMessage() );
		}
	}
}
