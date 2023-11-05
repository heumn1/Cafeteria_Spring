package ru.heumn.Cafeteria.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.heumn.Cafeteria.dto.OrderDto;
import ru.heumn.Cafeteria.factories.OrderDtoFactory;
import ru.heumn.Cafeteria.storage.entities.OrderEntity;
import ru.heumn.Cafeteria.storage.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    OrderDtoFactory orderDtoFactory;
    @Autowired
    OrderRepository orderRepository;

    public void addOrder(OrderDto orderDto) {

        OrderEntity orderEntity = orderDtoFactory.makeOrderEntity(orderDto);

        orderRepository.save(orderEntity);
    }

}
