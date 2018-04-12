package com.kkd.authenticationauthorisationserver.controller;

import java.security.PublicKey;
import java.util.Optional;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.ErrorCodes;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kkd.authenticationauthorisationserver.modal.CustomerBean;
import com.kkd.authenticationauthorisationserver.modal.FarmerBean;
import com.kkd.authenticationauthorisationserver.modal.AuthenticationAuthorisationBean;
import com.kkd.authenticationauthorisationserver.proxy.UserInformationProxy;
import com.kkd.authenticationauthorisationserver.repository.Repository;
import com.kkd.authenticationauthorisationserver.service.CreateTokenService;

@RestController
public class JWT {

	@Autowired
	UserInformationProxy userProxy;

	@Autowired
	CreateTokenService generateToken;

	@Autowired
	private Repository tokendb;

	@PostMapping("/login/token/farmer")
	public ResponseEntity<String> createJwtTokenFarmerAtLogin(@RequestBody FarmerBean informationInBody) {
		ResponseEntity<FarmerBean> responsefromCustomerAtlogin = userProxy.loginFarmer(informationInBody);
		HttpStatus code = responsefromCustomerAtlogin.getStatusCode();
		if (code.equals(HttpStatus.ACCEPTED)) {
			FarmerBean farmer = responsefromCustomerAtlogin.getBody();
			String mobileNo = farmer.getMobileNo();
			String role = farmer.getRole();
			String token = generateToken.saveTokenInDb(mobileNo, role);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@PostMapping("/login/token/customer")
	public ResponseEntity<String> createJwtTokenCustomerAtLogin(@RequestBody CustomerBean informationInBody) {
		ResponseEntity<CustomerBean> responsefromCustomerAtlogin = userProxy.loginCustomer(informationInBody);
		HttpStatus code = responsefromCustomerAtlogin.getStatusCode();
		if (code.equals(HttpStatus.ACCEPTED)) {
			CustomerBean customer = responsefromCustomerAtlogin.getBody();
			String mobileNo = customer.getMobileNo();
			String role = customer.getRole();
			String token = generateToken.saveTokenInDb(mobileNo, role);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@PostMapping("/register/token/farmer")
	public ResponseEntity<String> createJwtTokenFarmerAtRegister(@RequestBody FarmerBean informationInBody) {
		ResponseEntity<FarmerBean> responsefromFarmerAtRegister = userProxy.addFarmers(informationInBody);
		HttpStatus code = responsefromFarmerAtRegister.getStatusCode();
		if (code.equals(HttpStatus.CREATED)) {
			FarmerBean farmer = responsefromFarmerAtRegister.getBody();
			String mobileNo = farmer.getMobileNo();
			String role = farmer.getRole();
			String token = generateToken.saveTokenInDb(mobileNo, role);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@PostMapping("/register/token/customer")
	public ResponseEntity<String> createJwtTokenCustomerAtRegister(@RequestBody CustomerBean informationInBody) {
		ResponseEntity<CustomerBean> responsefromCustomerAtRegister = userProxy.createCustomer(informationInBody);
		HttpStatus code = responsefromCustomerAtRegister.getStatusCode();
		if (code.equals(HttpStatus.CREATED)) {
			CustomerBean customer = responsefromCustomerAtRegister.getBody();
			String mobileNo = customer.getMobileNo();
			String role = customer.getRole();
			String token = generateToken.saveTokenInDb(mobileNo, role);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);

	}

	@PostMapping("/forgetpassword/token/customer")
	public ResponseEntity<String> createJwtTokenCustomerAtForget(@RequestBody CustomerBean informationInBody) {
		ResponseEntity<CustomerBean> responsefromCustomerAtForget = userProxy
				.updateCustomerPassword(informationInBody.getPassword(), informationInBody.getMobileNo());
		HttpStatus code = responsefromCustomerAtForget.getStatusCode();
		if (code.equals(HttpStatus.ACCEPTED)) {
			CustomerBean customer = responsefromCustomerAtForget.getBody();
			String mobileNo = customer.getMobileNo();
			String role = customer.getRole();
			String token = generateToken.saveTokenInDb(mobileNo, role);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);

	}

	@PostMapping("/forgetpassword/token/farmer")
	public ResponseEntity<String> createJwtTokenFarmerAtForget(@RequestBody FarmerBean informationInBody) {
		ResponseEntity<FarmerBean> responsefromCustomerAtForget = userProxy
				.updateFarmerPassword(informationInBody.getPassword(), informationInBody.getMobileNo());
		HttpStatus code = responsefromCustomerAtForget.getStatusCode();
		if (code.equals(HttpStatus.ACCEPTED)) {
			FarmerBean farmer = responsefromCustomerAtForget.getBody();
			String mobileNo = farmer.getMobileNo();
			String role = farmer.getRole();
			String token = generateToken.saveTokenInDb(mobileNo, role);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);

	}

	@GetMapping("/verifytoken/{token}")
	public String verifyTokenComingFromService(@PathVariable String token) {
		// public key from token db
		Optional<AuthenticationAuthorisationBean> userFound = tokendb.findById(token);
		if (!userFound.isPresent()) {
			return "false";
		}
		System.out.println(userFound);
		AuthenticationAuthorisationBean uaaModel = userFound.get();
		PublicJsonWebKey parsedPublicKeyJwk = null;
		try {
			parsedPublicKeyJwk = PublicJsonWebKey.Factory.newPublicJwk(uaaModel.getPublicKey());
		} catch (JoseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		PublicKey publicKey = parsedPublicKeyJwk.getPublicKey();
		// Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
		// be used to validate and process the JWT.
		// The specific validation requirements for a JWT are context dependent,
		// however,
		// it typically advisable to require a (reasonable) expiration time, a trusted
		// issuer, and
		// and audience that identifies your system as the intended recipient.
		// If the JWT is encrypted too, you need only provide a decryption key or
		// decryption key resolver to the builder.
		JwtConsumer jwtConsumer = new JwtConsumerBuilder().setRequireExpirationTime() // the JWT must have an
																						// expiration time
				.setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account
													// for clock skew
				.setRequireSubject() // the JWT must have a subject claim
				.setExpectedIssuer("Issuer") // whom the JWT needs to have been issued by
				.setExpectedAudience("Audience") // to whom the JWT is intended for
				.setVerificationKey(publicKey) // verify the signature with the public key
				.setJweAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
						new AlgorithmConstraints(ConstraintType.WHITELIST, // which is only RS256 here
								AlgorithmIdentifiers.RSA_USING_SHA256))
				.build(); // create the JwtConsumer instance
		try {
			// Validate the JWT and process it to the Claims
			JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
			System.out.println("JWT validation succeeded! " + jwtClaims);
			try {
				return "true"+","+jwtClaims.getSubject();
			} catch (MalformedClaimException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (InvalidJwtException e) {
			// InvalidJwtException will be thrown, if the JWT failed processing or
			// validation in anyway.
			// Hopefully with meaningful explanations(s) about what went wrong.
			System.out.println("Invalid JWT! " + e);

			// Programmatic access to (some) specific reasons for JWT invalidity is also
			// possible
			// should you want different error handling behavior for certain conditions.

			// Whether or not the JWT has expired being one common reason for invalidity
			if (e.hasExpired()) {
				try {
					System.out.println("JWT expired at " + e.getJwtContext().getJwtClaims().getExpirationTime());
				} catch (MalformedClaimException e1) {
					return "UnAuthorized";
					// e1.printStackTrace();
				}
			}

			// Or maybe the audience was invalid
			if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID)) {
				try {
					System.out.println("JWT had wrong audience: " + e.getJwtContext().getJwtClaims().getAudience());
				} catch (MalformedClaimException e1) {
					return "UnAuthorized";
					// e1.printStackTrace();
				}
			}
			try {
				throw new Exception("UnAuthorized");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return "false";

	}
}