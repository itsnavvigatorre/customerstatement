package com.kundu.customerstatement.model;

public class CustomerStatementFailedRecord {

	/**
	 * reference attribute of the failed statement
	 */
	private Long reference;

	/**
	 * description attribute of the failed statement
	 */
	private String description;

	/**
	 * Fail reason of the failed statement
	 */
	private CustomerStatementFailReason failReason;

	public CustomerStatementFailedRecord(Long reference, String description, CustomerStatementFailReason failReason) {
		this.reference = reference;
		this.description = description;
		this.failReason = failReason;
	}

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CustomerStatementFailReason getFailReason() {
		return failReason;
	}

	public void setFailReason(CustomerStatementFailReason failReason) {
		this.failReason = failReason;
	}

	@Override
	public String toString() {
		return "CustomerStatementFailedRecord [reference=" + reference + ", description=" + description + ", failReason=" + failReason + "]";
	}

}
