package com.aq.inventoryservice.service;

import com.aq.inventoryservice.dto.InventoryResponse;
import com.aq.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes) {
        log.info("Wait started...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        log.info("Wait ended...");
        return inventoryRepository.findBySkuCodeIn(skuCodes)
            .stream()
            .map(inventory -> InventoryResponse.builder()
                .skuCode((inventory.getSkuCode()))
                .isInStock(inventory.getQuantity() > 0)
                .build()
            ).toList();
    }

}
