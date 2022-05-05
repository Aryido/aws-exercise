package com.aryido.s3.operator.config;

import com.aryido.common.proto.Event.KinesisData;
import com.aryido.s3.operator.repository.IS3Repository;
import com.aryido.s3.operator.repository.impl.S3KinesisDataRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YunYang Lee
 */
@Configuration
public class S3OperatorAutoConfiguration {
	@Bean
	public IS3Repository<KinesisData> s3KinesisDataRepository() {
		return new S3KinesisDataRepositoryImpl( );
	}
}
