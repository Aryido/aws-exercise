package com.aryido.common.property;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

/**
 * @author YunYang Lee
 */
@SpringBootTest
public class SSMParameterTest {

	@Autowired
	private SSMParameter parameter;

	@Test
	public void testSSMParameter() {
		System.out.println( parameter.getAryidoS3Parameter() );
		System.out.println( parameter.getAryidoKinesisStreamParameter() );
		Assertions.assertTrue( Objects.nonNull( parameter.getAryidoS3Parameter() ) );
		Assertions.assertTrue( Objects.nonNull( parameter.getAryidoKinesisStreamParameter() ) );
	}
}
