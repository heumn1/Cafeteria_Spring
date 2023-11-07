package ru.heumn.Cafeteria.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.heumn.Cafeteria.storage.entities.OrderEntity;
import ru.heumn.Cafeteria.storage.entities.UserEntity;

import java.time.Instant;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    OrderEntity findByDateCreateAndSeller(Instant instant, UserEntity userEntity);

    List<OrderEntity> findAll();
}
