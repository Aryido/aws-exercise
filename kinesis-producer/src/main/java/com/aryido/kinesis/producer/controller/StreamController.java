package com.aryido.kinesis.producer.controller;

import com.aryido.common.proto.Message.KinesisData;
import com.aryido.kinesis.producer.service.IProtobufConvertorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class StreamController {
	private static final String BINDING_NAME = "test-kinesis-henry-y-lee-stream";
	private static final MimeType MIME_TYPE = MimeType.valueOf( "application/x-protobuf" );

	private final StreamBridge bridge;
	private final IProtobufConvertorService<KinesisData> protoBufConvertorService;

	@Autowired
	public StreamController( StreamBridge bridge, IProtobufConvertorService<KinesisData> protoBufConvertorService ) {
		this.bridge = bridge;
		this.protoBufConvertorService = protoBufConvertorService;
	}

	@GetMapping( "/send/{name}" )
	public ResponseEntity<?> delegateToSource( @PathVariable String name ) {
		KinesisData data = protoBufConvertorService.convert( name );
		this.bridge.send( BINDING_NAME, data, MIME_TYPE );
		return ResponseEntity.ok( name );
	}
}
