package com.aryido.kinesis.consumer.stream;

import com.aryido.common.proto.Message.KinesisData;
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

	@Autowired
	public KinesisStreamBinding( IDataOperator<KinesisData> dataOperator ) {
		this.dataOperator = dataOperator;
	}

	@Bean
	public Consumer<Message<byte[]>> responseData() {
		return message ->{
			log.info(message.getHeaders().toString());
			dataOperator.upload(message.getPayload());
		};
	}

}
