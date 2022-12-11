package hu.webuni.orderservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Address {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    private Long id;

    private String zipCode;
    private String street;
    private String houseNo;
    private String cityName;
}
