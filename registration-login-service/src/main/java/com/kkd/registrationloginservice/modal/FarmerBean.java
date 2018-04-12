package com.kkd.registrationloginservice.modal;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "farmer")
public class FarmerBean {

	private String kkdFarmId;
	@Id
	private String mobileNo;
	private String password;
	private String alternateNo;
	private ArrayList<String> cities;
	private AddressBean currentAddress;
	private String status;
	private boolean autoConfirm;
	private AadharBean aadharData;
	private String role;

	public FarmerBean() {
		super();
	}

	public FarmerBean(String kkdFarmId, String mobileNo, String password, String alternateNo, ArrayList<String> cities,
			AddressBean currentAddress, String status, boolean autoConfirm, AadharBean aadharData, String role) {
		super();
		this.kkdFarmId = kkdFarmId;
		this.mobileNo = mobileNo;
		this.password = password;
		this.alternateNo = alternateNo;
		this.cities = cities;
		this.currentAddress = currentAddress;
		this.status = status;
		this.autoConfirm = autoConfirm;
		this.aadharData = aadharData;
		this.role = role;
	}

	public String getKkdFarmId() {
		return kkdFarmId;
	}

	public void setKkdFarmId(String kkdFarmId) {
		this.kkdFarmId = kkdFarmId;
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

	public String getAlternateNo() {
		return alternateNo;
	}

	public void setAlternateNo(String alternateNo) {
		this.alternateNo = alternateNo;
	}

	public ArrayList<String> getCities() {
		return cities;
	}

	public void setCities(ArrayList<String> cities) {
		this.cities = cities;
	}

	public AddressBean getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(AddressBean currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isAutoConfirm() {
		return autoConfirm;
	}

	public void setAutoConfirm(boolean autoConfirm) {
		this.autoConfirm = autoConfirm;
	}

	public AadharBean getAadharData() {
		return aadharData;
	}

	public void setAadharData(AadharBean aadharData) {
		this.aadharData = aadharData;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "FarmerBean [kkdFarmId=" + kkdFarmId + ", mobileNo=" + mobileNo + ", password=" + password
				+ ", alternateNo=" + alternateNo + ", cities=" + cities + ", currentAddress=" + currentAddress
				+ ", status=" + status + ", autoConfirm=" + autoConfirm + ", aadharData=" + aadharData + ", role="
				+ role + "]";
	}
}
