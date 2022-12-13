package hu.webuni.userservice.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import hu.webuni.userservice.api.UserControllerApi;
import hu.webuni.userservice.api.model.UserDto;
import hu.webuni.userservice.mapper.UserMapper;
import hu.webuni.userservice.model.Users;
import hu.webuni.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerApi {
	
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	
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
		if(userRepository.findByEmail(email).isEmpty()) {
			Users user = userRepository.save(Users.builder()
							.email(email).password(passwordEncoder.encode(password)).username(username).build());

			return ResponseEntity.ok(userMapper.userToDto(user));
		} else {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
		}
	}
}
