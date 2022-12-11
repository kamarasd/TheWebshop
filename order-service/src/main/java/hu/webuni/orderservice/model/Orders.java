package hu.webuni.orderservice.model;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Builder
public class Orders {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    private Long Id;
    private Long userId;
    private String parcelno;
    private OrderStatuses orderStatus;
    private Integer priceSum;

    private Integer piece;

    @ManyToOne
    private Address address;
    
    private LocalDate deliveryDate;
    
   
}
