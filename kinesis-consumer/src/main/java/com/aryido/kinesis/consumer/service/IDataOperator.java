package com.aryido.kinesis.consumer.service;

/**
 * @author YunYang Lee
 */
public interface IDataOperator<T> {
	void upload( byte[] bytes );
}
