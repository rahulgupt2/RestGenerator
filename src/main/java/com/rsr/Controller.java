package com.rsr;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rsr.domain.Request;
import com.rsr.service.CodeWriter;
import com.rsr.service.CodeWriterServiceImpl;

@RestController
public class Controller {

	@Autowired
	TableDaoImpl tableDaoImpl;
	
	@Autowired
	CodeWriterServiceImpl writerServiceImpl;
		
	@PostMapping(path = "/run", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Request> register(@RequestBody final Request request) throws IOException, SQLException {

		request.validateRequest(tableDaoImpl);
		request.enrichRequest(tableDaoImpl);
		
		CopyProjectUtils.copyProjectTemplate(request.getProjectname());
		
		writerServiceImpl.writeCode(request);
		
		return new ResponseEntity<Request>(request, HttpStatus.OK);
	}

	

}
