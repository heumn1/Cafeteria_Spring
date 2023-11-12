package ru.heumn.Cafeteria.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.heumn.Cafeteria.dto.TaskDto;
import ru.heumn.Cafeteria.factories.TaskDtoFactory;
import ru.heumn.Cafeteria.services.ProductService;
import ru.heumn.Cafeteria.storage.StatusOrder;
import ru.heumn.Cafeteria.storage.entities.ProductEntity;
import ru.heumn.Cafeteria.storage.entities.TaskEntity;
import ru.heumn.Cafeteria.storage.repository.ProductRepository;
import ru.heumn.Cafeteria.storage.repository.TaskRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/cook")
@PreAuthorize("hasAuthority('COOK_ROLE')")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CookController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskDtoFactory taskDtoFactory;

    @Autowired
    ProductService productService;

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/orders")
    public String getOrders(Model model){

        model.addAttribute("statusCook", StatusOrder.COOK);
        model.addAttribute("statusReady", StatusOrder.READY);

        List<TaskDto> taskDtoList = new ArrayList<>();

        taskDtoFactory.makeListDtoTask(taskRepository.findAll())
                        .forEach(taskDto -> {
                                    for (Map.Entry<ProductEntity, StatusOrder> product : taskDto.getProducts().entrySet()) {
                                        if (product.getValue() != StatusOrder.READY) {
                                            taskDtoList.add(taskDto);
                                            break;
                                        }
                                    }
                                });

        model.addAttribute("orders", taskDtoList);

        return "cook";
    }

    @GetMapping("/orders/{id}/{product}")
    public String getCookProduct(@PathVariable Integer id, @PathVariable String product, Principal principal){

        productService.confirmProduct(id,product);

        return "redirect:/cook/orders";
    }

}
