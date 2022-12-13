package hu.webuni.catalogservice.web;


import com.querydsl.core.Query;
import com.querydsl.core.types.Predicate;
import hu.webuni.catalogservice.api.CatalogControllerApi;
import hu.webuni.catalogservice.api.model.CatalogDto;
import hu.webuni.catalogservice.api.model.HistoryDataDto;
import hu.webuni.catalogservice.api.model.PriceHistoryDto;
import hu.webuni.catalogservice.mapper.CatalogMapper;
import hu.webuni.catalogservice.model.Catalog;
import hu.webuni.catalogservice.model.HistoryData;
import hu.webuni.catalogservice.model.PriceHistory;
import hu.webuni.catalogservice.repository.CatalogRepository;
import hu.webuni.catalogservice.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CatalogController implements CatalogControllerApi {

    private final CatalogRepository catalogRepository;
    private final CatalogMapper catalogMapper;
    private final NativeWebRequest nativeWebRequest;
    private final CatalogService catalogService;
    private final PageableHandlerMethodArgumentResolver pageResolver;



    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(nativeWebRequest);
    }

    @Override
    public ResponseEntity<CatalogDto> addNewItemToCatalog(CatalogDto catalogDto) {
        Catalog catalog = catalogRepository.save(catalogMapper.dtoToCatalog(catalogDto));
        return ResponseEntity.ok(catalogMapper.catalogToDto(catalog));
    }

    @Override
    public ResponseEntity<Void> deleteItemFromCatalog(Long id) {
        catalogRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<CatalogDto>> getCatalogItems() {
        List<Catalog> catalog = catalogRepository.findAll();
        return ResponseEntity.ok(catalogMapper.catalogsToDtos(catalog));
    }

    @Override
    public ResponseEntity<CatalogDto> modifyItemById(Long id, CatalogDto catalogDto) {
       catalogRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
       catalogDto.setId(id);
       Catalog catalog = catalogRepository.save(catalogMapper.dtoToCatalog(catalogDto));
       return ResponseEntity.ok(catalogMapper.catalogToDto(catalog));

    }

    @Override
    public ResponseEntity<List<HistoryDataDto>> getCatalogHistory(Long id) {
        List<HistoryData<Catalog>> catalogHistory = catalogService.getCatalogHistory(id);
        List<HistoryData<CatalogDto>> catalogWithHistory = new ArrayList<>();

        catalogHistory.forEach(historyData -> {
            catalogWithHistory.add(new HistoryData<>(
                    catalogMapper.catalogToDto(historyData.getData()),
                    historyData.getRevType(),
                    historyData.getRevision(),
                    historyData.getRevDate()
            ));
        });

        return ResponseEntity.ok(catalogMapper.catalogHistoryToHistoryDto(catalogHistory));
    }

    @Override
    public ResponseEntity<List<PriceHistoryDto>> gePriceHistoryFromCatalog(Long id) {
        List<PriceHistory> priceHistory = catalogService.getPriceHistoryForItem(id);
        return ResponseEntity.ok(catalogMapper.catalogPriceHistoryToPriceHistoryDto(priceHistory));
    }

    @Override
    public ResponseEntity<List<CatalogDto>> getCatalogWithCriteria(Long id, String productName, List<String> productPrice, String categoryCategoryName, Integer page, Integer size, List<String> sort) {

        Predicate predicate = catalogService.createPredicate("configurePredicate");
        Pageable pageable = catalogService.createPageable("confPageable");

        return ResponseEntity.ok(catalogMapper.catalogsToDtos(catalogRepository.findAll(predicate, pageable)));
    }

}
