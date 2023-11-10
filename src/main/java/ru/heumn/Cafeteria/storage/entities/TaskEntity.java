package ru.heumn.Cafeteria.storage.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.heumn.Cafeteria.storage.StatusOrder;

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
public class TaskEntity {

    @ElementCollection
    @Enumerated(EnumType.STRING)
    Map<ProductEntity, StatusOrder> products = new HashMap<>();

    @Column(name = "number_order")
    Integer numberOrder;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
}
