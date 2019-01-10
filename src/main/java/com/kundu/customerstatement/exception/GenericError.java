package com.kundu.customerstatement.exception;

import java.io.Serializable;

/**
 * This class is used to pass the error messages in a common way to the client.
 * 
 * @author ukundukan
 *
 */
public class GenericError implements Serializable {

	private static final long serialVersionUID = 2159047615146361183L;

	/**
	 * The error message which will send to the client
	 */
	private String errorMessage;

	public GenericError(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}