package ru.heumn.Cafeteria.controllers;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.heumn.Cafeteria.storage.dto.ProductDto;
import ru.heumn.Cafeteria.services.ProductService;
import ru.heumn.Cafeteria.storage.enums.ProductCategory;
import ru.heumn.Cafeteria.storage.repository.ProductRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/product")
@PreAuthorize("hasAuthority('MANAGER_ROLE')")
public class ProductController {

    ProductService productService;
    ProductRepository productRepository;

    @GetMapping()
    public String allProducts(Model model){

        model.addAttribute("noSort", "сортировка");

        List<ProductDto> productDtoList = productService.getListProduct();
        model.addAttribute("productList", productDtoList);

        return "products";
    }

    @GetMapping("/sort")
    public String sortedProducts(@RequestParam ProductCategory productCategory, Model model){

        model.addAttribute("noSort", "сортировка");

        List<ProductDto> productDtoList = productService.getListProductSorted(productCategory);
        model.addAttribute("productList", productDtoList);

        return "products";
    }

    @GetMapping("/delete/sort")
    public String sortedDeleteProducts(@RequestParam ProductCategory productCategory, Model model){

        model.addAttribute("settings", "Настройки");

        List<ProductDto> productDtoList = productService.getListProductSorted(productCategory);
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

        if(productRepository.findByProductName(productDto.getProductName()) != null)
        {
            model.addAttribute("errorName", "Ошибка, такое имя продукта уже существует");

            List<ProductCategory> categories = List.of(ProductCategory.values());
            model.addAttribute("categories", categories);
            return "addproduct";
        }

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
