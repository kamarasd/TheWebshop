package hu.webuni.userservice.security;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.interfaces.DecodedJWT;

import hu.webuni.userservice.config.UserConfigProperties;
import hu.webuni.userservice.model.LoggedUser;
import hu.webuni.userservice.model.Users;
import hu.webuni.userservice.repository.UserRepository;

@Service
public class JwtService {
	
	@Autowired
	UserRepository userRepository;

	private Algorithm alg;
	private String issuer;
	private String auth;
	private Integer logInMinutes;
	
	private static final String CUST_USERNAME = "username";
	private static final String CUST_ROLE = "role";
	
	@PostConstruct
	public void init() {
		issuer = "user-service";
		alg = (Algorithm) Algorithm.HMAC256("Salt");
		auth = "auth";
		logInMinutes = 1000;
	}

	public Builder createJwtToken(UserDetails principal) {
		System.out.println("564654");
		Builder jwt = JWT.create()
				.withSubject(principal.getUsername())
					.withArrayClaim(auth, principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new));
		
		Users user = ((LoggedUser) principal).getUser();
		System.out.println(principal.getUsername());
		if(user.getRole().contentEquals("customer")) {
			jwt.withClaim(CUST_USERNAME, user.getUsername());
			jwt.withClaim(CUST_ROLE, user.getRole());
		}
		
		jwt.withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(logInMinutes))).withIssuer(issuer).sign(alg);
			
		return jwt;
		
	}
	
	public UserDetails parseJwt(String jwtToken) {
		DecodedJWT decodedJWT = JWT.require(alg).withIssuer(issuer).build().verify(jwtToken);
		
		return new User(decodedJWT.getSubject(),
				"dummy",
				decodedJWT.getClaim(auth)
				.asList(String.class)
				.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList()));
	}
}
