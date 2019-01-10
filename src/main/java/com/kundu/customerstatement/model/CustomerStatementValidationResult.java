package com.kundu.customerstatement.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to send the validation results to the clients
 * 
 * @author ttukundukan
 *
 */
public class CustomerStatementValidationResult {

	/**
	 * 
	 * Validation status for all statements. If all statements are valid, then true,
	 * otherwise false
	 */
	private boolean valid;

	/**
	 * Failed customer statements
	 */
	private List<CustomerStatementFailedRecord> failedRecords;

	public boolean isValid() {
		valid = failedRecords == null || failedRecords.isEmpty();
		return valid;
	}

	public List<CustomerStatementFailedRecord> getFailedRecords() {
		if (failedRecords == null) {
			failedRecords = new ArrayList<>();
		}
		return failedRecords;
	}

	public void setFailedRecords(List<CustomerStatementFailedRecord> failedRecords) {
		this.failedRecords = failedRecords;
	}

}
