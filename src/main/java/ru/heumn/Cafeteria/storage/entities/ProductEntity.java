package ru.heumn.Cafeteria.storage.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.heumn.Cafeteria.storage.ProductCategory;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(name = "product_name")
    String productName;

    @Column(name = "product_cost")
    Double cost;

    @Column(name = "product_description")
    String description;

    @Column(name = "product_picture_path")
    String picturePath;

    @Column(name = "product_active")
    Boolean active;

    @Column(name = "product_category")
    @Enumerated(EnumType.STRING)
    ProductCategory productCategory;
}
