package ru.heumn.Cafeteria.factories;

import org.springframework.stereotype.Component;
import ru.heumn.Cafeteria.dto.TaskDto;
import ru.heumn.Cafeteria.storage.entities.TaskEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskDtoFactory {

    public TaskDto makeDtoTask(TaskEntity taskEntity) {
        return TaskDto.builder()
                .id(taskEntity.getId())
                .numberOrder(taskEntity.getNumberOrder())
                .products(taskEntity.getProducts())
                .build();
    }

    public TaskEntity makeTaskEntity(TaskEntity taskEntity) {
        return TaskEntity.builder()
                .id(taskEntity.getId())
                .numberOrder(taskEntity.getNumberOrder())
                .products(taskEntity.getProducts())
                .build();
    }

    public List<TaskDto> makeListDtoTask(List<TaskEntity> taskEntities){
        return taskEntities.stream()
                .map(this::makeDtoTask)
                .collect(Collectors.toList());
    }
}
