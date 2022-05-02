package com.aryido.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;

/**
 * @author YunYang Lee
 */
@Configuration
public class ProtocolBuffersSpringCloudStreamAutoConfiguration {
	@Bean
	public MessageConverter protobufMessageConverter() {
		return new ProtobufMessageConverter();
	}
}