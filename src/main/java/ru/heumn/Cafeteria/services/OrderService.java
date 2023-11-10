package ru.heumn.Cafeteria.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.heumn.Cafeteria.dto.OrderDto;
import ru.heumn.Cafeteria.factories.OrderDtoFactory;
import ru.heumn.Cafeteria.storage.ChatMessage;
import ru.heumn.Cafeteria.storage.PaymentMethod;
import ru.heumn.Cafeteria.storage.Role;
import ru.heumn.Cafeteria.storage.StatusOrder;
import ru.heumn.Cafeteria.storage.entities.NumberOrderEntity;
import ru.heumn.Cafeteria.storage.entities.OrderEntity;
import ru.heumn.Cafeteria.storage.entities.ProductEntity;
import ru.heumn.Cafeteria.storage.entities.TaskEntity;
import ru.heumn.Cafeteria.storage.repository.NumberOrderRepository;
import ru.heumn.Cafeteria.storage.repository.OrderRepository;
import ru.heumn.Cafeteria.storage.repository.TaskRepository;
import ru.heumn.Cafeteria.storage.repository.UserRepository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderDtoFactory orderDtoFactory;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    NumberOrderRepository numberOrderRepository;

    @Autowired
    private SimpMessagingTemplate template;

    public void addOrder(OrderDto orderDto) {

        OrderEntity orderEntity = orderDtoFactory.makeOrderEntity(orderDto);

        orderRepository.save(orderEntity);
    }

    public Boolean acceptOrder(Long id, PaymentMethod paymentMethod, String principal){
        Optional<OrderEntity> orderEntity =  orderRepository.findById(id);

        if(orderEntity.isPresent())
        {
            if(orderEntity.get().getSeller() == userRepository.findByLogin(principal)
                    || userRepository.findByLogin(principal).getRoles().contains(Role.ADMIN_ROLE))
            {
                orderEntity.get().setStatus(StatusOrder.ACCEPTED);

                orderEntity.get().setPaymentMethod(paymentMethod);
                orderRepository.save(orderEntity.get());

                Integer numberOfOrder;
                Optional<NumberOrderEntity> numberOrder = numberOrderRepository.findById(1L);

                if(numberOrder.isPresent())
                {
                    if(numberOrder.get().getNumberOrder() == null)
                    {
                        numberOrder.get().setNumberOrder(1);
                        numberOrderRepository.save(numberOrder.get());

                        numberOfOrder = 1;
                    }
                    else {
                        numberOfOrder = numberOrder.get().getNumberOrder();
                        numberOrder.get().setNumberOrder(numberOrder.get().getNumberOrder()+1);

                        if(numberOrder.get().getNumberOrder() > 1000)
                        {
                            numberOrder.get().setNumberOrder(1);
                        }

                        numberOrderRepository.save(numberOrder.get());
                    }
                }
                else {
                    numberOrderRepository.save(NumberOrderEntity.builder().Id(1L).numberOrder(1).build());
                    numberOfOrder = 1;
                }

                Map<ProductEntity,StatusOrder> products = new LinkedHashMap<>();
                orderEntity.get().getProducts()
                        .forEach(productEntity -> {products.put(productEntity, StatusOrder.GIVEN);});

                taskRepository.save(TaskEntity.builder()
                        .id(null)
                        .numberOrder(numberOfOrder)
                        .products(products)
                        .build());

                sendMessageOnCookPage();

                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    private void sendMessageOnCookPage(){

        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setType("Cook");
        chatMessage.setSender("Server");
        chatMessage.setContent("Поступил новый заказ");

        template.convertAndSend("/topic/public",chatMessage);
    }

}
