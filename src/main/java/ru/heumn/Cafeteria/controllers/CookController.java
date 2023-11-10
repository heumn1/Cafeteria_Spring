package ru.heumn.Cafeteria.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.heumn.Cafeteria.factories.TaskDtoFactory;
import ru.heumn.Cafeteria.storage.repository.TaskRepository;

@Controller
@RequestMapping("/cook")
@PreAuthorize("hasAuthority('COOK_ROLE')")
public class CookController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskDtoFactory taskDtoFactory;

    @GetMapping("/orders")
    public String getOrders(Model model){

        model.addAttribute("orders", taskDtoFactory.makeListDtoTask(taskRepository.findAll()));

        return "cook";
    }




}
