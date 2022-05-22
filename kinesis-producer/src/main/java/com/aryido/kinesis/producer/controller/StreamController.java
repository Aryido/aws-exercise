package com.aryido.kinesis.producer.controller;

import com.aryido.common.property.SSMParameter;
import com.aryido.common.proto.Event.KinesisData;
import com.aryido.common.util.ProtobufUtils;
import com.aryido.kinesis.producer.service.IProtobufConvertorService;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class StreamController {
	private final SSMParameter parameter;
	private final StreamBridge bridge;
	private final IProtobufConvertorService<KinesisData> protoBufConvertorService;

	@Autowired
	public StreamController( SSMParameter parameter, StreamBridge bridge,
			IProtobufConvertorService<KinesisData> protoBufConvertorService ) {
		this.parameter = parameter;
		this.bridge = bridge;
		this.protoBufConvertorService = protoBufConvertorService;
	}

	@GetMapping( "/send/{name}" )
	public ResponseEntity<?> delegateToSource( @PathVariable String name ) throws InvalidProtocolBufferException {
		log.info("stream name is {}.", parameter.getAryidoKinesisStreamParameter() );
		KinesisData data = protoBufConvertorService.convert( name );
		this.bridge.send( parameter.getAryidoKinesisStreamParameter(), data.toByteArray() );
		return ResponseEntity.ok( ProtobufUtils.convert( data ) );
	}
}
