package ru.heumn.Cafeteria.controllers;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.heumn.Cafeteria.storage.dto.OrderDto;
import ru.heumn.Cafeteria.factories.OrderDtoFactory;
import ru.heumn.Cafeteria.factories.ProductDtoFactory;
import ru.heumn.Cafeteria.services.OrderService;
import ru.heumn.Cafeteria.storage.enums.PaymentMethod;
import ru.heumn.Cafeteria.storage.enums.ProductCategory;
import ru.heumn.Cafeteria.storage.enums.Role;
import ru.heumn.Cafeteria.storage.entities.OrderEntity;
import ru.heumn.Cafeteria.storage.repository.OrderRepository;
import ru.heumn.Cafeteria.storage.repository.ProductRepository;
import ru.heumn.Cafeteria.storage.repository.UserRepository;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
@FieldDefaults(level = AccessLevel.PRIVATE)
@PreAuthorize("hasAuthority('SELLER_ROLE')")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDtoFactory orderDtoFactory;

    @Autowired
    ProductDtoFactory productDtoFactory;

    @GetMapping("/statistic")
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public String statistic(Model model){

        List<String> categories;
        categories = Arrays.stream(ProductCategory.values()).map(productCategory -> productCategory.toString()).collect(Collectors.toList());

        Map<LocalDate, Integer> sales = new  LinkedHashMap<>();

        Map<String, Integer> counts = new LinkedHashMap<>();
        List<OrderEntity> orderEntities = orderRepository.findAll();
        orderEntities
                .forEach(orderEntity -> {

                    try {
                        LocalDate ld = LocalDate.ofInstant(orderEntity.getDateCreate(), ZoneOffset.systemDefault());

                        Integer count = sales.get(ld);
                        if(count == null)
                        {
                            count = 1;
                        }
                        else
                        {
                            count++;
                        }

                        sales.put(ld, count);
                        }
                    catch (Exception ignore)
                    {
                    }

                    orderEntity.getProducts()
                            .forEach(product -> {
                                Integer i = counts.get(product.getProductCategory().toString());
                                if(i == null)
                                {
                                    i = 1;
                                }
                                else
                                {
                                    i++;
                                }
                                counts.put(product.getProductCategory().toString(), i);
                            });
                        });


        model.addAttribute("orders", orderDtoFactory.makeOrderDtoList(orderRepository.findAll()));

        model.addAttribute("sales", sales);
        model.addAttribute("categories", categories);
        model.addAttribute("counts", counts);

        return "statistic";
    }

    @PreAuthorize("hasAuthority('SELLER_ROLE')")
    @GetMapping("/add")
    public String order(@ModelAttribute("order") OrderDto orderDto, Model model){
        products(model);

        return "order";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('SELLER_ROLE')")
    public String addOrder(@ModelAttribute("order") @Valid OrderDto orderDto, Principal principal, Model model){

        if(orderDto.getProducts().isEmpty())
        {
            products(model);
            model.addAttribute("productsError", "Ошибка! не выбраны позиции");
            return "order";
        }

        double price = 0;
        for (int i = 0; i < orderDto.getProducts().size(); i++) {
            price += orderDto.getProducts().get(i).getCost();
        }
        orderDto.setPrice(price);

        orderDto.setDateCreate(Instant.now());
        orderDto.setSeller(userRepository.findByLogin(principal.getName()));

        orderService.addOrder(orderDto);

        Long id = orderRepository.findByDateCreateAndSeller(orderDto.getDateCreate(), orderDto.getSeller()).getId();

        return "redirect:/order/add/confirm/" + id;
    }

    private void products(Model model) {
        model.addAttribute("addOrder", "Добавление заказа");

        model.addAttribute("SOUPS",  productDtoFactory.makeProductDtoList(productRepository.findByProductCategoryAndActiveIsTrue(ProductCategory.SOUPS)));
        model.addAttribute("SALADS", productDtoFactory.makeProductDtoList(productRepository.findByProductCategoryAndActiveIsTrue(ProductCategory.SALADS)));
        model.addAttribute("HOT_DRINKS", productDtoFactory.makeProductDtoList(productRepository.findByProductCategoryAndActiveIsTrue(ProductCategory.HOT_DRINKS)));
        model.addAttribute("MAIN_DISHES", productDtoFactory.makeProductDtoList(productRepository.findByProductCategoryAndActiveIsTrue(ProductCategory.MAIN_DISHES)));
        model.addAttribute("DESSERTS", productDtoFactory.makeProductDtoList(productRepository.findByProductCategoryAndActiveIsTrue(ProductCategory.DESSERTS)));
    }

    @GetMapping("/add/confirm/{id}")
    @PreAuthorize("hasAuthority('SELLER_ROLE')")
    public String confirmPage(Model model, @PathVariable Long id, Principal principal){

        Optional<OrderEntity> orderEntity =  orderRepository.findById(id);

        if(orderEntity.isPresent())
        {
            if(orderEntity.get().getSeller() == userRepository.findByLogin(principal.getName())
                    || userRepository.findByLogin(principal.getName()).getRoles().contains(Role.ADMIN_ROLE))
            {
                model.addAttribute("confirmOrder", orderDtoFactory.makeDtoOrder(orderEntity.get()));

                List<PaymentMethod> paymentMethods = List.of(PaymentMethod.values());
                model.addAttribute("paymentMethods", paymentMethods);

                return "order";
            }
        }
        return "redirect:/order/add";
    }

    @PostMapping("/add/confirm/{id}")
    @PreAuthorize("hasAuthority('SELLER_ROLE')")
    public String confirmOrder(@RequestParam("payment") PaymentMethod paymentMethod, @PathVariable Long id, Principal principal){

        if(orderService.acceptOrder(id,paymentMethod,principal.getName()))
        {
            return "redirect:/order/add";
        }

        return "redirect:/order/add";
    }

    @PostMapping("/add/delete/{id}")
    @PreAuthorize("hasAuthority('SELLER_ROLE')")
    public String deleteOrder(@PathVariable Long id, Principal principal){

        Optional<OrderEntity> orderEntity =  orderRepository.findById(id);

        if(orderEntity.isPresent())
        {
            if(orderEntity.get().getSeller() == userRepository.findByLogin(principal.getName())
                    || userRepository.findByLogin(principal.getName()).getRoles().contains(Role.ADMIN_ROLE))
            {
                orderRepository.delete(orderEntity.get());
            }
        }
        return "redirect:/order/add";
    }
}
