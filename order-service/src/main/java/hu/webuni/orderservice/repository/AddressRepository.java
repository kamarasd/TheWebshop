package hu.webuni.orderservice.repository;

import hu.webuni.orderservice.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}