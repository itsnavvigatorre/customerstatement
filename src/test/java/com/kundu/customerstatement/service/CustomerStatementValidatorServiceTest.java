package com.kundu.customerstatement.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.kundu.customerstatement.exception.BusinessException;
import com.kundu.customerstatement.model.CustomerStatementValidationResult;
import com.kundu.customerstatement.repository.CustomerStatementRepository;
import com.kundu.customerstatement.service.impl.CustomerStatementValidatorServiceImpl;
import com.kundu.customerstatement.util.CustomerStatementContentType;

@RunWith(MockitoJUnitRunner.class)
public class CustomerStatementValidatorServiceTest {

	private static final String SAMPLE_DATA_CSV_FILENAME_ALLVALID = "records_allvalid.csv";
	private static final String SAMPLE_DATA_CSV_FILENAME_INVALID = "records.csv";
	private static final String SAMPLE_DATA_CSV_FILENAME_INVALID_FORMAT = "records_invalidformat.csv";
	private static final String SAMPLE_DATA_XML_FILENAME_ALLVALID = "records_allvalid.xml";
	private static final String SAMPLE_DATA_XML_FILENAME_INVALID = "records.xml";
	private static final String SAMPLE_DATA_XML_FILENAME_INVALID_FORMAT = "records_invalidformat.xml";

	@Mock
	private CustomerStatementRepository customerStatementRepository;

	@InjectMocks
	private CustomerStatementValidatorServiceImpl customerStatementValidatorService;

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test(expected = BusinessException.class)
	public void testValidate_MissingFile() throws BusinessException {
		customerStatementValidatorService.validate(null, CustomerStatementContentType.CONTENT_TYPE_CSV);
	}

	@Test(expected = BusinessException.class)
	public void testValidate_MissingContentType() throws BusinessException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_CSV_FILENAME_ALLVALID);
		customerStatementValidatorService.validate(is, null);
	}

	@Test(expected = BusinessException.class)
	public void testValidate_CsvInvalidFormat() throws BusinessException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_CSV_FILENAME_INVALID_FORMAT);
		customerStatementValidatorService.validate(is, CustomerStatementContentType.CONTENT_TYPE_CSV);
	}

	@Test(expected = BusinessException.class)
	public void testValidate_XmlInvalidFormat() throws BusinessException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_XML_FILENAME_INVALID_FORMAT);
		customerStatementValidatorService.validate(is, CustomerStatementContentType.CONTENT_TYPE_XML);
	}

	@Test
	public void testValidate_CsvInvalid() throws BusinessException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_CSV_FILENAME_INVALID);
		CustomerStatementValidationResult result = customerStatementValidatorService.validate(is, CustomerStatementContentType.CONTENT_TYPE_CSV);
		assertTrue("There must be validation errors.", !result.isValid());
		assertTrue("There must be validation errors.", !result.getFailedRecords().isEmpty());
	}

	@Test
	public void testValidate_XmlInvalid() throws BusinessException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_XML_FILENAME_INVALID);
		CustomerStatementValidationResult result = customerStatementValidatorService.validate(is, CustomerStatementContentType.CONTENT_TYPE_XML);
		assertTrue("There must be validation errors.", !result.isValid());
		assertTrue("There must be validation errors.", !result.getFailedRecords().isEmpty());
	}

	@Test
	public void testValidate_CsvSuccess() throws BusinessException {
		when(customerStatementRepository.saveAll(any())).thenReturn(null);

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_CSV_FILENAME_ALLVALID);
		CustomerStatementValidationResult result = customerStatementValidatorService.validate(is, CustomerStatementContentType.CONTENT_TYPE_CSV);

		assertTrue("All validation should be passed", result.isValid());
		assertTrue("All validation should be passed", result.getFailedRecords().isEmpty());

		verify(customerStatementRepository, times(1)).saveAll(any());
	}

	@Test
	public void testValidate_XmlSuccess() throws BusinessException {
		when(customerStatementRepository.saveAll(any())).thenReturn(null);

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_XML_FILENAME_ALLVALID);
		CustomerStatementValidationResult result = customerStatementValidatorService.validate(is, CustomerStatementContentType.CONTENT_TYPE_XML);

		assertTrue("All validation should be passed", result.isValid());
		assertTrue("All validation should be passed", result.getFailedRecords().isEmpty());

		verify(customerStatementRepository, times(1)).saveAll(any());
	}

}
