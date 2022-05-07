package com.aryido.parquet.reader.controller;

import com.aryido.common.proto.Event.KinesisData;
import com.aryido.s3.operator.repository.IS3Repository;
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
	private final IS3Repository<KinesisData> repository;

	@Autowired
	public DataObserverController( IS3Repository<KinesisData> repository ) {
		this.repository = repository;
	}

	@GetMapping( "/s3-data/{uid}" )
	public ResponseEntity<?> findData( @PathVariable String uid ) throws IOException {
		Optional<byte[]> optionalBytes = repository.getDataBy( uid );
		if ( optionalBytes.isPresent() ) {
			byte[] bytes = optionalBytes.get();
			KinesisData kinesisData = KinesisData.parseFrom( bytes );
			return ResponseEntity.ok( kinesisData );
		} else {
			return ResponseEntity.ok( "not find " + uid + " in bucket." );
		}
	}
}
