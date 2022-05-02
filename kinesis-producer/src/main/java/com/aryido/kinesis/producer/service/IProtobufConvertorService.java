package com.aryido.kinesis.producer.service;

/**
 * @author YunYang Lee
 */
public interface IProtobufConvertorService<T> {
	T convert( String name );
}
