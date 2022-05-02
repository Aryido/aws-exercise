package com.aryido.kinesis.consumer.service.impl;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.aryido.common.proto.Message.KinesisData;
import com.aryido.kinesis.consumer.service.IDataOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * @author YunYang Lee
 */
@Slf4j
@Service
public class DataOperatorImpl implements IDataOperator<KinesisData> {
	private static final String BUCKET_NAME = "test-kinesis-henry-y-lee-s3";
	private final AmazonS3 s3client;

	@Autowired
	public DataOperatorImpl( AmazonS3 s3client ) {
		this.s3client = AmazonS3ClientBuilder.standard()
				.withRegion( Regions.AP_NORTHEAST_1 )
				.build();
	}

	@Override
	public void upload( Message<KinesisData> message ) {
		try {
			KinesisData kinesisData = message.getPayload() ;
			log.info( "hello, {}, {}.", kinesisData.getUid(), kinesisData.getName() );
			this.update( kinesisData );
		} catch (Exception e) {
			log.error( e.getMessage() );
		}
	}

	private void update( KinesisData kinesisData ) {
		if ( s3client.doesBucketExistV2( BUCKET_NAME ) ) {
			log.info( "write into s3." );
		}
	}
}
