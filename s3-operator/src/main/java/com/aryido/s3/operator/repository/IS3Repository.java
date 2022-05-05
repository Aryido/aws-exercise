package com.aryido.s3.operator.repository;

import com.google.protobuf.InvalidProtocolBufferException;

import java.io.IOException;

/**
 * @author YunYang Lee
 */
public interface IS3Repository<T> {

	byte[] getDataBy(String uid) throws IOException;
	void putData(T data) throws IOException;
}
