package ru.heumn.Cafeteria.storage;

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
