package ru.heumn.Cafeteria.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.heumn.Cafeteria.storage.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
