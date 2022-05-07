package com.aryido.s3.operator.util;

import com.aryido.common.proto.Event.KinesisData;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.parquet.proto.ProtoSchemaConverter;
import org.apache.parquet.schema.MessageType;

import java.io.File;
import java.io.IOException;

@Slf4j
public class ParquetUtils {
	
	//todo
	public static File writeParquetFile( KinesisData data ) {
		ProtoSchemaConverter protoSchemaConverter = new ProtoSchemaConverter( true );
		MessageType schema = protoSchemaConverter.convert( KinesisData.class );
		log.info( "schema: {}", schema.toString() );
		File filePath = FileUtils.getFile( FileUtils.getTempDirectory(), data.getUid() + ".parquet" );
		try {
			log.info( "start to write parquet file {}.", data.getUid() );
			FileUtils.writeByteArrayToFile( filePath, data.toByteArray() );
		} catch (IOException ioException) {
			log.error( "Failed to Write parquet file {}.", data.getUid() );
		}
		return filePath;
	}

	//todo
	public static void readParquetFile( byte[] bytes ) throws InvalidProtocolBufferException {
		KinesisData kinesisData = KinesisData.parseFrom( bytes );
		log.info( "hello, {}, {}.", kinesisData.getUid(), kinesisData.getName() );
	}
}
