package ru.heumn.Cafeteria.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.heumn.Cafeteria.dto.ProductDto;
import ru.heumn.Cafeteria.factories.ProductDtoFactory;
import ru.heumn.Cafeteria.storage.StatusOrder;
import ru.heumn.Cafeteria.storage.entities.ProductEntity;
import ru.heumn.Cafeteria.storage.entities.TaskEntity;
import ru.heumn.Cafeteria.storage.repository.ProductRepository;
import ru.heumn.Cafeteria.storage.repository.TaskRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductDtoFactory productDtoFactory;
    @Autowired
    TaskRepository taskRepository;

    @Value("${upload.path}")
    String path;


    public void addProduct(ProductDto productDto, MultipartFile file){

        ProductEntity product = productDtoFactory.makeProductEntity(productDto);

        try {
            if (!file.isEmpty()) {
                File uploadDir = new File(path);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(path + "/" + resultFilename));
                product.setPicturePath(resultFilename);
            }
        } catch (IOException e) {
            product.setPicturePath(null);
        }

        product.setActive(true);

        productRepository.save(product);
    }

    public void deleteProduct(Long id){

        Optional<ProductEntity> productEntity = productRepository.findById(id);

        if(productEntity.isPresent()) {
            try {

                if (productEntity.get().getPicturePath() != null) {
                    Files.delete(Path.of(path + "\\" + productEntity.get().getPicturePath()));
                }

            } catch (Exception ignored) {
            }
            productEntity.get().setActive(false);
            productEntity.get().setPicturePath(null);
            productRepository.save(productEntity.get());
        }
    }

    public List<ProductDto> getListProduct(){

        List<ProductDto> productDtoList = new ArrayList<>();

        productRepository.findAll().stream()
                .filter(productEntity -> (productEntity.getActive() == true))
                .forEach(product -> productDtoList.add(productDtoFactory.makeDtoProduct(product)));

        return productDtoList;
    }

    public void confirmProduct(Integer numberOrder, String productName){
        Optional<TaskEntity> taskEntity = Optional.of(taskRepository.findByNumberOrder(numberOrder));

        Map<ProductEntity, StatusOrder> products = taskEntity.get().getProducts();
        if(products.get(productRepository.findByProductName(productName)) == StatusOrder.COOK)
        {
            products.put(productRepository.findByProductName(productName), StatusOrder.READY);
        }
        else
        {
            products.put(productRepository.findByProductName(productName), StatusOrder.COOK);

        }
        taskEntity.get().setProducts(products);

        boolean allReady = false;

        for (Map.Entry<ProductEntity, StatusOrder> product : taskEntity.get().getProducts().entrySet()) {
            if (product.getValue() != StatusOrder.READY) {
                taskRepository.save(taskEntity.get());
                break;
            } else {
                allReady = true;
            }
        }

        if(allReady)
        {
            //taskRepository.delete(taskEntity.get());

            //ТУТ НЕ УДАЛЯТЬ А ПОСЫЛАТЬ НА ВЫДАЧУ (with js)

            //TODO НА ЗОНУ ВЫДАЧИ
        }



    }
}
