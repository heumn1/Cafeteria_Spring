package ru.heumn.Cafeteria.storage.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.heumn.Cafeteria.storage.entities.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    TaskEntity findByNumberOrder(Integer id);

}
