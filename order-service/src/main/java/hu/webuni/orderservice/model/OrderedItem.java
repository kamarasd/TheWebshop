package hu.webuni.orderservice.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import hu.webuni.orderservice.api.model.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class OrderedItem {
	
	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private Long id;
	private String productName;
	private Integer productPrice;
	private Integer piece;
	
	@ManyToOne
	private Orders orders;
	

}
