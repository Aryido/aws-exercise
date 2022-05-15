package com.aryido.kinesis.consumer.stream;

import com.aryido.common.proto.Event.KinesisData;
import com.aryido.kinesis.consumer.property.S3Properties;
import com.aryido.kinesis.consumer.service.IDataOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

/**
 * @author YunYang Lee
 */
@Slf4j
@Configuration
public class KinesisStreamBinding {
	private final IDataOperator<KinesisData> dataOperator;
	private final S3Properties s3Properties;

	@Autowired
	public KinesisStreamBinding( IDataOperator<KinesisData> dataOperator, S3Properties s3Properties ) {
		this.dataOperator = dataOperator;
		this.s3Properties = s3Properties;
	}

	@Bean
	public Consumer<Message<byte[]>> responseData() {
		return message -> {
			log.info( "Header: {}", message.getHeaders() );
			dataOperator.upload( s3Properties.getBucketName(), message.getPayload() );
		};
	}

}
