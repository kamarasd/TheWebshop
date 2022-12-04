package hu.webuni.catalogservice.web;


import hu.webuni.catalogservice.api.CatalogControllerApi;
import hu.webuni.catalogservice.api.model.CatalogDto;
import hu.webuni.catalogservice.api.model.HistoryDataDto;
import hu.webuni.catalogservice.mapper.CatalogMapper;
import hu.webuni.catalogservice.model.Catalog;
import hu.webuni.catalogservice.model.HistoryData;
import hu.webuni.catalogservice.repository.CatalogRepository;
import hu.webuni.catalogservice.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CatalogController implements CatalogControllerApi {

    private final CatalogRepository catalogRepository;
    private final CatalogMapper catalogMapper;
    private final NativeWebRequest nativeWebRequest;

    private final CatalogService catalogService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(nativeWebRequest);
    }

    @Override
    public ResponseEntity<CatalogDto> addNewItemToCatalog(CatalogDto catalogDto) {
        System.out.println(catalogMapper.dtoToCatalog(catalogDto));
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
    public ResponseEntity<List<CatalogDto>> searchCategory(Boolean all, String page, String size, String sort) {
        return CatalogControllerApi.super.searchCategory(all, page, size, sort);
    }

    @Override
    public ResponseEntity<List<HistoryDataDto>> getCatalogHistory(Long id) {
        catalogRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<HistoryData<Catalog>> catalogHistory = catalogService.getCatalogHistory(id);
        return ResponseEntity.ok(catalogMapper.catalogHistoryToHistoryDto(catalogHistory));
    }
}
