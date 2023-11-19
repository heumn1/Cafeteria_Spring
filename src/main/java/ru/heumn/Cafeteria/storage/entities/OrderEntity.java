package ru.heumn.Cafeteria.storage.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.heumn.Cafeteria.storage.enums.PaymentMethod;
import ru.heumn.Cafeteria.storage.enums.StatusOrder;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "ordering")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(name = "date_create")
    Instant dateCreate;

    @ManyToMany
    List<ProductEntity> products;

    @ManyToOne
    UserEntity seller;

    @Column(name = "order_price")
    Double price;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    StatusOrder status;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;
}
