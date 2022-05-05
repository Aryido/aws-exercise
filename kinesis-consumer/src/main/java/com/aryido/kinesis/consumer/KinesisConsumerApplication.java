package com.aryido.kinesis.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KinesisConsumerApplication {
	public static void main( String[] args ) {
		SpringApplication.run( KinesisConsumerApplication.class, args );
	}
}
