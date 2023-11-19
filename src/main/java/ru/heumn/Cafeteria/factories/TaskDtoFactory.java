package ru.heumn.Cafeteria.factories;

import org.springframework.stereotype.Component;
import ru.heumn.Cafeteria.storage.dto.TaskDto;
import ru.heumn.Cafeteria.storage.entities.TaskEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskDtoFactory {

    public TaskDto makeDtoTask(TaskEntity taskEntity) {
        return TaskDto.builder()
                .id(taskEntity.getId())
                .cooks(taskEntity.getCooks())
                .numberOrder(taskEntity.getNumberOrder())
                .products(taskEntity.getProducts())
                .build();
    }

    public TaskEntity makeTaskEntity(TaskDto taskDto) {
        return TaskEntity.builder()
                .id(taskDto.getId())
                .cooks(taskDto.getCooks())
                .numberOrder(taskDto.getNumberOrder())
                .products(taskDto.getProducts())
                .build();
    }

    public List<TaskDto> makeListDtoTask(List<TaskEntity> taskEntities){
        return taskEntities.stream()
                .map(this::makeDtoTask)
                .collect(Collectors.toList());
    }
}
