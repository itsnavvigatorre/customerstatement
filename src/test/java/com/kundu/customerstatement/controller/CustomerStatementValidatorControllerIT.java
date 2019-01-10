package com.kundu.customerstatement.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerStatementValidatorControllerIT {

	private static final String SAMPLE_DATA_CSV_FILENAME_ALLVALID = "records_allvalid.csv";
	private static final String SAMPLE_DATA_CSV_FILENAME_INVALID = "records.csv";
	private static final String SAMPLE_DATA_CSV_FILENAME_INVALID_FORMAT = "records_invalidformat.csv";
	private static final String SAMPLE_DATA_XML_FILENAME_ALLVALID = "records_allvalid.xml";
	private static final String SAMPLE_DATA_XML_FILENAME_INVALID = "records.xml";
	private static final String SAMPLE_DATA_XML_FILENAME_INVALID_FORMAT = "records_invalidformat.xml";

	@Autowired
	private MockMvc mvc;

	@Test
	public void textValidation_EmptyFile() throws Exception {
		MockMultipartFile file = new MockMultipartFile("fileCustomerStatements", "filename", MediaType.TEXT_XML_VALUE, "".getBytes());
		mvc.perform(multipart("/validate").file(file).contentType(MediaType.TEXT_XML)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorMessage", is("Customer Statements file is mandatory.")));

	}

	@Test
	public void textValidation_InvalidContentType() throws Exception {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_CSV_FILENAME_ALLVALID);
		MockMultipartFile file = new MockMultipartFile("fileCustomerStatements", "filename", MediaType.TEXT_PLAIN_VALUE, is);
		mvc.perform(multipart("/validate").file(file).contentType(MediaType.TEXT_PLAIN)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorMessage", is("Only 'text/csv' and 'text/xml' types are allowed")));
	}

	@Test
	public void textValidation_CsvAllValid() throws Exception {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_CSV_FILENAME_ALLVALID);
		MockMultipartFile file = new MockMultipartFile("fileCustomerStatements", "filename", "text/csv", is);
		mvc.perform(multipart("/validate").file(file).contentType(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
				.andExpect(jsonPath("$.valid", is(Boolean.TRUE))).andExpect(jsonPath("$.failedRecords", hasSize(0)));
	}

	@Test
	public void textValidation_XmlAllValid() throws Exception {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_XML_FILENAME_ALLVALID);
		MockMultipartFile file = new MockMultipartFile("fileCustomerStatements", "filename", MediaType.TEXT_XML_VALUE, is);
		mvc.perform(multipart("/validate").file(file).contentType(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
				.andExpect(jsonPath("$.valid", is(Boolean.TRUE))).andExpect(jsonPath("$.failedRecords", hasSize(0)));
	}

	@Test
	public void textValidation_CsvInvalid() throws Exception {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_CSV_FILENAME_INVALID);
		MockMultipartFile file = new MockMultipartFile("fileCustomerStatements", "filename", "text/csv", is);
		mvc.perform(multipart("/validate").file(file).contentType(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
				.andExpect(jsonPath("$.valid", is(Boolean.FALSE))).andExpect(jsonPath("$.failedRecords", hasSize(3)));
	}

	@Test
	public void textValidation_XmlInvalid() throws Exception {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_XML_FILENAME_INVALID);
		MockMultipartFile file = new MockMultipartFile("fileCustomerStatements", "filename", MediaType.TEXT_XML_VALUE, is);
		mvc.perform(multipart("/validate").file(file).contentType(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
				.andExpect(jsonPath("$.valid", is(Boolean.FALSE))).andExpect(jsonPath("$.failedRecords", hasSize(2)));
	}

	@Test
	public void textValidation_CsvInvalidFormat() throws Exception {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_CSV_FILENAME_INVALID_FORMAT);
		MockMultipartFile file = new MockMultipartFile("fileCustomerStatements", "filename", "text/csv", is);
		mvc.perform(multipart("/validate").file(file).contentType(MediaType.TEXT_PLAIN)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorMessage", is("Csv file is invalid.")));
	}

	@Test
	public void textValidation_XmlInvalidFormat() throws Exception {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_XML_FILENAME_INVALID_FORMAT);
		MockMultipartFile file = new MockMultipartFile("fileCustomerStatements", "filename", MediaType.TEXT_XML_VALUE, is);
		mvc.perform(multipart("/validate").file(file).contentType(MediaType.TEXT_PLAIN)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorMessage", is("Xml file is invalid.")));
	}

}
