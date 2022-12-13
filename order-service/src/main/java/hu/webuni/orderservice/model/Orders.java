package hu.webuni.orderservice.model;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Builder

@NamedEntityGraph(
		name = "Orders" , attributeNodes =
				@NamedAttributeNode(value = "orderItem", subgraph = "orderItemSubgraph")
)
public class Orders {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    private Long id;
    private Long userId;
    private String parcelno;
    private OrderStatuses orderStatus;
    private Integer priceSum;

    private Integer piecesSum;

    @ManyToOne
    private Address address;
    
    private LocalDate deliveryDate;  
    
    /*@ManyToOne
    private OrderedItem orderedItems;*/
    
    @OneToMany(mappedBy = "orders", fetch = FetchType.EAGER)
    private List<OrderedItem> orderItem;
    
    public List<OrderedItem> getOrderedItemList() {
		return orderItem;
	}
    
    public void setOrderedItemList(List<OrderedItem> orderItem) {
		this.orderItem = orderItem;
	}
    
    public void addOrders(OrderedItem orderItem) {
    	orderItem.setOrders(this);
		if(Objects.isNull(this.orderItem) || this.orderItem == null) {
			this.orderItem = new ArrayList<>();
		}
		this.orderItem.add(orderItem);
	}
   
}
