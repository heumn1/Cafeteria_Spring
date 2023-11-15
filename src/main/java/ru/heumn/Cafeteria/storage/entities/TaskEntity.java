package ru.heumn.Cafeteria.storage.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.heumn.Cafeteria.storage.StatusOrder;

import java.util.LinkedHashMap;
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
@Table(name = "Task")
public class TaskEntity {

    @ElementCollection
    @Enumerated(EnumType.STRING)
    Map<ProductEntity, StatusOrder> products = new LinkedHashMap<>();

    @ElementCollection
    Map<ProductEntity, Long> cooks = new LinkedHashMap<>();

    @Column(name = "number_order")
    Integer numberOrder;

    @Column(name = "main_order")
    Long mainOrder;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
}
