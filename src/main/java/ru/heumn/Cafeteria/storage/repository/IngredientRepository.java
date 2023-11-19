package ru.heumn.Cafeteria.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.heumn.Cafeteria.storage.entities.IngredientEntity;

public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {
    IngredientEntity findByNameAndActiveIsTrue(String name);
}
