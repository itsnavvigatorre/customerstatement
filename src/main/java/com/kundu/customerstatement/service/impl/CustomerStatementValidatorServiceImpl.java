package com.kundu.customerstatement.service.impl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kundu.customerstatement.entity.CustomerStatement;
import com.kundu.customerstatement.entity.CustomerStatementWrapper;
import com.kundu.customerstatement.exception.BusinessException;
import com.kundu.customerstatement.model.CustomerStatementFailReason;
import com.kundu.customerstatement.model.CustomerStatementFailedRecord;
import com.kundu.customerstatement.model.CustomerStatementValidationResult;
import com.kundu.customerstatement.repository.CustomerStatementRepository;
import com.kundu.customerstatement.service.CustomerStatementValidatorService;
import com.kundu.customerstatement.util.CustomerStatementContentType;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

@Service
public class CustomerStatementValidatorServiceImpl implements CustomerStatementValidatorService {
	
	private static final Logger myLogger = LoggerFactory.getLogger(CustomerStatementValidatorServiceImpl.class);

	private CustomerStatementRepository customerStatementRepository;

	@Autowired
	public CustomerStatementValidatorServiceImpl(CustomerStatementRepository customerStatementRepository) {
		this.customerStatementRepository = customerStatementRepository;
	}

	@Override
	public CustomerStatementValidationResult validate(InputStream inputStream, CustomerStatementContentType customerStatementContentType)
			throws BusinessException {
		if(inputStream == null || customerStatementContentType == null) {
			throw new BusinessException("File and content type parameters can not be empty.");
		}
		List<CustomerStatement> customerStatements = null;
		if (customerStatementContentType == CustomerStatementContentType.CONTENT_TYPE_CSV) {
			customerStatements = getCustomerStatementsFromCsv(inputStream);
		} else {
			customerStatements = getCustomerStatementsFromXml(inputStream);
		}

		// TODO Will be used for parallel validation
		customerStatementRepository.saveAll(customerStatements);

		CustomerStatementValidationResult result = new CustomerStatementValidationResult();

		// Find duplicates and Collect unique records in another array to reduce operation count
		List<CustomerStatement> uniqueCustomerStatement = validateDuplicate(customerStatements, result);

		// Find balance failures
		validateBalaceFailures(uniqueCustomerStatement, result);
		
		if(!result.isValid() ) {
			myLogger.warn("There are invalid customer statements. Count: {}", result.getFailedRecords().size());
			if(myLogger.isDebugEnabled()) {
				myLogger.debug("Invalid statements: {}", result.getFailedRecords());
			}
		}

		return result;
	}

	/**
	 * This method used to convert Csv formatter stream to beans
	 * 
	 * @param inputStream
	 * @return
	 * @throws BusinessException
	 */
	private List<CustomerStatement> getCustomerStatementsFromCsv(InputStream inputStream) throws BusinessException {
		try {
			Map<String, String> columnMapping = new HashMap<String, String>();
			columnMapping.put("Reference", "reference");
			columnMapping.put("Account Number", "accountNumber");
			columnMapping.put("Description", "description");
			columnMapping.put("Start Balance", "startBalance");
			columnMapping.put("Mutation", "mutation");
			columnMapping.put("End Balance", "endBalance");

			HeaderColumnNameTranslateMappingStrategy<CustomerStatement> strategy = new HeaderColumnNameTranslateMappingStrategy<CustomerStatement>();
			strategy.setType(CustomerStatement.class);
			strategy.setColumnMapping(columnMapping);

			CsvToBeanBuilder<CustomerStatement> csvToBeanBuilder = new CsvToBeanBuilder<CustomerStatement>(new InputStreamReader(inputStream));
			return csvToBeanBuilder.withMappingStrategy(strategy).build().parse();
		} catch (Exception e) {
			throw new BusinessException("error.validation.invalidFileCsv", null, e);
		}
	}

	/**
	 * This method used to convert Xml formatter stream to beans
	 * 
	 * @param inputStream
	 * @return
	 * @throws BusinessException
	 */
	private List<CustomerStatement> getCustomerStatementsFromXml(InputStream inputStream) throws BusinessException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(CustomerStatementWrapper.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			CustomerStatementWrapper wrapper = (CustomerStatementWrapper) unmarshaller.unmarshal(inputStream);
			return wrapper.getCustomerStatements();
		} catch (Exception e) {
			throw new BusinessException("error.validation.invalidFileXml", null, e);
		}
	}

	/**
	 * This method is used to find duplicated record, and add these records to the result.
	 * 
	 * @param customerStatements
	 * @param result
	 * @return Returns unique records
	 */
	private List<CustomerStatement> validateDuplicate(List<CustomerStatement> customerStatements, CustomerStatementValidationResult result) {
		// Collect unique records in another array to reduce operation count
		List<CustomerStatement> uniqueCustomerStatement = new ArrayList<>();
		customerStatements.stream().collect(Collectors.groupingBy(CustomerStatement::getReference)).forEach((k, v) -> {
			if (v.size() > 1) {
				v.forEach(failed -> result.getFailedRecords().add(new CustomerStatementFailedRecord(failed.getReference(), failed.getDescription(),
						CustomerStatementFailReason.NON_UNUIQUE_TRANSACTION)));
			} else {
				uniqueCustomerStatement.add(v.get(0));
			}
		});
		return uniqueCustomerStatement;
	}

	/**
	 * This method is used to find balance failures by comparing the equality between "start + mutation" and "end", and add these records to the
	 * result.
	 * 
	 * @param customerStatements
	 * @param result
	 */
	private void validateBalaceFailures(List<CustomerStatement> customerStatements, CustomerStatementValidationResult result) {
		for (CustomerStatement cs : customerStatements) {
			BigDecimal start = BigDecimal.valueOf(cs.getStartBalance());
			BigDecimal mutation = BigDecimal.valueOf(cs.getMutation());
			BigDecimal end = BigDecimal.valueOf(cs.getEndBalance());
			if (start.add(mutation).compareTo(end) != 0) {
				result.getFailedRecords().add(
						new CustomerStatementFailedRecord(cs.getReference(), cs.getDescription(), CustomerStatementFailReason.INVALID_END_BALANCE));
			}
		}
	}

}
