package com.kundu.customerstatement.service;

import java.io.InputStream;

import com.kundu.customerstatement.exception.BusinessException;
import com.kundu.customerstatement.model.CustomerStatementValidationResult;
import com.kundu.customerstatement.util.CustomerStatementContentType;

/**
 * This interface will be used as a reference for processing Customer Statement
 * validation rules.
 * 
 * @author ukundukan
 *
 */
public interface CustomerStatementValidatorService {

	/**
	 * This method is used to validate the customer statements
	 * 
	 * @param inputStream
	 *            The content of the file
	 * @param customerStatementContentType
	 *            The content type of the file
	 * @return Returns the validation result
	 * @throws BusinessException
	 */
	public CustomerStatementValidationResult validate(InputStream inputStream, CustomerStatementContentType customerStatementContentType)
			throws BusinessException;

}
