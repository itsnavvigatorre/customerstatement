package com.kundu.customerstatement.model;

public enum CustomerStatementFailReason {

	NON_UNUIQUE_TRANSACTION(1, "Non Unique Transaction"), INVALID_END_BALANCE(2, "Invalid End Balance");

	private final int failCode;
	private final String failMessage;

	private CustomerStatementFailReason(int failCode, String failMessage) {
		this.failCode = failCode;
		this.failMessage = failMessage;
	}

	public int getFailCode() {
		return failCode;
	}

	public String getFailMessage() {
		return failMessage;
	}

}
