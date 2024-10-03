package com.eventflowerexchange.dto.request;

import jakarta.validation.constraints.NotEmpty;

public class TypeRequestDTO {

    @NotEmpty(message = "Type name can't not be empty")
    private String name;
}
