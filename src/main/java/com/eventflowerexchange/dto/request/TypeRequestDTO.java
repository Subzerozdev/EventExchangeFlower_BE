package com.eventflowerexchange.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeRequestDTO {

    @NotEmpty(message = "Type name can't not be empty")
    private String name;
}
