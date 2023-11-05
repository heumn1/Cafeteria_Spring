package ru.heumn.Cafeteria.factories;

import org.springframework.stereotype.Component;
import ru.heumn.Cafeteria.dto.OrderDto;
import ru.heumn.Cafeteria.storage.entities.OrderEntity;

@Component
public class OrderDtoFactory {

    public OrderDto makeDtoOrder(OrderEntity orderEntity){
        return OrderDto.builder()
                .id(orderEntity.getId())
                .products(orderEntity.getProducts())
                .dateCreate(orderEntity.getDateCreate())
                .paymentMethod(orderEntity.getPaymentMethod())
                .price(orderEntity.getPrice())
                .build();
    }

    public OrderEntity makeOrderEntity(OrderDto orderDto){
        return OrderEntity.builder()
                .id(orderDto.getId())
                .products(orderDto.getProducts())
                .dateCreate(orderDto.getDateCreate())
                .seller(orderDto.getSeller())
                .paymentMethod(orderDto.getPaymentMethod())
                .price(orderDto.getPrice())
                .build();
    }
}
