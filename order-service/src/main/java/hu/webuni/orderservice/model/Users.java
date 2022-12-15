package hu.webuni.orderservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
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
    private String role;
}
