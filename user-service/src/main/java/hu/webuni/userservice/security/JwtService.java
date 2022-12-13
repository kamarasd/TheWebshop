package hu.webuni.userservice.security;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.interfaces.DecodedJWT;

import hu.webuni.userservice.config.UserConfigProperties;
import hu.webuni.userservice.model.Users;
import hu.webuni.userservice.repository.UserRepository;

@Service
public class JwtService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserConfigProperties userConfProp;

	private Algorithm alg;
	private String issuer;
	private String auth;
	private Integer logInMinutes;
	
	@PostConstruct
	public void init() {
		issuer = userConfProp.getUserConf().getIssuer();
		alg = (Algorithm) Algorithm.HMAC256("Salt");
		auth = userConfProp.getUserConf().getAuth();
		logInMinutes = userConfProp.getUserConf().getMinutesLoggedIn();
	}
	
	private static final String SUP_EMP_NAME = "superiored_user_full_name";
	private static final String SUP_EMP_ID = "superiored_user_id";
	
	private static final String EMP_NAME = "user_full_name";
	private static final String EMP_USER = "username";
	private static final String EMP_ID = "user_id";

	public String createJwtToken(UserDetails principal) {
		Builder jwt = JWT.create().withSubject(principal.getUsername())
		.withArrayClaim(auth, principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new));
		/*
		Employee employee = ((HrUser) principal).getEmployee();
		if(employee.getPosition().getPosName().contentEquals("Superior")) {
			List<Employee> superioredEmployees = employeeRepository.findBySuperior(employee.getName());
			if(!superioredEmployees.isEmpty()) {
				jwt.withArrayClaim(SUP_EMP_ID, superioredEmployees.stream().map(Employee::getEmployeeId).toArray(Long[]::new))
				.withArrayClaim(SUP_EMP_NAME, superioredEmployees.stream().map(Employee::getName).toArray(String[]::new));
			}
		}
		
		jwt.withClaim(EMP_ID, employee.getEmployeeId());
		jwt.withClaim(EMP_USER, employee.getUsername());
		jwt.withClaim(EMP_NAME, employee.getName());*/
		
		return jwt.withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(logInMinutes))).withIssuer(issuer)
				.sign(alg);
		
	}
	
	public UserDetails parseJwt(String jwtToken) {
		DecodedJWT decodedJwt = JWT.require(alg)
		.withIssuer(issuer)
		.build()
		.verify(jwtToken);
		
		Users user = new Users();
		/*employee.setEmployeeId(decodedJwt.getClaim(EMP_ID).asLong());
		employee.setName(decodedJwt.getClaim(EMP_NAME).asString());
		employee.setUsername(decodedJwt.getClaim(EMP_USER).asString());*/
		
		/*return new HrUser(decodedJwt.getSubject(), "dumdum", 
				decodedJwt.getClaim(auth)
				.asList(String.class)
				.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList()), employee);*/
		return null;
	}
}
