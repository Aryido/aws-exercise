package com.aryido.kinesis.consumer.stream;

import com.aryido.common.property.SSMParameter;
import com.aryido.common.proto.Event.KinesisData;
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
	private final SSMParameter ssmParameter;

	@Autowired
	public KinesisStreamBinding( IDataOperator<KinesisData> dataOperator, SSMParameter ssmParameter ) {
		this.dataOperator = dataOperator;
		this.ssmParameter = ssmParameter;
	}

	@Bean
	public Consumer<Message<byte[]>> responseData() {
		return message -> {
			log.info( "Header: {}", message.getHeaders() );
			dataOperator.upload( ssmParameter.getAryidoS3Parameter(), message.getPayload() );
		};
	}
}
