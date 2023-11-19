package ru.heumn.Cafeteria.storage.entities;

import jakarta.persistence.*;
import lombok.*;
import ru.heumn.Cafeteria.storage.enums.NumeralSystem;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(name = "ingredient_name")
    String name;

    @Column(name = "ingredient_quantity")
    Integer quantity;

    @Column(name = "numeral_system")
    @Enumerated(EnumType.STRING)
    NumeralSystem numeralSystem;

    @Column(name = "ingredient_active")
    Boolean active;
}
