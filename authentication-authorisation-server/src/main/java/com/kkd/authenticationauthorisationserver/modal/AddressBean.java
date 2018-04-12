package com.kkd.authenticationauthorisationserver.modal;

import org.springframework.stereotype.Component;

@Component
public class AddressBean {

	private Integer pincode;
	private String addressLine;
	private String city;
	private String district;
	private String state;
	private Boolean primary;

	public AddressBean() {
		super();
	}

	public AddressBean(Integer pincode, String addressLine, String city, String district, String state, Boolean primary) {
		super();
		this.pincode = pincode;
		this.addressLine = addressLine;
		this.city = city;
		this.district = district;
		this.state = state;
		this.primary = primary;
	}

	public Integer getPincode() {
		return pincode;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getPrimary() {
		return primary;
	}

	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}

	@Override
	public String toString() {
		return "Address [pincode=" + pincode + ", addressLine=" + addressLine + ", city=" + city + ", district="
				+ district + ", state=" + state + ", primary=" + primary + "]";
	}

}
