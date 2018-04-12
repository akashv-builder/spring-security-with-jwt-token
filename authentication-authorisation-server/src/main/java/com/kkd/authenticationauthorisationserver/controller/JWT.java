package com.kkd.authenticationauthorisationserver.controller;

import java.security.PublicKey;
import java.util.Optional;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
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
import com.kkd.authenticationauthorisationserver.modal.UaaModel;
import com.kkd.authenticationauthorisationserver.proxy.UserInformationProxy;
import com.kkd.authenticationauthorisationserver.repository.Repository;

@RestController
public class JWT {

	@Autowired
	UserInformationProxy userProxy;

	@Autowired
	private Repository tokendb; // tokendb object of token db class

	/*@PostMapping("/login/token/farmer")
	public ResponseEntity<String> createJwtTokenFarmerAtLogin(@RequestBody FarmerBean informationInBody) {
		System.out.println("*******in"+informationInBody);
		System.out.println("thankyou"+userProxy.loginFarmer(informationInBody));
		ResponseEntity<FarmerBean> responsefromFarmerAtLogin = userProxy.loginFarmer(informationInBody);
		System.out.println("*******out");
		System.out.println(userProxy.loginFarmer(informationInBody));
		HttpStatus code=responsefromFarmerAtLogin.getStatusCode();
		if(code.equals(HttpStatus.FOUND)) {
			FarmerBean farmer = responsefromFarmerAtLogin.getBody();
			String mobileNo = farmer.getMobileNo();
			String role = farmer.getRole();
			String tokenAndKey = createToken(mobileNo, role);
			String[] splittedTokenAndKey = tokenAndKey.split(",");
			String publicKeyJwkString = splittedTokenAndKey[1];
			String jwt = splittedTokenAndKey[0];
			tokendb.save(new UaaModel(publicKeyJwkString, jwt));
			return ResponseEntity.status(HttpStatus.FOUND).body(jwt);
		}	
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@PostMapping("/login/token/customer")
	public ResponseEntity<String> createJwtTokenCustomerAtLogin(@RequestBody CustomerBean informationInBody) {
		ResponseEntity<CustomerBean> responsefromCustomerAtLogin = userProxy.loginCustomer(informationInBody);
		HttpStatus code=responsefromCustomerAtLogin.getStatusCode();
		if(code.equals(HttpStatus.FOUND)) {
			CustomerBean customer = responsefromCustomerAtLogin.getBody();
			String mobileNo = customer.getMobileNo();
			String role = customer.getRole();
			String tokenAndKey = createToken(mobileNo, role);
			String[] splittedTokenAndKey = tokenAndKey.split(",");
			String publicKeyJwkString = splittedTokenAndKey[1];
			String jwt = splittedTokenAndKey[0];
			tokendb.save(new UaaModel(publicKeyJwkString, jwt));
			return ResponseEntity.status(HttpStatus.CREATED).body(jwt);
		}	
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}*/

