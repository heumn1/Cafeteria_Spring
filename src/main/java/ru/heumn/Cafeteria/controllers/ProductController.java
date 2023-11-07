package ru.heumn.Cafeteria.controllers;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.heumn.Cafeteria.dto.ProductDto;
import ru.heumn.Cafeteria.services.ProductService;
import ru.heumn.Cafeteria.storage.ProductCategory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/product")
public class ProductController {

    ProductService productService;

    @GetMapping()
    public String allProducts(Model model){

        List<ProductDto> productDtoList = productService.getListProduct();
        model.addAttribute("productList", productDtoList);

        return "products";
    }

    @GetMapping("/settings")
    public String settingsProducts(Model model){

        model.addAttribute("settings", "Настройки");

        List<ProductDto> productDtoList = productService.getListProduct();
        model.addAttribute("productList", productDtoList);

        return "products";
    }

    @GetMapping("/add")
    public String addProductGet(@ModelAttribute("product") ProductDto productDto, Model model){

        List<ProductCategory> categories = List.of(ProductCategory.values());



        model.addAttribute("categories", categories);

        return "addproduct";
    }

    @PostMapping("/add")
    public String addProductPost(@ModelAttribute("product") @Valid ProductDto productDto,
                                 BindingResult bindingResult, @RequestParam(required = true) MultipartFile picture, Model model){

        if(bindingResult.hasErrors() || picture.isEmpty())
        {
            model.addAttribute("errorPicture", "Ошибка, необходимо выбрать картинку");

            List<ProductCategory> categories = List.of(ProductCategory.values());
            model.addAttribute("categories", categories);
            return "addproduct";
        }

        productService.addProduct(productDto, picture);

        return "redirect:/product";
    }

    @PostMapping("/delete/{id}")
    public String addProduct(@PathVariable("id") Long id, Model model){

        try {
            productService.deleteProduct(id);
        }
        catch (Exception e)
        {
            List<ProductCategory> categories = List.of(ProductCategory.values());
            model.addAttribute("categories", categories);
            return "product";
        }

        return "redirect:/product";
    }
}
