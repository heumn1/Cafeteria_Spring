package ru.heumn.Cafeteria.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.heumn.Cafeteria.storage.ProductCategory;

@Data
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {

    Long id;

    @NotEmpty(message = "Ошибка! Поле не должно быть пустым")
    @NotNull(message = "Ошибка! Поле не должно быть пустым")
    @Size(min = 2, max = 40, message = "Название не должно быть меньше 2 символов и больше 40")
    String productName;

    @NotNull
    @Min(value = 10, message = "цена не должна быть ниже 10 рублей")
    Integer cost;

    String description;

    String picturePath;

    @Enumerated(EnumType.STRING)
    ProductCategory productCategory;
}
