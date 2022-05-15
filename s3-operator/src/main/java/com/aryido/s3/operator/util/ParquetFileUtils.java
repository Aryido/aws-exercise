package com.aryido.s3.operator.util;

import com.aryido.common.proto.Event;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.proto.ProtoParquetReader;
import org.apache.parquet.proto.ProtoParquetWriter;

import java.io.IOException;

/**
 * @author YunYang Lee
 */
@Slf4j
public class ParquetFileUtils {

	public static Path writeParquetFile( Path hadoopPath, Event.KinesisData data ) throws IOException {
		log.info( "start to write parquet file by uid {}", data.getUid() );
		try (ParquetWriter writer = ProtoParquetWriter.builder( hadoopPath )
				.withWriteMode( ParquetFileWriter.Mode.OVERWRITE )
				.withCompressionCodec( CompressionCodecName.SNAPPY )
				.withPageSize( ParquetWriter.DEFAULT_PAGE_SIZE )
				.withMessage( data.getClass() )
				.build();) {
			writer.write( data );
		}
		return hadoopPath;
	}

	public static Event.KinesisData readParquetFile( Path hadoopPath ) throws IOException {
		log.info( "start to read parquet file by path {}", hadoopPath.getName() );
		try (ParquetReader reader = ProtoParquetReader.builder( hadoopPath )
				.build();) {
			Event.KinesisData.Builder builder = (Event.KinesisData.Builder) reader.read();
			return builder.build();
		}
	}
}
