package com.aryido.kinesis.consumer.stream;

import com.aryido.kinesis.consumer.service.IDataOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

/**
 * @author YunYang Lee
 */
@Slf4j
@Configuration
public class KinesisStreamBinding {
	private final IDataOperator dataOperator;

	public KinesisStreamBinding( IDataOperator dataOperator ) {
		this.dataOperator = dataOperator;
	}

	@Bean
	public Consumer<byte[]> responseData() {
		return dataOperator::update;
	}

}
