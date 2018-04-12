package com.kkd.authenticationauthorisationserver.modal;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
public class CustomerBean {


	private String customerId;
	@Id
	private String mobileNo;
	private String password;
	private String firstName;
	private String lastName;
	private List<AddressBean> addresses;
	private AddressBean primaryAddress;
	private String role;

	public CustomerBean() {
		super();
	}

	public CustomerBean(String customerId, String mobileNo, String password, String firstName, String lastName,
			List<AddressBean> addresses, AddressBean primaryAddress, String role) {
		super();
		this.customerId = customerId;
		this.mobileNo = mobileNo;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.addresses = addresses;
		this.primaryAddress = primaryAddress;
		this.role = role;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<AddressBean> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressBean> addresses) {
		this.addresses = addresses;
	}

	public AddressBean getPrimaryAddress() {
		return primaryAddress;
	}

	public void setPrimaryAddress(AddressBean primaryAddress) {
		this.primaryAddress = primaryAddress;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "CustomerBean [customerId=" + customerId + ", mobileNo=" + mobileNo + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", addresses=" + addresses
				+ ", primaryAddress=" + primaryAddress + ", role=" + role + "]";
	}
}
