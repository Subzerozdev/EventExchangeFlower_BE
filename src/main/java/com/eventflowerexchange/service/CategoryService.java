package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.EventCategoryRequestDTO;
import com.eventflowerexchange.entity.Category;

import java.util.List;

public interface CategoryService {
    void createCategory(EventCategoryRequestDTO category);
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    void updateCategory(long id, EventCategoryRequestDTO category);
    void deleteCategory(long id);
}
