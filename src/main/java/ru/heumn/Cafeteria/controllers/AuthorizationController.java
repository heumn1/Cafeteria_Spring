package ru.heumn.Cafeteria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class AuthorizationController {
    // сюда логин и регистрация!

    @GetMapping()
    public String login(@RequestParam(required = false) String error, Model model) {

        try {
            if (error.isEmpty()) {
                error = "Неправильный логин или пароль";
            }
        } catch (Exception ignored) {
        }

        model.addAttribute("userError", error);
        return "login";
    }
}
