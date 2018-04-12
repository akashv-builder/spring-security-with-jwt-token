package com.kkd.registrationloginservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kkd.registrationloginservice.modal.CustomerBean;
import com.kkd.registrationloginservice.modal.FarmerBean;
import com.kkd.registrationloginservice.repository.CustomerRepository;
import com.kkd.registrationloginservice.repository.FarmerDetailsRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class CustomerFarmerController {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private FarmerDetailsRepository farmerDetailsRepository;

	/* add customer in the collection */
	@PostMapping("/customer/user/register")
	@HystrixCommand(fallbackMethod = "fallbackStatus")
	public ResponseEntity<CustomerBean> createCustomer(@RequestBody CustomerBean customer) {
		Optional<CustomerBean> findByMobileNo = customerRepository.findByMobileNo(customer.getMobileNo());
		if (!findByMobileNo.isPresent()) {
			customerRepository.save(customer);
			return ResponseEntity.status(HttpStatus.CREATED).body(customer);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@PutMapping("/customer/user/forgetpassword/{mobileNo}")
	@HystrixCommand(fallbackMethod = "fallbackStatusCustomer")
	public ResponseEntity<CustomerBean> updateCustomerPassword(@RequestBody String password,
			@PathVariable String mobileNo) {
		Optional<CustomerBean> findByMobileNo = customerRepository.findByMobileNo(mobileNo);

		if (findByMobileNo.isPresent()) {
			CustomerBean customer = findByMobileNo.get();
			customer.setPassword(password);
			customerRepository.save(customer);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(customer);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@PostMapping("/farmer/user/register")
	@HystrixCommand(fallbackMethod = "fallbackStatus")
	public ResponseEntity<FarmerBean> addFarmers(@RequestBody FarmerBean farmer) {
		Optional<FarmerBean> findByMobileNo = farmerDetailsRepository.findByMobileNo(farmer.getMobileNo());
		if (!findByMobileNo.isPresent()) {
			farmerDetailsRepository.save(farmer);
			return ResponseEntity.status(HttpStatus.CREATED).body(farmer);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@PutMapping("/farmer/user/forgetpassword/{mobileNo}")
	@HystrixCommand(fallbackMethod = "fallbackStatusFarmer")
	public ResponseEntity<FarmerBean> updateFarmerPassword(@RequestBody String password,
			@PathVariable String mobileNo) {
		Optional<FarmerBean> findByMobileNo = farmerDetailsRepository.findByMobileNo(mobileNo);
		if (findByMobileNo.isPresent()) {
			FarmerBean farmer = findByMobileNo.get();
			farmer.setPassword(password);
			farmerDetailsRepository.save(farmer);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(farmer);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@PostMapping("/customer/user/login")
	@HystrixCommand(fallbackMethod = "fallbackStatus")
	public ResponseEntity<CustomerBean> loginCustomer(@RequestBody CustomerBean customer) {
		Optional<CustomerBean> findByMobileNo = customerRepository.findByMobileNo(customer.getMobileNo());
		if (findByMobileNo.isPresent()) {
			CustomerBean userFound = findByMobileNo.get();
			String storedPassword = userFound.getPassword();
			if (storedPassword.equals(customer.getPassword())) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(userFound);
			}
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@PostMapping("/farmer/user/login")
	@HystrixCommand(fallbackMethod = "fallbackStatus")
	public ResponseEntity<FarmerBean> loginFarmer(@RequestBody FarmerBean farmer) {
		Optional<FarmerBean> findByMobileNo = farmerDetailsRepository.findByMobileNo(farmer.getMobileNo());
		if (findByMobileNo.isPresent()) {
			FarmerBean userFound = findByMobileNo.get();
			String storedPassword = userFound.getPassword();
			if (storedPassword.equals(farmer.getPassword())) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(userFound);
			}
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	/* hystrix fallback method for default customer creation */
	public ResponseEntity<CustomerBean> fallbackStatus(CustomerBean customer) {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<FarmerBean> fallbackStatus(FarmerBean farmer) {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<CustomerBean> fallbackStatusCustomer(String password, String mobileNo) {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<FarmerBean> fallbackStatusFarmer(String password, String mobileNo) {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
