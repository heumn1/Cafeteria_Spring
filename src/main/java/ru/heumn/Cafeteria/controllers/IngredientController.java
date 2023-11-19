package ru.heumn.Cafeteria.controllers;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.heumn.Cafeteria.storage.dto.IngredientDto;
import ru.heumn.Cafeteria.factories.IngredientDtoFactory;
import ru.heumn.Cafeteria.storage.entities.IngredientEntity;
import ru.heumn.Cafeteria.storage.enums.NumeralSystem;
import ru.heumn.Cafeteria.storage.repository.IngredientRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ingredients")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IngredientController {

    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    IngredientDtoFactory ingredientDtoFactory;

    @GetMapping()
    public String getAllIngredient(Model model){

        List<IngredientEntity> ingredients = ingredientRepository.findAll();
        ingredients.sort((o1, o2)
                -> o1.getName().compareTo(
                o2.getName()));

        model.addAttribute("ingredients", ingredients);


        return "ingredients";
    }

    @GetMapping("/settings")
    public String settingsIngredient(Model model){

        model.addAttribute("settings", "настройки");
        List<IngredientEntity> ingredients = ingredientRepository.findAll();
        ingredients.sort((o1, o2)
                -> o1.getName().compareTo(
                o2.getName()));

        model.addAttribute("ingredients", ingredients);

        return "ingredients";
    }

    @PostMapping("/delete/{id}")
    public String deleteIngredient(@PathVariable(value = "id", required = true) Long id) {

        Optional<IngredientEntity> ingredientEntity = ingredientRepository.findById(id);

        if(ingredientEntity.isPresent())
        {
            ingredientEntity.get().setActive(false);
            ingredientRepository.save(ingredientEntity.get());
        }

        return "redirect:/ingredients";
    }

    @GetMapping("/add")
    public String addIngredientPage(@ModelAttribute("ingredient") IngredientDto ingredientDto, Model model){

        model.addAttribute("systems", NumeralSystem.values());

        return "ingredients";
    }

    @PostMapping("/settings/subtract")
    public String subtractIngredient(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "count") Integer count,
            Model model
    ){

        Optional<IngredientEntity> ingredientEntity = ingredientRepository.findById(id);

        if(count < 0)
        {
            model.addAttribute("errorSubtract",
                    "Ошибка! В поле вычитания значение меньше 0");
            return setModel(model);
        }

        if(ingredientEntity.isPresent())
        {
            if(ingredientEntity.get().getQuantity() < count) {
                model.addAttribute("errorSubtract",
                        "У выбранного ингридиента в настоящее время количество меньше вычитаемого");
                return setModel(model);
            }
            else {
                ingredientEntity.get().setQuantity(ingredientEntity.get().getQuantity() - count);
                ingredientRepository.save(ingredientEntity.get());
            }
        }
        return "redirect:/ingredients/settings";
    }

    private String setModel(Model model) {

        model.addAttribute("settings", "настройки");
        List<IngredientEntity> ingredients = ingredientRepository.findAll();
        ingredients.sort((o1, o2)
                -> o1.getName().compareTo(
                o2.getName()));

        model.addAttribute("ingredients", ingredients);

        return "ingredients";
    }

    @PostMapping("/settings/set")
    public String setCountIngredient(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "count") Integer count,
            Model model
    ){

        if(count < 0)
        {
            model.addAttribute("errorSubtract",
                    "Ошибка! В поле вычитания значение меньше 0");
            return setModel(model);
        }

        Optional<IngredientEntity> ingredientEntity = ingredientRepository.findById(id);

        if(ingredientEntity.isPresent())
        {
            ingredientEntity.get().setQuantity(count);
            ingredientRepository.save(ingredientEntity.get());
        }
        return "redirect:/ingredients/settings";
    }

    @PostMapping("/add")
    public String addIngredient(@ModelAttribute("ingredient") @Valid IngredientDto ingredientDto,
                                BindingResult bindingResult,
                                Model model){

        if(ingredientDto.getName().equals(ingredientRepository.findByNameAndActiveIsTrue(ingredientDto.getName())))
        {
            model.addAttribute("systems", NumeralSystem.values());
            model.addAttribute("errorName", "Такой ингредиент уже существует");
            return "ingredients";
        }

        if(bindingResult.hasErrors())
        {
            model.addAttribute("systems", NumeralSystem.values());
            return "ingredients";
        }

        try {
            ingredientRepository.save(ingredientDtoFactory.makeIngredientEntity(ingredientDto));
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

        return "redirect:/ingredients";
    }
}
