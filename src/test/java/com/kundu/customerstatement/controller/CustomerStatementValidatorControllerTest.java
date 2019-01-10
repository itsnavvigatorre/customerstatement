package com.kundu.customerstatement.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.kundu.customerstatement.exception.BusinessException;
import com.kundu.customerstatement.model.CustomerStatementValidationResult;
import com.kundu.customerstatement.service.CustomerStatementValidatorService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerStatementValidatorControllerTest {

	private static final String SAMPLE_DATA_XML_FILENAME_ALLVALID = "records_allvalid.xml";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CustomerStatementValidatorService customerStatementValidatorService;

	@Test
	public void textValidation_Succcess() throws Exception {
		CustomerStatementValidationResult result = new CustomerStatementValidationResult();
		when(customerStatementValidatorService.validate(any(), any())).thenReturn(result);

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_XML_FILENAME_ALLVALID);
		MockMultipartFile file = new MockMultipartFile("fileCustomerStatements", "filename", MediaType.TEXT_XML_VALUE, is);
		mvc.perform(multipart("/validate").file(file).contentType(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
				.andExpect(jsonPath("$.valid", is(Boolean.TRUE))).andExpect(jsonPath("$.failedRecords", hasSize(0)));
	}

	@Test
	public void textValidation_ServiceThrowException() throws Exception {
		BusinessException be = new BusinessException("test");
		when(customerStatementValidatorService.validate(any(), any())).thenThrow(be);

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_XML_FILENAME_ALLVALID);
		MockMultipartFile file = new MockMultipartFile("fileCustomerStatements", "filename", MediaType.TEXT_XML_VALUE, is);
		mvc.perform(multipart("/validate").file(file).contentType(MediaType.TEXT_PLAIN)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorMessage", is(be.getErrorMessage())));
	}

}
