package com.aryido.s3.operator.repository.config;

import com.amazonaws.services.s3.AmazonS3;
import com.aryido.s3.operator.repository.IS3Repository;
import com.aryido.s3.operator.repository.impl.S3KinesisDataRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.aryido.common.proto.Message.KinesisData;

/**
 * @author YunYang Lee
 */
@Configuration
public class S3OperatorAutoConfiguration {
	private final AmazonS3 s3client;
	@Autowired
	public S3OperatorAutoConfiguration(AmazonS3 s3client ) {
		this.s3client = s3client;
	}

	@Bean
	public IS3Repository<KinesisData> s3KinesisDataRepository() {
		return new S3KinesisDataRepositoryImpl( this.s3client );
	}
}
