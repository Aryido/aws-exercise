package com.aryido.kinesis.consumer.stream;

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
	@Bean
	public Consumer<String> responseData() {
		return name -> {
			log.info( "hello, {}.", name );
		};
	}
}
