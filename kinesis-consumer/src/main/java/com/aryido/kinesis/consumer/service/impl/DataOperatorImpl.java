package com.aryido.kinesis.consumer.service.impl;

import com.aryido.common.proto.Event.KinesisData;
import com.aryido.kinesis.consumer.service.IDataOperator;
import com.aryido.s3.operator.service.IS3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author YunYang Lee
 */
@Slf4j
@Service
public class DataOperatorImpl implements IDataOperator<KinesisData> {
	private final IS3Service<KinesisData> kinesisDataIS3Service;

	@Autowired
	public DataOperatorImpl( IS3Service<KinesisData> s3Repository ) {
		this.kinesisDataIS3Service = s3Repository;
	}

	@Override
	public void upload( String bucketName, byte[] bytes ) {
		try {
			KinesisData kinesisData = KinesisData.parseFrom( bytes );
			log.info( "hello, {}, {}.", kinesisData.getUid(), kinesisData.getName() );
			this.kinesisDataIS3Service.putData( bucketName, kinesisData );
		} catch (Exception e) {
			log.error( e.getMessage() );
		}
	}
}
