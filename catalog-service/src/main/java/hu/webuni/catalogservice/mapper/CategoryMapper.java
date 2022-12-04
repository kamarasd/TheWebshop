package hu.webuni.catalogservice.mapper;

import hu.webuni.catalogservice.api.model.CategoryDto;
import hu.webuni.catalogservice.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto categoryToDto(Category category);

    Category dtoToCategory(CategoryDto categoryDto);

    List<CategoryDto> categoriesToDtos(Iterable<Category> category);
}
