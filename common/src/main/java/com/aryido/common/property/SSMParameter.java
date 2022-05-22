package com.aryido.common.property;

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
@ConfigurationProperties
public class SSMParameter {
	private String AryidoKinesisStreamParameter;
	private String AryidoS3Parameter;
}
