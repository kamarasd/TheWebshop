package hu.webuni.orderservice.model;

import lombok.*;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Immutable
public class Category {

    @Id
    @GeneratedValue
    private long id;

    private String categoryName;
}

