package hu.webuni.userservice.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoggedUser extends User {
	
	private Users user;

	public LoggedUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Users user) {
		super(username, password, authorities);
		this.user = user;
	}
	
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
}
