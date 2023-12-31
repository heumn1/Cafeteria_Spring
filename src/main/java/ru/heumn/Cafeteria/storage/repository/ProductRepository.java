package ru.heumn.Cafeteria.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.heumn.Cafeteria.storage.enums.ProductCategory;
import ru.heumn.Cafeteria.storage.entities.ProductEntity;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByProductCategoryAndActiveIsTrue(ProductCategory productCategory);

    ProductEntity findByProductName(String name);
}
