package ru.heumn.Cafeteria.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelExtensionsKt;
import org.springframework.web.bind.annotation.*;
import ru.heumn.Cafeteria.dto.OrderDto;
import ru.heumn.Cafeteria.factories.OrderDtoFactory;
import ru.heumn.Cafeteria.factories.ProductDtoFactory;
import ru.heumn.Cafeteria.services.OrderService;
import ru.heumn.Cafeteria.storage.ChatMessage;
import ru.heumn.Cafeteria.storage.ProductCategory;
import ru.heumn.Cafeteria.storage.StatusOrder;
import ru.heumn.Cafeteria.storage.TestEntity;
import ru.heumn.Cafeteria.storage.repository.OrderRepository;
import ru.heumn.Cafeteria.storage.repository.ProductRepository;
import ru.heumn.Cafeteria.storage.repository.UserRepository;

import java.security.Principal;
import java.time.Instant;

@Controller
@RequestMapping("/cook")
@PreAuthorize("hasAuthority('COOK_ROLE')")
public class CookController {



    @GetMapping("/orders")
    public String getOrders(Model model){

//        model.addAttribute("orders", orderDtoFactory.makeOrderDtoList(orderRepository.findAll())
//                .stream()
//                .filter(orderDto -> orderDto.getStatus() == StatusOrder.COOK)
//
//        );

        return "cook";
    }




}
