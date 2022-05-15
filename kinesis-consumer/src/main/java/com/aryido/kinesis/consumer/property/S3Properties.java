package com.aryido.kinesis.consumer.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YunYang Lee
 */
@Getter
@Setter
@Component
@ConfigurationProperties( prefix = "aryido.s3" )
public class S3Properties {
	private String bucketName;
}
