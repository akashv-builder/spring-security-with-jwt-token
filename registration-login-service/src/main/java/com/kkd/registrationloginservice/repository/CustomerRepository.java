package com.kkd.registrationloginservice.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.kkd.registrationloginservice.modal.CustomerBean;;

public interface CustomerRepository extends MongoRepository<CustomerBean, String> {

	public Optional<CustomerBean> findByMobileNo(String mobileNo);
}
