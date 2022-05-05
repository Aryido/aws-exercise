package com.aryido.s3.operator.repository;

import java.io.IOException;
import java.util.Optional;

/**
 * @author YunYang Lee
 */
public interface IS3Repository<T> {

	Optional<byte[]> getDataBy( String uid ) throws IOException;

	void putData( T data ) throws IOException;
}
