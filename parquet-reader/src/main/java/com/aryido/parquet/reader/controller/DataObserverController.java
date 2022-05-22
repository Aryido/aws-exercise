package com.aryido.parquet.reader.controller;

import com.aryido.common.proto.Event.KinesisData;
import com.aryido.common.util.ProtobufUtils;
import com.aryido.s3.operator.service.IS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

/**
 * @author YunYang Lee
 */
@RestController
public class DataObserverController {
	private final IS3Service<KinesisData> service;

	@Autowired
	public DataObserverController( IS3Service<KinesisData> service ) {
		this.service = service;
	}

	@GetMapping( "/s3-data/{bucketName}/{uid}" )
	public ResponseEntity<?> findData( @PathVariable String bucketName, @PathVariable String uid ) throws IOException {
		Optional<KinesisData> optionalKinesisData = service.getDataBy( bucketName, uid );
		if ( optionalKinesisData.isPresent() ) {
			KinesisData kinesisData = optionalKinesisData.get();
			return ResponseEntity.ok( ProtobufUtils.convert( kinesisData ) );
		} else {
			return ResponseEntity.ok( "not find " + uid + " in bucket." );
		}
	}
}