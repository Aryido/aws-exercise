package com.aryido.s3.operator.repository;

/**
 * @author YunYang Lee
 */
public interface IS3Repository<T> {
	void putData(T data);
}
