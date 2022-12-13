package hu.webuni.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.orderservice.model.OrderedItem;

public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long>{

}
