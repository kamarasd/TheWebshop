package hu.webuni.orderservice.model;

import lombok.*;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Immutable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Immutable
public class Catalog {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    private long id;

    private String productName;
    private int productPrice;

    @ManyToOne
    private Category category;

}
