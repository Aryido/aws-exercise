package com.aryido.kinesis.producer.service.impl;

import com.aryido.common.proto.Message.KinesisData;
import com.aryido.kinesis.producer.service.IProtobufConvertorService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author YunYang Lee
 */
@Service
public class ProtobufConvertorServiceImpl implements IProtobufConvertorService<KinesisData> {
	@Override
	public KinesisData convert( String name ) {
		KinesisData.Builder builder = KinesisData.newBuilder();
		return builder.setUid( UUID.randomUUID().toString() )
				.setName( name )
				.build();
	}
}
