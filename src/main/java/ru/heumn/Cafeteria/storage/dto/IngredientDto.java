package ru.heumn.Cafeteria.storage.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.heumn.Cafeteria.storage.enums.NumeralSystem;

@Data
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {

    Long id;

    @NotEmpty(message = "Ошибка! Поле не должно быть пустым")
    @NotNull(message = "Ошибка! Поле не должно быть пустым")
    @Size(min = 2, max = 50, message = "название не должно быть меньше 2 и больше 50")
    String name;

    Integer quantity;

    @Enumerated(EnumType.STRING)
    NumeralSystem numeralSystem;

    Boolean active;
}
