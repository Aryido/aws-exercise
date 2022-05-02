package com.aryido.kinesis.consumer.service.impl;

import com.aryido.common.proto.Message.KinesisData;
import com.aryido.kinesis.consumer.service.IDataOperator;
import com.aryido.s3.operator.repository.IS3Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * @author YunYang Lee
 */
@Slf4j
@Service
public class DataOperatorImpl implements IDataOperator<KinesisData> {
	private final IS3Repository<KinesisData> s3Repository;

	@Autowired
	public DataOperatorImpl( IS3Repository<KinesisData> s3Repository ) {
		this.s3Repository = s3Repository;
	}

	@Override
	public void upload( Message<KinesisData> message ) {
		try {
			KinesisData kinesisData = message.getPayload();
			log.info( "hello, {}, {}.", kinesisData.getUid(), kinesisData.getName() );
			this.s3Repository.putData( kinesisData );
		} catch (Exception e) {
			log.error( e.getMessage() );
		}
	}
}
