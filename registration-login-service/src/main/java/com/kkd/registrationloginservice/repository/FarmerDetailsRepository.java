package com.kkd.registrationloginservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kkd.registrationloginservice.modal.FarmerBean;


public interface FarmerDetailsRepository extends MongoRepository<FarmerBean, String> {

	public Optional<FarmerBean> findByMobileNo(String mobileNo);
}
