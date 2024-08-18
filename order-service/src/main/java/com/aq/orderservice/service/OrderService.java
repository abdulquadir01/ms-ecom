package com.aq.orderservice.service;

import com.aq.orderservice.dto.InventoryResponse;
import com.aq.orderservice.dto.OrderLineItemDto;
import com.aq.orderservice.dto.OrderRequest;
import com.aq.orderservice.model.Order;
import com.aq.orderservice.model.OrderLineItem;
import com.aq.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        String orderPlacedMsg = "Couldn't Place Order!!";

        List<OrderLineItem> orderLineItemList = orderRequest.getOrderLineItemDtoList()
            .stream()
            .map(this::mapOrderLineItemsToDto)
            .toList();

        order.setOrderLineItems(orderLineItemList);

        List<String> skuCodes = order.getOrderLineItems()
            .stream()
            .map(OrderLineItem::getSkuCode)
            .toList();

//        TBD- call inventory service & place order if product is in stock  //done
        InventoryResponse[] inventoryResponseArr = webClient
            .get()
            .uri("http://localhost:8082/api/inventory",
                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
            .retrieve()
            .bodyToMono(InventoryResponse[].class)
            .block();

        if (inventoryResponseArr != null) {
            boolean allProductInStock = Arrays.stream(inventoryResponseArr)
                .allMatch(InventoryResponse::isInStock);

            if (allProductInStock) {
                orderRepository.save(order);
                log.info("Order saved with id = {}", order.getId());
                orderPlacedMsg = "Order Placed Successfully!!";
            } else {
                throw new IllegalArgumentException("Product is not in stock, please try again later");
            }
        }

        return orderPlacedMsg;
    }

    private OrderLineItem mapOrderLineItemsToDto(OrderLineItemDto orderLineItemDto) {
        return OrderLineItem.builder()
            .skuCode(orderLineItemDto.getSkuCode())
            .price(orderLineItemDto.getPrice())
            .quantity(orderLineItemDto.getQuantity())
            .build();
    }

}
