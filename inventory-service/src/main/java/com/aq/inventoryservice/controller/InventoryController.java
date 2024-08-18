package com.aq.inventoryservice.controller;

import com.aq.inventoryservice.dto.InventoryResponse;
import com.aq.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String skuCode) {
        return inventoryService.isInStock(skuCode);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCodes) {
        return inventoryService.isInStock(skuCodes);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public String addedToInventory(@RequestBody InventoryRequest inventoryRequest){
//        return "Added to inventory";
//    }

}
