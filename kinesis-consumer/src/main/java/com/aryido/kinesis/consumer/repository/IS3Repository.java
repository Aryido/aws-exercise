package com.aryido.kinesis.consumer.repository;

/**
 * @author YunYang Lee
 */
public interface IS3Repository<T> {
	void putData(T data);
}
