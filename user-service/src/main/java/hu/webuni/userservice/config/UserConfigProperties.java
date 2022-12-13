package hu.webuni.userservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "user")
@Component
public class UserConfigProperties {

	private userConfigData userConf = new userConfigData();
	
	public userConfigData getUserConf() {
		return userConf;
	}

	public void setUserConf(userConfigData userConf) {
		this.userConf = userConf;
	}

	public static class userConfigData {
		
		private String auth;
		private String issuer;
		private String alg;
		private Integer minutesLoggedIn;
		
		public String getAuth() {
			return auth;
		}
		
		public void setAuth(String auth) {
			this.auth = auth;
		}
		
		public String getIssuer() {
			return issuer;
		}
		
		public void setIssuer(String issuer) {
			this.issuer = issuer;
		}
		
		public String getAlg() {
			return alg;
		}
		
		public void setAlg(String alg) {
			this.alg = alg;
		}
		
		public Integer getMinutesLoggedIn() {
			return minutesLoggedIn;
		}
		
		public void setMinutesLoggedIn(Integer minutesLoggedIn) {
			this.minutesLoggedIn = minutesLoggedIn;
		}
	}
}
