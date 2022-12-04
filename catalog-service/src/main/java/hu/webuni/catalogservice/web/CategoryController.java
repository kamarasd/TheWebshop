package hu.webuni.catalogservice.web;

import hu.webuni.catalogservice.api.CategoryControllerApi;
import hu.webuni.catalogservice.api.model.CategoryDto;
import hu.webuni.catalogservice.mapper.CategoryMapper;
import hu.webuni.catalogservice.model.Category;
import hu.webuni.catalogservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryControllerApi {

    private final NativeWebRequest nativeWebRequest;
    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(nativeWebRequest);
    }

    @Override
    public ResponseEntity<CategoryDto> addNewCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.save(categoryMapper.dtoToCategory(categoryDto));
        return ResponseEntity.ok(categoryMapper.categoryToDto(category));
    }

    @Override
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categoryMapper.categoriesToDtos(categories));
    }
}
