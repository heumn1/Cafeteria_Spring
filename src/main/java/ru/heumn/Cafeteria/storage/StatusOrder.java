package ru.heumn.Cafeteria.storage;

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
            case GIVEN -> "Выдвн";
        };
    }
}
