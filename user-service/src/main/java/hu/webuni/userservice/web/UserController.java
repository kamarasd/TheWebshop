package hu.webuni.userservice.web;

import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import hu.webuni.userservice.api.UserControllerApi;
import hu.webuni.userservice.api.model.LoginDto;
import hu.webuni.userservice.api.model.UserDto;
import hu.webuni.userservice.mapper.UserMapper;
import hu.webuni.userservice.model.Users;
import hu.webuni.userservice.repository.UserRepository;
import hu.webuni.userservice.security.JwtService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerApi {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	@Override
	public Optional<NativeWebRequest> getRequest() {
		// TODO Auto-generated method stub
		return UserControllerApi.super.getRequest();
	}
	
	@Override
	public ResponseEntity<UserDto> createFbUser(@Valid String facebookId, @Valid String password) {
		// TODO Auto-generated method stub
		return UserControllerApi.super.createFbUser(facebookId, password);
	}
	
	@Override
	public ResponseEntity<UserDto> createUser(@Valid String username, @Valid String password, @Valid String email) {
		if(Objects.isNull(userRepository.findByEmail(email))) {
			Users user = userRepository.save(Users.builder()
							.email(email)
							.password(passwordEncoder.encode(password))
							.username(username)
							.role("customer")
						.build());

			return ResponseEntity.ok(userMapper.userToDto(user));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@Override
	public ResponseEntity<String> userLogin(@Valid LoginDto loginDto) {
		System.out.print(loginDto);
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
			System.out.print(authentication);
			System.out.println("wqeqwe");
			return ResponseEntity.ok("\"" + jwtService.createJwtToken((UserDetails)authentication.getPrincipal()) + "\""); 
		
	}
}
