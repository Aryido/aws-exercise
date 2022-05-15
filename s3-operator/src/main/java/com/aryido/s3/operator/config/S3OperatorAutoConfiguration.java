package com.aryido.s3.operator.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.aryido.common.proto.Event.KinesisData;
import com.aryido.s3.operator.service.IS3Service;
import com.aryido.s3.operator.service.impl.S3KinesisDataServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YunYang Lee
 */
@Configuration
public class S3OperatorAutoConfiguration {

	@Bean
	public IS3Service<KinesisData> s3KinesisDataService() {
		return new S3KinesisDataServiceImpl( AmazonS3ClientBuilder.standard()
				.withRegion( Regions.AP_NORTHEAST_1 )
				.build() );
	}
}