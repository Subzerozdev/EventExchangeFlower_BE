package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.EventCategoryRequestDTO;
import com.eventflowerexchange.entity.Category;
import com.eventflowerexchange.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {
    Category createCategory(EventCategoryRequestDTO category);

    Category getCategoryById(long id);

    List<Category> getAllCategories();

    Category updateCategory(long id, EventCategoryRequestDTO category);

    void deleteCategory(long id);
}
