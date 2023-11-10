package ru.heumn.Cafeteria.storage.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
public class NumberOrderEntity {

    @Id
    @Column(name = "id")
    private Long Id;

    @Column(name = "number_order")
    private Integer numberOrder;

    public NumberOrderEntity(){

    }

    public Long getId() {
        return Id;
    }

    public Integer getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(Integer numberOrder) {
        this.numberOrder = numberOrder;
    }
}
