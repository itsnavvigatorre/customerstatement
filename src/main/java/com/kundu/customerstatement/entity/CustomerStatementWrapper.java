package com.kundu.customerstatement.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is used to wrap CustomerStatement class to be able to do xml
 * operations
 * 
 * @author ttukundukan
 *
 */
@XmlRootElement(name = "records")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerStatementWrapper {

	@XmlElement(name = "record")
	private List<CustomerStatement> customerStatements;

	public List<CustomerStatement> getCustomerStatements() {
		return customerStatements;
	}

	public void setCustomerStatements(List<CustomerStatement> customerStatements) {
		this.customerStatements = customerStatements;
	}

}
