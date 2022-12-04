package hu.webuni.catalogservice.mapper;

import hu.webuni.catalogservice.api.model.CatalogDto;
import hu.webuni.catalogservice.api.model.HistoryDataDto;
import hu.webuni.catalogservice.model.Catalog;
import hu.webuni.catalogservice.model.HistoryData;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CatalogMapper {

    CatalogDto catalogToDto(Catalog catalog);

    Catalog dtoToCatalog(CatalogDto catalogDto);

    List<CatalogDto> catalogsToDtos(Iterable<Catalog> catalogs);

    @Named("summary")
    CatalogDto catalogSummaryToDto(Catalog catalog);

    @IterableMapping(qualifiedByName = "summary")
    List<CatalogDto> catalogSummariesToDto(Iterable<Catalog> findAll);

    List<HistoryDataDto> catalogHistoryToHistoryDto(List<HistoryData<Catalog>> historyData);
}
