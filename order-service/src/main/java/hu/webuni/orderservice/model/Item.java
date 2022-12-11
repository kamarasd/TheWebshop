package hu.webuni.orderservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Data
@Entity
public class Item {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    private long id;

    private String productName;
    private int productPrice;
}
