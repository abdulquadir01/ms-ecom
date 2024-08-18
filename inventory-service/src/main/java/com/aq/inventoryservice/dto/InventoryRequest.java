package com.aq.inventoryservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequest {

    private String skuCode;
    private Integer quantity;
}
