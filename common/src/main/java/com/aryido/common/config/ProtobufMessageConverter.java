package com.aryido.common.config;

import com.google.protobuf.AbstractMessageLite;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

/**
 * @author YunYang Lee
 */
public class ProtobufMessageConverter extends AbstractMessageConverter {

	public ProtobufMessageConverter() {
		super( new MimeType( "application", "x-protobuf" ) );
	}

	@Override
	protected boolean supports( Class<?> clazz ) {
		return AbstractMessageLite.class.isAssignableFrom( clazz );
	}

	@Override
	protected Object convertToInternal( Object payload, MessageHeaders headers, Object conversionHint ) {
		return ((AbstractMessageLite) payload).toByteArray();
	}

	@Override
	protected Object convertFromInternal( Message<?> message, Class<?> targetClass, Object conversionHint ) {
		Object payload = message.getPayload();
		try {
			return com.aryido.common.proto.Message.KinesisData.parseFrom( (byte[]) payload );
		} catch (com.google.protobuf.InvalidProtocolBufferException e) {
			logger.warn( e.getUnfinishedMessage() );
			return payload;
		}
	}
}
