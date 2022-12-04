package hu.webuni.catalogservice.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface QueryDslWithEntityGraphRepository<T, ID> {
   // List<T> findAll(Predicate predicate, String graphName, EntityGraph.EntityGraphType type, Sort sort);
}
