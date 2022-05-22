package com.aryido.common.proto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.UUID;

/**
 * @author YunYang Lee
 */
public class EventTest {

	@Test
	public void testEvent() throws InvalidProtocolBufferException {
		Event.KinesisData.Builder builder = Event.KinesisData.newBuilder();
		Event.KinesisData data = builder.setUid( UUID.randomUUID().toString() )
				.setName( "henry" )
				.build();
		String jsonString = JsonFormat.printer().print( data );
		JsonObject jsonObject = JsonParser.parseString( jsonString ).getAsJsonObject();

		Assertions.assertTrue( Objects.nonNull( jsonObject.get( "uid" ) ) );
		Assertions.assertEquals( "henry", jsonObject.get( "name" ).getAsString() );
	}
}
