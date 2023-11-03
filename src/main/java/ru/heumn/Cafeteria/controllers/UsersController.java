package ru.heumn.Cafeteria.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.heumn.Cafeteria.services.UserService;
import ru.heumn.Cafeteria.storage.Role;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN_ROLE')")
public class UsersController {
    @Autowired
    UserService userService;

    @GetMapping()
    public String main(Model model) {
        model.addAttribute("users", userService.getAllUsers());

        return "users";
    }

    @GetMapping("/settings")
    public String userSettings(Model model) {
        model.addAttribute("roleCook", Role.COOK_ROLE);
        model.addAttribute("roleSeller", Role.SELLER_ROLE);
        model.addAttribute("roleAdmin", Role.ADMIN_ROLE);
        model.addAttribute("roleManager", Role.MANAGER_ROLE);

        model.addAttribute("settings", "Настройки");

        model.addAttribute("users", userService.getAllUsers());

        return "users";
    }

    @PostMapping("/settings/setrole")
    public String userSetRole(@RequestParam(value = "id", required = true) Long id,
                              @RequestParam(value = "role", required = true) Role role) {

        userService.setRoleForUser(id, role);

        return "redirect:/users/settings";
    }

    @PostMapping("/settings/deleterole")
    public String userDeleteRole(@RequestParam(value = "id", required = true) Long id,
                              @RequestParam(value = "role", required = true) Role role) {

        userService.DeleteRoleForUser(id, role);

        return "redirect:/users/settings";
    }
}
