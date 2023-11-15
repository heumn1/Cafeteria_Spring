package ru.heumn.Cafeteria.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.heumn.Cafeteria.storage.StatusOrder;
import ru.heumn.Cafeteria.storage.entities.ProductEntity;
import ru.heumn.Cafeteria.storage.entities.UserEntity;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDto {

    Long id;

    Integer numberOrder;

    Map<ProductEntity, StatusOrder> products = new LinkedHashMap<>();

    Map<ProductEntity, Long> cooks = new LinkedHashMap<>();

}
