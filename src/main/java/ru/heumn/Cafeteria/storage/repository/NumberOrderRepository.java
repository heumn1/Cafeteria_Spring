package ru.heumn.Cafeteria.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.heumn.Cafeteria.storage.entities.NumberOrderEntity;

public interface NumberOrderRepository extends JpaRepository<NumberOrderEntity, Long> {
}
