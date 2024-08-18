package com.aq.inventoryservice.util;

import com.aq.inventoryservice.model.Inventory;
import com.aq.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;

    @Override
    public void run(String... args) throws Exception {

        Inventory inventory = Inventory.builder()
            .skuCode("pixel_8a")
            .quantity(100)
            .build();

        Inventory inventory1 = Inventory.builder()
            .skuCode("pixel_8a_midnightBlack")
            .quantity(100)
            .build();

        Inventory inventory2 = Inventory.builder()
            .skuCode("pixel_8pro")
            .quantity(100)
            .build();

        Inventory inventory3 = Inventory.builder()
            .skuCode("pixel_8pro_midnightBlack")
            .quantity(100)
            .build();

        inventoryRepository.saveAll(Arrays.asList(inventory, inventory1, inventory2, inventory3));
    }
}
