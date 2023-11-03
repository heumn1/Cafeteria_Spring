package ru.heumn.Cafeteria.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import ru.heumn.Cafeteria.dto.ProductDto;
import ru.heumn.Cafeteria.services.ProductService;

import java.util.List;

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

    @PostMapping("/add")
    public String addProduct(ProductDto productDto, MultipartFile multipartFile){

        productService.addProduct(productDto,multipartFile);

        return "redirect:/product";
    }

    @PostMapping("/delete/{id}")
    public String addProduct(@PathVariable("id") Long id){

        productService.deleteProduct(id);

        return "redirect:/product";
    }
}
