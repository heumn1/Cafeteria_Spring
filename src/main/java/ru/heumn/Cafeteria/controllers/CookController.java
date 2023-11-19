package ru.heumn.Cafeteria.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.heumn.Cafeteria.storage.dto.TaskDto;
import ru.heumn.Cafeteria.factories.TaskDtoFactory;
import ru.heumn.Cafeteria.services.ProductService;
import ru.heumn.Cafeteria.storage.enums.StatusOrder;
import ru.heumn.Cafeteria.storage.entities.ProductEntity;
import ru.heumn.Cafeteria.storage.repository.TaskRepository;
import ru.heumn.Cafeteria.storage.repository.UserRepository;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

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
    UserRepository userRepository;

    @Autowired
    ProductService productService;

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/orders")
    public String getOrders(Model model){

        model.addAttribute("statusCook", StatusOrder.COOK);
        model.addAttribute("statusReady", StatusOrder.READY);

        List<TaskDto> taskDtoList;

        taskDtoList = taskDtoFactory.makeListDtoTask(taskRepository.findAll());

        taskDtoList = taskDtoList.stream()
                .filter(taskDto -> {
                    return taskDto.getProducts().containsValue(StatusOrder.GIVEN);
                }).collect(Collectors.toList());

        model.addAttribute("orders", taskDtoList);

        return "cook";
    }

    @GetMapping("/orders/{id}/{product}")
    public String getCookProduct(@PathVariable Integer id, @PathVariable String product, Principal principal){

        productService.confirmProduct(id,product,principal);

        return "redirect:/cook/orders";
    }

    @GetMapping("/orders/personal")
    public String getCookPersonal(Model model, Principal principal){

        List<TaskDto> taskDtoList = taskDtoFactory.makeListDtoTask(taskRepository.findAll());

        final Long[] count = {0L};

        Map<Long,  Map.Entry<Integer, ProductEntity>> productEntityHashMap = new LinkedHashMap<>();

        taskDtoList
                .forEach(taskDto -> {
                    for (Map.Entry<ProductEntity, Long> cook : taskDto.getCooks().entrySet())
                    {
                       if(cook.getValue().equals(userRepository.findByLogin(principal.getName()).getId())
                               && taskDto.getProducts().get(cook.getKey()) != StatusOrder.READY)
                       {
                           count[0]++;
                           productEntityHashMap.put(count[0],new AbstractMap.SimpleEntry<>(taskDto.getNumberOrder(), cook.getKey()));
                       }
                    }
                });

        model.addAttribute("orders", productEntityHashMap);

        return "orderPersonal";
    }

}
