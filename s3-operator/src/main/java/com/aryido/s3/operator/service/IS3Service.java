package com.aryido.s3.operator.service;

import java.io.IOException;
import java.util.Optional;

/**
 * @author YunYang Lee
 */
public interface IS3Service<T> {

	Optional<T> getDataBy( String bucketName, String uid ) throws IOException;

	void putData( String bucketName, T data ) throws IOException;
}
