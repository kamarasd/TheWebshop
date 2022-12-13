package hu.webuni.orderservice.repository;

import hu.webuni.orderservice.model.Orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
	public Iterable<Orders> findByUserId(Long id);
}
