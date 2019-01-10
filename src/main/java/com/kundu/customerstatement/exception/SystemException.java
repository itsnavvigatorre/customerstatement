package com.kundu.customerstatement.exception;

/**
 * This exception type is used to handle the situations like breaking any system
 * rules defined in the application such as getting timeout from external
 * services etc.
 * 
 * @author ukundukan
 *
 */
public class SystemException extends Exception {

	private static final long serialVersionUID = -6401247844415505413L;

	/**
	 * This property stores the bundle key of exception, if the case of exception
	 * has already been defined in the resource bundle.
	 */
	private String errorBundleKey;

	/**
	 * This property stores the message of the error, if the case of exception has
	 * not been defined in the resource bundle.
	 */
	private String errorMessage;

	public SystemException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public SystemException(String errorBundleKey, String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
		this.errorBundleKey = errorBundleKey;
	}

	public SystemException(String errorBundleKey, String errorMessage, Throwable cause) {
		super(errorMessage, cause);
		this.errorBundleKey = errorBundleKey;
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorBundleKey() {
		return errorBundleKey;
	}

}
