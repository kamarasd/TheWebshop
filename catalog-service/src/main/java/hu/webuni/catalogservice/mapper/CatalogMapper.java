package hu.webuni.catalogservice.mapper;

import hu.webuni.catalogservice.api.model.CatalogDto;
import hu.webuni.catalogservice.api.model.HistoryDataDto;
import hu.webuni.catalogservice.model.Catalog;
import hu.webuni.catalogservice.model.HistoryData;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CatalogMapper {

    CatalogDto catalogToDto(Catalog catalog);

    Catalog dtoToCatalog(CatalogDto catalogDto);

    List<CatalogDto> catalogsToDtos(Iterable<Catalog> catalogs);

    List<HistoryDataDto> catalogHistoryToHistoryDto(List<HistoryData<Catalog>> historyData);
}
