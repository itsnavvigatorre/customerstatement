package com.kundu.customerstatement.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kundu.customerstatement.exception.BusinessException;
import com.kundu.customerstatement.model.CustomerStatementValidationResult;
import com.kundu.customerstatement.service.CustomerStatementValidatorService;
import com.kundu.customerstatement.util.CustomerStatementContentType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/asyncvalidate")
@Api(value = "Operations to be able to validate the Customer Statements asynchronous for long running proccesses.", description = "Operations to be able to validate the Customer Statements asynchronous for long running proccesses.")
public class CustomerStatementAsyncValidatorController {

	private CustomerStatementValidatorService customerStatementValidatorService;

	@Autowired
	public CustomerStatementAsyncValidatorController(CustomerStatementValidatorService customerStatementValidatorService) {
		this.customerStatementValidatorService = customerStatementValidatorService;
	}

	/**
	 * This method is used to validate of the given customer statements asynchronous for long running proccesses.
	 * 
	 * @param fileCustomerStatements
	 *            The file contains customer statements
	 * @return The results of the validation
	 * @throws BusinessException
	 *             Throws BusinessException in case of invalid input or content
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.POST)
	@Async("taskExecutor")
	@ApiOperation(value = "This method is used to validate of the given customer statements asynchronous for long running proccesses. File type could be cvs or xml.", response = CustomerStatementValidationResult.class, consumes = "application/xml, text/csv")
	public Callable<ResponseEntity<CustomerStatementValidationResult>> validate(
			@RequestParam(name = "fileCustomerStatements", required = true) final MultipartFile fileCustomerStatements)
			throws BusinessException, IOException {
		// File is mandatory
		if (fileCustomerStatements == null || fileCustomerStatements.isEmpty()) {
			throw new BusinessException("error.validation.requiredFile", null);
		}
		// Content type must be cvs or xml
		Optional<CustomerStatementContentType> contentType = CustomerStatementContentType.fromString(fileCustomerStatements.getContentType());
		if (!contentType.isPresent()) {
			throw new BusinessException("error.validation.invalidFileContentType", "Content type: " + fileCustomerStatements.getContentType());
		}
		return () -> new ResponseEntity<>(customerStatementValidatorService.validate(fileCustomerStatements.getInputStream(), contentType.get()),
				HttpStatus.OK);
	}

}
