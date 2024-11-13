package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.TypeRequestDTO;
import com.eventflowerexchange.entity.Type;
import com.eventflowerexchange.service.TypeService;
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
public class TypeAPI {
    private final TypeService typeService;

    @PostMapping("/api/admin/types")
    public ResponseEntity<?> createType(
            @Valid @RequestBody TypeRequestDTO typeRequestDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        typeService.createType(typeRequestDTO);
        return ResponseEntity.ok("Insert type successfully");
    }

    @GetMapping("/types")
    public ResponseEntity<List<Type>> getAllTypes() {
        List<Type> types = typeService.getAllTypes();
        return ResponseEntity.ok(types);
    }

    @PutMapping("/api/admin/types/{id}")
    public ResponseEntity<String> updateType(@PathVariable Long id,
                                                 @Valid @RequestBody TypeRequestDTO typeRequestDTO) {
        typeService.updateType(id, typeRequestDTO);
        return ResponseEntity.ok("Update Type with id = " +id + " successfully");

    }

    @DeleteMapping("/api/admin/types/{id}")
    public ResponseEntity<String> deleteType(@PathVariable Long id) {
        typeService.deleteType(id);
        return ResponseEntity.ok("Delete Type with id = " + id + " successfully");
    }

}


