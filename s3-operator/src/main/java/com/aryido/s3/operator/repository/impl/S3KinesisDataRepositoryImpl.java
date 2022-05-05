package com.aryido.s3.operator.repository.impl;

import com.aryido.common.proto.Event.KinesisData;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.aryido.s3.operator.repository.IS3Repository;
import com.aryido.s3.operator.util.ParquetUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.parquet.proto.ProtoSchemaConverter;
import org.apache.parquet.schema.MessageType;

import java.io.File;
import java.io.IOException;
import java.net.URL;


/**
 * @author YunYang Lee
 */
@Slf4j
public class S3KinesisDataRepositoryImpl implements IS3Repository<KinesisData> {
    private static final String BUCKET_NAME = "test-kinesis-henry-y-lee-trendmicro-s3";
    private final AmazonS3 s3client;

    public S3KinesisDataRepositoryImpl(AmazonS3 s3client) {
        this.s3client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_1)
                .build();
    }

    @Override
    public byte[] getDataBy(String uid) throws IOException {
        if (s3client.doesBucketExistV2(BUCKET_NAME)) {
            S3Object object = s3client.getObject(new GetObjectRequest(BUCKET_NAME+"/upload", uid));
            System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
            return object.getObjectContent().readAllBytes();
        }
        return new byte[0];
    }

    @Override
    public void putData(KinesisData data) throws IOException {
        if (s3client.doesBucketExistV2(BUCKET_NAME)) {
            log.info("write into s3.");

            ProtoSchemaConverter protoSchemaConverter = new ProtoSchemaConverter(true);
            MessageType schema = protoSchemaConverter.convert(KinesisData.class);
            log.info(schema.toString());

            //todo
            File file = ParquetUtils.writeParquetFile(data);

            try {
                String bucketPath = BUCKET_NAME + "/upload";
                s3client.putObject(new PutObjectRequest(bucketPath, data.getUid() + ".parquet", file));
                GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME, data.getUid() + ".parquet");
                URL url = s3client.generatePresignedUrl(urlRequest);
                log.info(url.toString());
            } catch (AmazonServiceException ase) {
                log.error(ase.getErrorMessage());
            } catch (AmazonClientException ace) {
                log.error(ace.getMessage());
            }

            byte[] dataBy = this.getDataBy("bd26b854-7b7e-4979-aacd-c9505dc66af3.parquet");
            KinesisData kinesisData = KinesisData.parseFrom(dataBy);
            log.info( "hello, {}, {}.", kinesisData.getUid(), kinesisData.getName() );

        }
    }
}
