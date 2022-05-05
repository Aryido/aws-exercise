package com.aryido.s3.operator.repository.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.aryido.common.proto.Event.KinesisData;
import com.aryido.s3.operator.repository.IS3Repository;
import com.aryido.s3.operator.util.ParquetUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @author YunYang Lee
 */
@Slf4j
public class S3KinesisDataRepositoryImpl implements IS3Repository<KinesisData> {
	private static final String BUCKET_NAME = "test-kinesis-henry-y-lee-s3";
	private final AmazonS3 s3client;

	public S3KinesisDataRepositoryImpl() {
		this.s3client = AmazonS3ClientBuilder.standard()
				.withRegion( Regions.AP_NORTHEAST_1 )
				.build();
	}

	@Override
	public Optional<byte[]> getDataBy( String uid ) throws IOException {
		if ( !s3client.doesBucketExistV2( BUCKET_NAME ) ) {
			log.error( "There is no bucket {}", BUCKET_NAME );
			return Optional.empty();
		}
		S3Object object = s3client.getObject( new GetObjectRequest( BUCKET_NAME, uid + ".parquet" ) );
		return Optional.of( object.getObjectContent()
				.readAllBytes() );
	}

	@Override
	public void putData( KinesisData data ) throws IOException {
		if ( !s3client.doesBucketExistV2( BUCKET_NAME ) ) {
			log.error( "There is no bucket {}", BUCKET_NAME );
			return;
		}
		File file = ParquetUtils.writeParquetFile( data );
		try (InputStream is = new FileInputStream( file )) {
			s3client.putObject( new PutObjectRequest( BUCKET_NAME, data.getUid() + ".parquet", is, new ObjectMetadata() ) );
			log.info( "Success putting data into bucket {}", BUCKET_NAME );
		} catch (AmazonServiceException ase) {
			log.error( ase.getErrorMessage() );
		} catch (AmazonClientException ace) {
			log.error( ace.getMessage() );
		}
	}
}
