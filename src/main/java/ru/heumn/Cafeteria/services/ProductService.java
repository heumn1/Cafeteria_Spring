package ru.heumn.Cafeteria.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.heumn.Cafeteria.dto.ProductDto;
import ru.heumn.Cafeteria.factories.ProductDtoFactory;
import ru.heumn.Cafeteria.storage.entities.ProductEntity;
import ru.heumn.Cafeteria.storage.repository.ProductRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductDtoFactory productDtoFactory;

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

        productRepository.save(product);
    }

    public void deleteProduct(Long id){

        Optional<ProductEntity> productEntity = productRepository.findById(id);

        if(productEntity.isPresent()) {

            try {
                if (productEntity.get().getPicturePath() != null) {
                    Files.delete(Path.of(productEntity.get().getPicturePath()));
                }

            } catch (Exception ignore) {
            }

            productRepository.delete(productEntity.get());
        }
    }

    public List<ProductDto> getListProduct(){

        List<ProductDto> productDtoList = new ArrayList<>();
        productRepository.findAll()
                .forEach(productEntity -> productDtoList.add(productDtoFactory.makeDtoProduct(productEntity)));

        return productDtoList;
    }
}
