package com.aryido.kinesis.consumer.service.impl;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.aryido.common.proto.KinesisData;
import com.aryido.kinesis.consumer.service.IDataOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

/**
 * @author YunYang Lee
 */
@Slf4j
@Service
public class DataOperatorImpl implements IDataOperator {
	private static final String BUCKET_NAME = "test-kinesis-henry-y-lee-s3";
	private final AmazonS3 s3client;

	public DataOperatorImpl( AmazonS3 s3client ) {
		this.s3client = AmazonS3ClientBuilder.standard()
				.withRegion( Regions.AP_NORTHEAST_1 )
				.build();
	}

	@Override
	public void update( byte[] bytes ) {
		try {
			KinesisData.kinesisData kinesisData = KinesisData.kinesisData.parseFrom( bytes );
			log.info( "hello, {}, {}.", kinesisData.getUid(), kinesisData.getName() );
			this.update( kinesisData );
		} catch (Exception e) {
			log.error( e.getMessage() );
		}
	}

	private void update( KinesisData.kinesisData kinesisData ) {
		if ( s3client.doesBucketExistV2( BUCKET_NAME ) ) {
			s3client.putObject( BUCKET_NAME, kinesisData.getUid(),
					new ByteArrayInputStream( kinesisData.toByteArray() ), new ObjectMetadata() );
		}
	}
}
