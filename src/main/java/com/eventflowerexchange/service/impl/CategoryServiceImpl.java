package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.EventCategoryRequestDTO;
import com.eventflowerexchange.entity.Category;
import com.eventflowerexchange.repository.CategoryRepository;
import com.eventflowerexchange.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public void createCategory(EventCategoryRequestDTO categoryDTO) {
        Category newCategory = Category.builder()
                .name(categoryDTO.getName())
                .build(); // map đói tượng DTO thành category   ( nhớ là phải có @Builder )
        categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id).orElseThrow( () -> new RuntimeException("Category not found"));
        // đây là biểu thức lambda
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void updateCategory(long id, EventCategoryRequestDTO categoryDTO) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(categoryDTO.getName());   // category thì chỉ có mỗi trường name mà thôi
        categoryRepository.save(existingCategory);   // update xong phải save lại nhé
    }

    @Override
    public void deleteCategory(long id) {
        // xóa xong
        categoryRepository.deleteById(id);
    }
}
