package com.kkd.authenticationauthorisationserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kkd.authenticationauthorisationserver.modal.UaaModel;

public interface Repository extends MongoRepository<UaaModel, String> {

}
