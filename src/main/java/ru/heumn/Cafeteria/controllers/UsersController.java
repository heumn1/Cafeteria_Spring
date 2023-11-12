package ru.heumn.Cafeteria.controllers;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.heumn.Cafeteria.dto.UserDto;
import ru.heumn.Cafeteria.services.UserService;
import ru.heumn.Cafeteria.storage.Role;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN_ROLE')")
@FieldDefaults(level = AccessLevel.PRIVATE)
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

    @GetMapping("/add")
    public String addUserPage(@ModelAttribute("user") UserDto userDto){

        return "addUser";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult, Model model){

        System.out.println(userDto);

        if(bindingResult.hasErrors())
        {
            return "addUser";
        }

        if(userService.addUser(userDto))
        {
            return "redirect:/users";
        }
        else
        {
            model.addAttribute("errorUserExists", "Пользователь уже существует");
            return "addUser";
        }
    }
}
