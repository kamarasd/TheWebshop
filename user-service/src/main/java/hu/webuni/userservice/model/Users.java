package hu.webuni.userservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Users {

	@Id
    @GeneratedValue
    @ToString.Include
    private Long id;
	
    private String email;
    private String username;
    private String password;
    private String facebookId;
}
