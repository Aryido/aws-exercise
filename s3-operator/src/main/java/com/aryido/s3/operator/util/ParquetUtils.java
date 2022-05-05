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

	//    public static Path writeParquetFile(Message.KinesisData data) {
	//        List<Descriptors.FieldDescriptor> fields = data.getDescriptorForType().getFields();
	//        //File file = FileUtils.getFile(FileUtils.getTempDirectory(), data.getUid()+".parquet");
	//        File file = FileUtils.getFile(data.getUid()+".parquet");
	//        log.info(file.toString());
	//
	//        fields.forEach(fieldDescriptor -> {
	//            com.google.protobuf.Message dataField = (com.google.protobuf.Message) data.getField(fieldDescriptor);
	//            ProtoWriteSupport<com.google.protobuf.Message> protoWriteSupport = new ProtoWriteSupport<>(Message.KinesisData.class);
	//
	//            org.apache.hadoop.fs.Path hoopPath = new org.apache.hadoop.fs.Path(file.toURI());
	//            try (ParquetWriter<com.google.protobuf.Message> w = new ParquetWriter<com.google.protobuf.Message>(hoopPath, protoWriteSupport,
	//                    CompressionCodecName.SNAPPY, ParquetWriter.DEFAULT_BLOCK_SIZE, ParquetWriter.DEFAULT_PAGE_SIZE);) {
	//                w.write(dataField);
	//            } catch (IOException e) {
	//                throw new RuntimeException(e);
	//            }
	//        });
	//        return file.toPath();
	//    }

	//todo
	public static File writeParquetFile( KinesisData data ) {
		ProtoSchemaConverter protoSchemaConverter = new ProtoSchemaConverter( true );
		MessageType schema = protoSchemaConverter.convert( KinesisData.class );
		log.info( "schema: {}", schema.toString() );
		File tempDirectory = FileUtils.getTempDirectory();
		File filePath = FileUtils.getFile( tempDirectory, data.getUid() + ".parquet" );
		try {
			log.info( "start to write parquet file {}.", data.getUid() );
			FileUtils.writeByteArrayToFile( filePath, data.toByteArray() );
		} catch (IOException ioException) {
			log.error( "Failed to Write parquet file {}.", data.getUid() );
		}
		return tempDirectory;
	}

	//todo
	public static void readParquetFile( byte[] bytes ) throws InvalidProtocolBufferException {
		KinesisData kinesisData = KinesisData.parseFrom( bytes );
		log.info( "hello, {}, {}.", kinesisData.getUid(), kinesisData.getName() );
	}
}
