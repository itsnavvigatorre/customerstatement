package com.kundu.customerstatement.service;

import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kundu.customerstatement.exception.BusinessException;
import com.kundu.customerstatement.model.CustomerStatementValidationResult;
import com.kundu.customerstatement.util.CustomerStatementContentType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerStatementValidatorServiceIT {

	private static final String SAMPLE_DATA_CSV_FILENAME_ALLVALID = "records_allvalid.csv";
	private static final String SAMPLE_DATA_CSV_FILENAME_INVALID = "records.csv";
	private static final String SAMPLE_DATA_XML_FILENAME_ALLVALID = "records_allvalid.xml";
	private static final String SAMPLE_DATA_XML_FILENAME_INVALID = "records.xml";

	@Autowired
	private CustomerStatementValidatorService customerStatementValidatorService;
	
	@Test
	public void testValidate_ValidCsvFile() throws BusinessException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_CSV_FILENAME_ALLVALID);
		CustomerStatementValidationResult result = customerStatementValidatorService.validate(is, CustomerStatementContentType.CONTENT_TYPE_CSV);
		assertTrue("All validation should be passed", result.isValid());
		assertTrue("All validation should be passed", result.getFailedRecords().isEmpty());
	}

	@Test
	public void testValidate_InvalidCsvFile() throws BusinessException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_CSV_FILENAME_INVALID);
		CustomerStatementValidationResult result = customerStatementValidatorService.validate(is, CustomerStatementContentType.CONTENT_TYPE_CSV);
		assertTrue("There must be validation errors.", !result.isValid());
		assertTrue("There must be validation errors.", !result.getFailedRecords().isEmpty());
	}

	@Test
	public void testValidate_ValidXmlFile() throws BusinessException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_XML_FILENAME_ALLVALID);
		CustomerStatementValidationResult result = customerStatementValidatorService.validate(is, CustomerStatementContentType.CONTENT_TYPE_XML);
		assertTrue("All validation should be passed", result.isValid());
		assertTrue("All validation should be passed", result.getFailedRecords().isEmpty());
	}

	@Test
	public void testValidate_InvalidXmlFile() throws BusinessException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_XML_FILENAME_INVALID);
		CustomerStatementValidationResult result = customerStatementValidatorService.validate(is, CustomerStatementContentType.CONTENT_TYPE_XML);
		assertTrue("There must be validation errors.", !result.isValid());
		assertTrue("There must be validation errors.", !result.getFailedRecords().isEmpty());
	}

}
