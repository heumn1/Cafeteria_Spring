package ru.heumn.Cafeteria.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.heumn.Cafeteria.storage.entities.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
