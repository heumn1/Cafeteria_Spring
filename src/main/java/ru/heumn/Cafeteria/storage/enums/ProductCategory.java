package ru.heumn.Cafeteria.storage.enums;



public enum ProductCategory {
    SALADS,
    SOUPS,
    HOT_DRINKS,
    MAIN_DISHES,
    DESSERTS;

    @Override
    public String toString() {
        return switch (this) {
            case SALADS -> "Салат";
            case SOUPS -> "Суп";
            case HOT_DRINKS -> "Горячий напиток";
            case MAIN_DISHES -> "Основное блюдо";
            case DESSERTS -> "Десерт";
        };
    }
}
