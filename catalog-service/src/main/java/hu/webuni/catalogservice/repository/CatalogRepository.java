package hu.webuni.catalogservice.repository;

import hu.webuni.catalogservice.model.Catalog;
import hu.webuni.catalogservice.model.QCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.Iterator;
import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Catalog, Long>, QuerydslPredicateExecutor<Catalog>,
        QuerydslBinderCustomizer<QCatalog>,  QueryDslWithEntityGraphRepository<Catalog, Long>{

    @Override
    default void customize(QuerydslBindings bindings, QCatalog catalog) {
        bindings.bind(catalog.productName).first((path, value) -> path.likeIgnoreCase(value));
        bindings.bind(catalog.category.categoryName).first((path, value) -> path.startsWith(value));

        bindings.bind(catalog.productPrice).all((path, values) -> {
            if(values.size() != 2) return Optional.empty();

            Iterator<? extends Integer> iterator = values.iterator();
            Integer priceFrom = iterator.next();
            Integer priceTo = iterator.next();

            return Optional.of(path.between(priceFrom, priceTo));
        });

    }

}
