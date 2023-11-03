package ru.heumn.Cafeteria.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.heumn.Cafeteria.storage.PaymentMethod;
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

    @NotNull
    Instant dateCreate;

    @NotNull
    List<ProductEntity> products;

    @NotNull
    UserEntity seller;

    @NotNull
    Double price;

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;
}
