package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.EventCategoryRequestDTO;
import com.eventflowerexchange.dto.request.TypeRequestDTO;
import com.eventflowerexchange.entity.Category;
import com.eventflowerexchange.entity.Type;
import com.eventflowerexchange.service.CategoryService;
import com.eventflowerexchange.service.JwtService;
import com.eventflowerexchange.service.TypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/types")
@RequiredArgsConstructor
public class TypeAPI {
    private final TypeService typeService;
    private final JwtService jwtService;

    @PostMapping("")
    // Nếu tham số truyền vào là 1 object thì sao??? => Data Transfer Object = Request Object
    // BindingResult bindingResult dùng cái này thì bắt validate tại hàm và trả về tên lỗi kèm theo vì dùng  @NotEmpty(message = "Category name can't not be empty") bên DTO
    // Đổi thành dấu chấm  ? vì kiểu trả về là có cả String và List of String nha
    public ResponseEntity<?> createType(
            @Valid @RequestBody TypeRequestDTO typeRequestDTO,
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
        // Tạo type
        typeService.createType(typeRequestDTO); // chỗ này là truyền vào nè.
        return ResponseEntity.ok("Insert type successfully");
    }

    // Hiển thị tất cả các categories.
    @GetMapping("")   //   http://localhost:8088/api/categories?page=1&limit=10
    public ResponseEntity<List<Type>> getAllTypes(  // đổi kiểu dữ liệu trả về
                                                    @RequestParam("page") int page,
                                                    @RequestParam("limit") int limit
    ) {
        List<Type> types = typeService.getAllTypes();   // lấy ra 1 cái list categoríes
        return ResponseEntity.ok(types);  // trả về client 1 chuỗi xe kiểu mỹ
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateType(@PathVariable Long id,
                                                 @Valid @RequestBody TypeRequestDTO typeRequestDTO) {

        typeService.updateType(id, typeRequestDTO);// truyền vào id vào 1 cái category mới

        return ResponseEntity.ok("Update Type with id = " +id + " successfully");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteType(@PathVariable Long id) {
        typeService.deleteType(id);
        return ResponseEntity.ok("Delete Type with id =     " + id + " successfully");
    }

}


