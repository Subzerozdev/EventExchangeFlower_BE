package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.EventCategoryRequestDTO;
import com.eventflowerexchange.entity.Category;
import com.eventflowerexchange.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class CategoryAPI {
    private final CategoryService categoryService;

    // Nếu tham số truyền vào là 1 object thì sao??? => Data Transfer Object = Request Object
    // BindingResult bindingResult dùng cái này thì bắt validate tại hàm và trả về tên lỗi kèm theo vì dùng  @NotEmpty(message = "Category name can't not be empty") bên DTO
    // Đổi thành dấu chấm  ? vì kiểu trả về là có cả String và List of String nha
    @PostMapping("api/admin/categories")
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody EventCategoryRequestDTO categoryDTO,
            BindingResult result
    ) {
        // Kiểm tra lỗi
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        // Tạo Category
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("Insert category successfully");
    }

    // Hiển thị tất cả các categories.
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("api/admin/categories/{id}")
    public ResponseEntity<String> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody EventCategoryRequestDTO categoryDTO
    ) {
        categoryService.updateCategory(id, categoryDTO);// truyền vào id vào 1 cái category mới
        return ResponseEntity.ok("Update category successfully");
    }

    @DeleteMapping("api/admin/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete Category with id =     " + id + " successfully");
    }
}
