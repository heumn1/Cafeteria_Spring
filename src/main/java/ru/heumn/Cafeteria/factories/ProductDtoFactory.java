package ru.heumn.Cafeteria.factories;


import org.springframework.stereotype.Component;
import ru.heumn.Cafeteria.storage.dto.ProductDto;
import ru.heumn.Cafeteria.storage.entities.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDtoFactory {

    public ProductDto makeDtoProduct(ProductEntity productEntity) {
        return ProductDto.builder()
                .id(productEntity.getId())
                .productName(productEntity.getProductName())
                .picturePath(productEntity.getPicturePath())
                .active(productEntity.getActive())
                .description(productEntity.getDescription())
                .cost(productEntity.getCost())
                .productCategory(productEntity.getProductCategory())
                .build();
    }

    public ProductEntity makeProductEntity(ProductDto productDto) {
        return ProductEntity.builder()
                .id(productDto.getId())
                .productName(productDto.getProductName())
                .active(productDto.getActive())
                .picturePath(productDto.getPicturePath())
                .description(productDto.getDescription())
                .cost(productDto.getCost())
                .productCategory(productDto.getProductCategory())
                .build();
    }

    public List<ProductDto> makeProductDtoList(List<ProductEntity> productEntities) {

        return productEntities.stream()
                .map(productEntity -> makeDtoProduct(productEntity))
                .collect(Collectors.toList());

    }

}
