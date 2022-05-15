package com.aryido.s3.operator.service;

import com.aryido.common.proto.Event;
import com.aryido.s3.operator.util.ParquetFileUtils;
import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

/**
 * @author YunYang Lee
 */
public class ParquetFIleServiceTest {

	@Test
	public void testParquetFile() throws IOException {
		Event.KinesisData.Builder builder = Event.KinesisData.newBuilder();
		Event.KinesisData data = builder.setUid( UUID.randomUUID().toString() )
				.setName( "henry" )
				.build();

		Path hadoopPath = new Path( "./src/test/resources/kinesisData.parquet" );
		ParquetFileUtils.writeParquetFile( hadoopPath, data );
		Event.KinesisData kinesisData = ParquetFileUtils.readParquetFile( hadoopPath );
		Assertions.assertEquals( "henry",  kinesisData.getName());
	}
}
