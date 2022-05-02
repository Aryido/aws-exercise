package com.aryido.kinesis.consumer.service;

import org.springframework.messaging.Message;

/**
 * @author YunYang Lee
 */
public interface IDataOperator<T> {
	void upload( Message<T> message );
}
