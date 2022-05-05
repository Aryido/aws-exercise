package repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.proto.ProtoParquetWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

/**
 * @author YunYang Lee
 */
@Slf4j
public class S3KinesisDataRepositoryTest {

	@Test
	public void testWriteParquet(){
		KinesisData.Builder builder = KinesisData.newBuilder();
		KinesisData data = builder.setUid( UUID.randomUUID()
						.toString() )
				.setName( "henry" )
				.build();
		System.out.println(writeParquet( data ));
	}

	private Path writeParquet( KinesisData data ) {
		int pageSize = 4 * 1024 * 1024;
		Path filePath = new Path( UUID.randomUUID() + ".gzip" );
		try (ProtoParquetWriter<KinesisData> writer = new ProtoParquetWriter<>( filePath, KinesisData.class,
				CompressionCodecName.GZIP, 32 * pageSize, pageSize )) {
			writer.write( data );
			return filePath;
		} catch (IOException e) {
			log.error( "write data {} parquet file error ", data.getUid() );
			return filePath;
		}
	}
}
