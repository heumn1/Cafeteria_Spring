package ru.heumn.Cafeteria.storage;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.heumn.Cafeteria.storage.entities.ProductEntity;

import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestEntity {

    @ElementCollection
    Map<ProductEntity, Boolean> products = new HashMap<>();

    @Id
    Long id;
}
