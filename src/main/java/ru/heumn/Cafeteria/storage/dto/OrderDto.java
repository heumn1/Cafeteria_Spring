package ru.heumn.Cafeteria.storage.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.heumn.Cafeteria.storage.enums.PaymentMethod;
import ru.heumn.Cafeteria.storage.enums.StatusOrder;
import ru.heumn.Cafeteria.storage.entities.ProductEntity;
import ru.heumn.Cafeteria.storage.entities.UserEntity;

import java.time.Instant;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {

    Long id;

    Instant dateCreate;

    @NotNull(message = "Нужно выбрать как минимум один товар!")
    List<ProductEntity> products;

    UserEntity seller;

    StatusOrder status;

    Double price;

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;
}
