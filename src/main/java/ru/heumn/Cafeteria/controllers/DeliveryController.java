package ru.heumn.Cafeteria.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.heumn.Cafeteria.storage.dto.TaskDto;
import ru.heumn.Cafeteria.factories.TaskDtoFactory;
import ru.heumn.Cafeteria.services.OrderService;
import ru.heumn.Cafeteria.storage.enums.StatusOrder;
import ru.heumn.Cafeteria.storage.entities.ProductEntity;
import ru.heumn.Cafeteria.storage.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/delivery")
@PreAuthorize("hasAuthority('MANAGER_ROLE')")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryController {

    @Autowired
    TaskDtoFactory taskDtoFactory;
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    OrderService orderService;

    @GetMapping()
    public String orders(Model model){

        List<TaskDto> taskDtoList = new ArrayList<>();

        taskDtoFactory.makeListDtoTask(taskRepository.findAll())
                .forEach(taskDto -> {
                    boolean allReady = false;
                    for (Map.Entry<ProductEntity, StatusOrder> product : taskDto.getProducts().entrySet()) {
                        if (product.getValue() != StatusOrder.READY) {
                            allReady = false;
                            break;
                        }
                        else {
                            allReady = true;
                        }
                    }
                    if(allReady)
                    {
                        taskDtoList.add(taskDto);
                    }
                });

        model.addAttribute("orders", taskDtoList);

        return "delivery";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(@PathVariable Integer id){

        orderService.finishOrder(id);
        return "redirect:/delivery";
    }
}