	@PostMapping("/test")
	public String aaa(@RequestBody FarmerBean body) {
		ResponseEntity<FarmerBean> responsefromFarmerAtRegister=userProxy.addFarmers(body);
		System.out.println(responsefromFarmerAtRegister);
		return "hey";
	}
	@PostMapping("/register/token/farmer")
	public ResponseEntity<String> createJwtTokenFarmerAtRegister(@RequestBody FarmerBean informationInBody) {
		ResponseEntity<FarmerBean> responsefromFarmerAtRegister = userProxy.addFarmers(informationInBody);
		HttpStatus code=responsefromFarmerAtRegister.getStatusCode();
		if(code.equals(HttpStatus.CREATED)) {
			FarmerBean farmer = responsefromFarmerAtRegister.getBody();
			String mobileNo = farmer.getMobileNo();
			String role = farmer.getRole();
			String tokenAndKey = createToken(mobileNo, role);
			String[] splittedTokenAndKey = tokenAndKey.split(",");
			String publicKeyJwkString = splittedTokenAndKey[1];
			String jwt = splittedTokenAndKey[0];
			tokendb.save(new UaaModel(publicKeyJwkString, jwt));
			return ResponseEntity.status(HttpStatus.CREATED).body(jwt);
		}	
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@PostMapping("/register/token/customer")
	public ResponseEntity<String> createJwtTokenCustomerAtRegister(@RequestBody CustomerBean informationInBody) {
		ResponseEntity<CustomerBean> responsefromCustomerAtRegister = userProxy.createCustomer(informationInBody);
		HttpStatus code=responsefromCustomerAtRegister.getStatusCode();
		if(code.equals(HttpStatus.CREATED)) {
			CustomerBean customer = responsefromCustomerAtRegister.getBody();
			String mobileNo = customer.getMobileNo();
			String role = customer.getRole();
			String tokenAndKey = createToken(mobileNo, role);
			String[] splittedTokenAndKey = tokenAndKey.split(",");
			String publicKeyJwkString = splittedTokenAndKey[1];
			String jwt = splittedTokenAndKey[0];
			tokendb.save(new UaaModel(publicKeyJwkString, jwt));
			return ResponseEntity.status(HttpStatus.CREATED).body(jwt);
		}	
		return new ResponseEntity<>(HttpStatus.CONFLICT);

	}

	@PostMapping("/forgetpassword/token/customer")
	public ResponseEntity<String> createJwtTokenCustomerAtForget(@RequestBody CustomerBean informationInBody) {
		ResponseEntity<CustomerBean> responsefromCustomerAtForget = userProxy.updateCustomerPassword(informationInBody.getPassword(),informationInBody.getMobileNo());
		HttpStatus code=responsefromCustomerAtForget.getStatusCode();
		if(code.equals(HttpStatus.ACCEPTED)) {
			CustomerBean customer = responsefromCustomerAtForget.getBody();
			String mobileNo = customer.getMobileNo();
			String role = customer.getRole();
			String tokenAndKey = createToken(mobileNo, role);
			String[] splittedTokenAndKey = tokenAndKey.split(",");
			String publicKeyJwkString = splittedTokenAndKey[1];
			String jwt = splittedTokenAndKey[0];
			tokendb.save(new UaaModel(publicKeyJwkString, jwt));
			return ResponseEntity.status(HttpStatus.CREATED).body(jwt);
		}	
		return new ResponseEntity<>(HttpStatus.CONFLICT);

	}

	@PostMapping("/forgetpassword/token/farmer")
	public ResponseEntity<String> createJwtTokenFarmerAtForget(@RequestBody FarmerBean informationInBody) {
		ResponseEntity<FarmerBean> responsefromCustomerAtForget = userProxy.updateFarmerPassword(informationInBody.getPassword(),informationInBody.getMobileNo());
		HttpStatus code=responsefromCustomerAtForget.getStatusCode();
		if(code.equals(HttpStatus.ACCEPTED)) {
			FarmerBean farmer = responsefromCustomerAtForget.getBody();
			String mobileNo = farmer.getMobileNo();
			String role = farmer.getRole();
			String tokenAndKey = createToken(mobileNo, role);
			String[] splittedTokenAndKey = tokenAndKey.split(",");
			String publicKeyJwkString = splittedTokenAndKey[1];
			String jwt = splittedTokenAndKey[0];
			tokendb.save(new UaaModel(publicKeyJwkString, jwt));
			return ResponseEntity.status(HttpStatus.CREATED).body(jwt);
		}	
		return new ResponseEntity<>(HttpStatus.CONFLICT);

	}

	public String createToken(String uname, String role) {
		RsaJsonWebKey rsaJsonWebKey = null;
		JsonWebKey jwk = null;
		// Create a new Json Web Encryption object
		JsonWebEncryption senderJwe = new JsonWebEncryption();
		try {
			rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
		} catch (JoseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Give the JWK a Key ID (kid), which is just the polite thing to do
		rsaJsonWebKey.setKeyId("k1");
		// Create the Claims, which will be the content of the JWT
		JwtClaims claims = null;
		// Create the Claims, which will be the content of the JWT
		claims = new JwtClaims();
		claims.setIssuer("Issuer"); // who creates the token and signs it
		claims.setAudience("Audience"); // to whom the token is intended to be sent
		claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
		claims.setGeneratedJwtId(); // a unique identifier for the token
		claims.setIssuedAtToNow(); // when the token was issued/created (now)
		claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
		claims.setSubject(role); // the subject/principal is whom the token is about
		claims.setClaim("username", uname); // additional claims/attributes about the subject can be added

		// A JWT is a JWS and/or a JWE with JSON claims as the payload.
		// In this example it is a JWS so we create a JsonWebSignature object.
		JsonWebSignature jws = new JsonWebSignature();
		// The payload of the JWS is JSON content of the JWT Claims
		jws.setPayload(claims.toJson());
		// The JWT is signed using the private key
		jws.setKey(rsaJsonWebKey.getPrivateKey());
		// Set the Key ID (kid) header because it's just the polite thing to do.
		// We only have one key in this example but a using a Key ID helps
		// facilitate a smooth key rollover process
		jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
		// Set the signature algorithm on the JWT/JWS that will integrity protect the
		// claims
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
		// Sign the JWS and produce the compact serialization or the complete JWT/JWS
		// representation, which is a string consisting of three dot ('.') separated
		// base64url-encoded parts in the form Header.Payload.Signature
		// If you wanted to encrypt it, you can simply set this jwt as the payload
		// of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
		String jwt = null;
		String jwkJson = null;
		try {
			jwt = jws.getCompactSerialization();
			// The shared secret or shared symmetric key represented as a octet sequence
			// JSON Web Key (JWK)
			jwkJson = "{\"kty\":\"oct\",\"k\":\"Fdh9u8rINxfivbrianbbVT1u232VQBZYKx1HGAGPt2I\"}";
			jwk = JsonWebKey.Factory.newJwk(jwkJson);
		} catch (JoseException e1) {
			e1.printStackTrace();
		}
		// A JSON string with only the public key info
		String publicKeyJwkString = rsaJsonWebKey.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);
		return jwt + "," + publicKeyJwkString;
	}

	@GetMapping("/verifytoken/{token}")
	public String verifyTokenComingFromService(@PathVariable String token) {
		// public key from token db
		Optional<UaaModel> userFound = tokendb.findById(token);
		if (!userFound.isPresent()) {
			return "false";
		}
		UaaModel uaaModel = userFound.get();
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
				return "true" + "," + jwtClaims.getSubject();
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
				}
			}

			// Or maybe the audience was invalid
			if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID)) {
				try {
					System.out.println("JWT had wrong audience: " + e.getJwtContext().getJwtClaims().getAudience());
				} catch (MalformedClaimException e1) {
					return "UnAuthorized";
				}
			}
			try {
				throw new Exception("UnAuthorized");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return "UnAuthorized";

	}
}