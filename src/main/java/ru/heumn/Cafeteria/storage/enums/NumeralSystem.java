package ru.heumn.Cafeteria.storage.enums;

public enum NumeralSystem {
    GRAMS,
    QUANTITY,

    MILLILITERS;

    @Override
    public String toString() {
        return switch (this) {
            case GRAMS -> "Грамм";
            case QUANTITY -> "Шт";
            case MILLILITERS -> "Мл.";
        };
    }
}
