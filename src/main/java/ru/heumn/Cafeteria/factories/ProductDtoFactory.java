package ru.heumn.Cafeteria.factories;


import org.springframework.stereotype.Component;
import ru.heumn.Cafeteria.dto.ProductDto;
import ru.heumn.Cafeteria.storage.entities.ProductEntity;

@Component
public class ProductDtoFactory {

    public ProductDto makeDtoProduct(ProductEntity productEntity)
    {
        return ProductDto.builder()
                .id(productEntity.getId())
                .productName(productEntity.getProductName())
                .picturePath(productEntity.getPicturePath())
                .description(productEntity.getDescription())
                .cost(productEntity.getCost())
                .productCategory(productEntity.getProductCategory())
                .build();
    }

    public ProductEntity makeProductEntity(ProductDto productDto)
    {
        return ProductEntity.builder()
                .id(productDto.getId())
                .productName(productDto.getProductName())
                .picturePath(productDto.getPicturePath())
                .description(productDto.getDescription())
                .cost(productDto.getCost())
                .productCategory(productDto.getProductCategory())
                .build();
    }
}
