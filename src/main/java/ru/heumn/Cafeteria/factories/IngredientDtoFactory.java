package ru.heumn.Cafeteria.factories;

import org.springframework.stereotype.Component;
import ru.heumn.Cafeteria.storage.dto.IngredientDto;
import ru.heumn.Cafeteria.storage.entities.IngredientEntity;

@Component
public class IngredientDtoFactory {

    public IngredientDto makeDtoIngredient(IngredientEntity ingredientEntity){
        return IngredientDto.builder()
                .id(ingredientEntity.getId())
                .name(ingredientEntity.getName())
                .quantity(ingredientEntity.getQuantity())
                .numeralSystem(ingredientEntity.getNumeralSystem())
                .active(ingredientEntity.getActive())
                .build();
    }

    public IngredientEntity makeIngredientEntity(IngredientDto ingredientDto){
        return IngredientEntity.builder()
                .id(ingredientDto.getId())
                .name(ingredientDto.getName())
                .quantity(ingredientDto.getQuantity())
                .numeralSystem(ingredientDto.getNumeralSystem())
                .active(ingredientDto.getActive())
                .build();
    }
}
