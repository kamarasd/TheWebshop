package hu.webuni.catalogservice.model;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Audited
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
