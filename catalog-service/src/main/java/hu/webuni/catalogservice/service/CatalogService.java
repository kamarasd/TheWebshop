package hu.webuni.catalogservice.service;

import hu.webuni.catalogservice.model.Catalog;
import hu.webuni.catalogservice.model.HistoryData;
import hu.webuni.catalogservice.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;

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

}
