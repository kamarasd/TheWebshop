package hu.webuni.catalogservice.repository;

import hu.webuni.catalogservice.model.Catalog;
import hu.webuni.catalogservice.model.QCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Catalog, Long>, QuerydslPredicateExecutor<Catalog>,
        QuerydslBinderCustomizer<QCatalog>,  QueryDslWithEntityGraphRepository<Catalog, Long>{

    @Override
    default void customize(QuerydslBindings bindings, QCatalog catalog) {

    }

    Optional<Catalog> findCatalogsById(Long id);
}
