package commons.request;

import commons.IngredientInRecipe;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record AddIngredientInRecipeRequest(
        @NotNull(
                message = "recipe id cannot be null"
        )
        Long recipeId,
        @Valid
        IngredientInRecipe ingredient
) {}
