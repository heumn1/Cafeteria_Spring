package ru.heumn.Cafeteria.storage.enums;

public enum PaymentMethod {
    CARD,
    CASH;

    @Override
    public String toString() {
        return switch (this) {
            case CARD -> "Карта";
            case CASH -> "Наличные";
        };
    }
}
