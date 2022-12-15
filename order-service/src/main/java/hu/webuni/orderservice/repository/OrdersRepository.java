package hu.webuni.orderservice.repository;

import hu.webuni.orderservice.model.Orders;
import hu.webuni.orderservice.model.Users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
	public Iterable<Orders> findByUserId(Long id);
	
	@Modifying
	@Query("DELETE OrderedItem oi WHERE oi.orders IN (SELECT o FROM Orders o WHERE o.id = :id)")
	public Integer deleteOrderedItems(Long id);
	
	@Query("SELECT o FROM Orders o WHERE o.userId = (SELECT u.id FROM Users u WHERE u.username = :username)")
	public List<Orders> findOrdersByUsername(String username);
}
