package hu.webuni.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.orderservice.model.Catalog;

public interface CatalogRepository extends JpaRepository<Catalog, Long>{

}
