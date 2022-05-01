package com.aryido.kinesis.producer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
public class StreamController {
	private final StreamBridge bridge;

	@Autowired
	public StreamController( StreamBridge bridge ) {
		this.bridge = bridge;
	}

	@GetMapping( "/send/{name}" )
	public ResponseEntity<?> delegateToSource( @PathVariable String name ) {
		UUID id = UUID.randomUUID();

		this.bridge.send( "test-kinesis-henry-y-lee-stream", name );
		return ResponseEntity.ok( name );
	}
}
