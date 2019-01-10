package com.kundu.customerstatement.util;

import java.util.Arrays;
import java.util.Optional;

/**
 * This enum type is used to store the supported Content Types of the uploaded
 * file
 * 
 * @author ttukundukan
 *
 */
public enum CustomerStatementContentType {

	CONTENT_TYPE_CSV("text/csv"), CONTENT_TYPE_XML("text/xml");

	private final String contentType;

	private CustomerStatementContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}

	public static Optional<CustomerStatementContentType> fromString(String contentType) {
		return Arrays.stream(CustomerStatementContentType.values()).filter(v -> v.getContentType().equals(contentType)).findFirst();
	}

}
