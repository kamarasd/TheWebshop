package hu.webuni.catalogservice.service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import hu.webuni.catalogservice.model.Catalog;
import hu.webuni.catalogservice.model.HistoryData;
import hu.webuni.catalogservice.model.PriceHistory;
import hu.webuni.catalogservice.model.QCatalog;
import hu.webuni.catalogservice.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;
    private final QuerydslPredicateArgumentResolver querydslPredicateArgumentResolver;
    private final PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver;
    private final NativeWebRequest nativeWebRequest;

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<HistoryData<Catalog>> getCatalogHistory(Long id) {
        List resultList = AuditReaderFactory
                .get(manager).createQuery()
                .forRevisionsOfEntity(Catalog.class, false, true)
                .add(AuditEntity.property("id").eq(id))
                .getResultList()
                .stream()
                    .map(obj -> {
                        Object[] objArray = (Object[])obj;
                        DefaultRevisionEntity revEnt = (DefaultRevisionEntity)objArray[1];
                            return new HistoryData<Catalog>(
                                    (Catalog)objArray[0],
                                    (RevisionType)objArray[2],
                                    revEnt.getId(),
                                    revEnt.getRevisionDate()
                            );
                    }).toList();

        return resultList;
    }

    @Transactional
    @Cacheable("catalogPriceCache")
    public List<PriceHistory> getPriceHistoryForItem(Long id) {
        List priceHistoryList = AuditReaderFactory
                .get(manager).createQuery()
                .forRevisionsOfEntity(Catalog.class, false, true)
                .add(AuditEntity.property("id").eq(id))
                .getResultList()
                .stream()
                .map(obj -> {
                    Object[] objArray = (Object[]) obj;
                    Catalog catalog = (Catalog) objArray[0];
                    DefaultRevisionEntity revEnt = (DefaultRevisionEntity) objArray[1];
                    return new PriceHistory(
                            revEnt.getRevisionDate(),
                            catalog.getProductPrice()
                    );
                }).toList();

        return priceHistoryList;
    }

    public Predicate createPredicate(String configMethodName) {
        try {
            Method method = this.getClass().getMethod(configMethodName, Predicate.class);
            MethodParameter methodParameter = new MethodParameter(method, 0);
            ModelAndViewContainer modelAndViewContainer = null;
            WebDataBinderFactory webDataBinderFactory = null;
            return (Predicate) querydslPredicateArgumentResolver.resolveArgument(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Pageable createPageable(String configMethodName) {
        try {
            Method method = this.getClass().getMethod(configMethodName, Pageable.class);
            MethodParameter methodParameter = new MethodParameter(method, 0);
            ModelAndViewContainer modelAndViewContainer = null;
            WebDataBinderFactory webDataBinderFactory = null;
            Pageable pageable = pageableHandlerMethodArgumentResolver.resolveArgument(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
            return pageable;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

    public void confPageable(@SortDefault("id") Pageable pageable) {}
    public void confPredicate(@QuerydslPredicate(root = Catalog.class) Predicate predicate) {}
    public void configurePredicate(@QuerydslPredicate(root = Catalog.class) Predicate predicate) {}
}
