package com.aryido.kinesis.consumer.repository.impl;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.aryido.common.proto.Message;
import com.aryido.kinesis.consumer.repository.IS3Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author YunYang Lee
 */
@Slf4j
@Service
public class S3RepositoryImpl implements IS3Repository<Message.KinesisData> {
	private static final String BUCKET_NAME = "test-kinesis-henry-y-lee-s3";

	private final AmazonS3 s3client;

	@Autowired
	public S3RepositoryImpl( AmazonS3 s3client ) {
		this.s3client = AmazonS3ClientBuilder.standard()
				.withRegion( Regions.AP_NORTHEAST_1 )
				.build();
	}

	@Override
	public void putData( Message.KinesisData data ) {
		if ( s3client.doesBucketExistV2( BUCKET_NAME ) ) {
			log.info( "write into s3." );
		}
	}
}
