package com.aryido.s3.operator.repository.impl;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.aryido.common.proto.Message.KinesisData;
import com.aryido.s3.operator.repository.IS3Repository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author YunYang Lee
 */
@Slf4j
public class S3KinesisDataRepositoryImpl implements IS3Repository<KinesisData> {
	private static final String BUCKET_NAME = "test-kinesis-henry-y-lee-s3";
	private final AmazonS3 s3client;

	public S3KinesisDataRepositoryImpl( AmazonS3 s3client ) {
		this.s3client = AmazonS3ClientBuilder.standard()
				.withRegion( Regions.AP_NORTHEAST_1 )
				.build();
	}

	@Override
	public void putData( KinesisData data ) {
		if ( s3client.doesBucketExistV2( BUCKET_NAME ) ) {
			log.info( "write into s3." );
		}
	}
}
