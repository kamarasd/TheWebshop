package hu.webuni.userservice.web;

import java.util.Map;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FacebookLoginController {

	@RequestMapping("/fbLoginSuccess")
	public String onFacebookLoginSuccess(Map<String, Object> model, OAuth2AuthenticationToken authenticationToken) { 
		
		String fullName = authenticationToken.getPrincipal().getAttribute("name");
		String email = authenticationToken.getPrincipal().getAttribute("email");
		
		System.out.println(fullName);
		System.out.println(email);
		return null;
	}
}
