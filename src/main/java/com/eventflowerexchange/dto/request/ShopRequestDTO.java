package com.eventflowerexchange.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopRequestDTO {
    private String shopName;
    private String description;
    private String shopAddress;
}
