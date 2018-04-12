package com.kkd.authenticationauthorisationserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kkd.authenticationauthorisationserver.modal.AuthenticationAuthorisationBean;

public interface Repository extends MongoRepository<AuthenticationAuthorisationBean, String> {

}
