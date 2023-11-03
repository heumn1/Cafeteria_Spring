package ru.heumn.Cafeteria.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping()
    public String index(){
        return "index";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
