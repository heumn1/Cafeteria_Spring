package ru.heumn.Cafeteria.storage.enums;

public enum StatusOrder {

    ACCEPTED,
    COOK,
    READY,
    GIVEN;

    @Override
    public String toString() {
        return switch (this) {
            case ACCEPTED -> "Принят";
            case COOK -> "Готовиться";
            case READY -> "Готов";
            case GIVEN -> "Выдан";
        };
    }
}
