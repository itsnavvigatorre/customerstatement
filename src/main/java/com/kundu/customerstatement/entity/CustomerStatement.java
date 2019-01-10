package com.kundu.customerstatement.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This entity is used to access and store the customer statement.
 * 
 * @author ukundukan
 * 
 */
@Entity
@Table(name = "CUSTOMERSTATEMENT")
@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerStatement implements Serializable {

	private static final long serialVersionUID = -2780764708740882016L;

	/**
	 * Identity property for the stored customer statement
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * reference attribute of customer statement
	 */
	@Column(name = "REFERENCE")
	@XmlAttribute(name="reference")
	private Long reference;

	/**
	 * accountNumber attribute of customer statement
	 */
	@Column(name = "ACCOUNTNUMBER")
	@XmlElement(name = "accountNumber")
	private String accountNumber;

	/**
	 * description attribute of customer statement
	 */
	@Column(name = "DESCRIPTION")
	@XmlElement(name = "description")
	private String description;

	/**
	 * startBalance attribute of customer statement
	 */
	@Column(name = "STARTBALANCE")
	@XmlElement(name = "startBalance")
	private Double startBalance;

	/**
	 * mutation attribute of customer statement
	 */
	@Column(name = "MUTATION")
	@XmlElement(name = "mutation")
	private Double mutation;

	/**
	 * endBalance attribute of customer statement
	 */
	@Column(name = "ENDBALANCE")
	@XmlElement(name = "endBalance")
	private Double endBalance;

	public CustomerStatement() {
	}

	public CustomerStatement(Long id) {
		this.id = id;
	}

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(Double startBalance) {
		this.startBalance = startBalance;
	}

	public Double getMutation() {
		return mutation;
	}

	public void setMutation(Double mutation) {
		this.mutation = mutation;
	}

	public Double getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(Double endBalance) {
		this.endBalance = endBalance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CustomerStatement [id=" + id + ", reference=" + reference + ", accountNumber=" + accountNumber + ", description=" + description
				+ ", startBalance=" + startBalance + ", mutation=" + mutation + ", endBalance=" + endBalance + "]";
	}
}
