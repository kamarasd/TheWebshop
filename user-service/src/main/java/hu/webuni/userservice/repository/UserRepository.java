package hu.webuni.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.userservice.model.Users;

public interface UserRepository extends JpaRepository<Users, Long>{
	
	
	Users findByEmail(String email);

}
