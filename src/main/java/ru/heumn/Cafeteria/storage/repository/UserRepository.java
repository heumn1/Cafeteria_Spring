package ru.heumn.Cafeteria.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.heumn.Cafeteria.storage.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findByLogin(String login);
}
