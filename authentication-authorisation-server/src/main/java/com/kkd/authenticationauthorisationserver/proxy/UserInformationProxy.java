package com.kkd.authenticationauthorisationserver.proxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kkd.authenticationauthorisationserver.modal.CustomerBean;
import com.kkd.authenticationauthorisationserver.modal.FarmerBean;

//accesing db service through feign client
@FeignClient(value = "registration-login-service", url = "http://localhost:8012")
@RibbonClient(name = "registration-login-service")
public interface UserInformationProxy {

	@PostMapping("/customer/user/register")
	public ResponseEntity<CustomerBean> createCustomer(@RequestBody CustomerBean customer);

	@PutMapping("/customer/user/forgetpassword/{mobileNo}")
	public ResponseEntity<CustomerBean> updateCustomerPassword(@RequestBody String password,
			@PathVariable(value = "mobileNo") String mobileNo);

	@PostMapping("/farmer/user/register")
	public ResponseEntity<FarmerBean> addFarmers(@RequestBody FarmerBean farmer);

	@PutMapping("/farmer/user/forgetpassword/{mobileNo}")
	public ResponseEntity<FarmerBean> updateFarmerPassword(@RequestBody String password,
			@PathVariable(value = "mobileNo") String mobileNo);

	@PostMapping("/customer/user/login")
	public ResponseEntity<CustomerBean> loginCustomer(@RequestBody CustomerBean customer);

	@PostMapping("/farmer/user/login")
	public ResponseEntity<FarmerBean> loginFarmer(@RequestBody FarmerBean farmer);

}