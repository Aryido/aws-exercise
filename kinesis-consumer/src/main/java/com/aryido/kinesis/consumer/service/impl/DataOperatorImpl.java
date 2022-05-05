package com.aryido.kinesis.consumer.service.impl;

import com.aryido.common.proto.Event.KinesisData;
import com.aryido.kinesis.consumer.service.IDataOperator;
import com.aryido.s3.operator.repository.IS3Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
	public void upload( byte[] bytes ) {
		try {
			KinesisData kinesisData = KinesisData.parseFrom( bytes );
			log.info( "hello, {}, {}.", kinesisData.getUid(), kinesisData.getName() );
			this.s3Repository.putData( kinesisData );
		} catch (Exception e) {
			log.error( e.getMessage() );
		}
	}
}
