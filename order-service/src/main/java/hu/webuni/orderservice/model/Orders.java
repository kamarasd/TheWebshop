package hu.webuni.orderservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


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
    private OrderStatuses orderStatus;
    private Integer priceSum;

    private Integer piece;


    private String items;

    @ManyToOne
    private Address address;
}
