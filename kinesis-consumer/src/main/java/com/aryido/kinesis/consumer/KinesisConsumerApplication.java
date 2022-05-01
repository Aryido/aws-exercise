package com.aryido.kinesis.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
public class KinesisConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(KinesisConsumerApplication.class, args);
	}
}
