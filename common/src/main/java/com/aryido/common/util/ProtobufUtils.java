package com.aryido.common.util;

import com.aryido.common.proto.Event;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

/**
 * @author YunYang Lee
 */
public class ProtobufUtils {

	public static String convert( Event.KinesisData data ) throws InvalidProtocolBufferException {
		return JsonFormat.printer().print( data );
	}
}
