package commons.request;

import commons.IngredientInRecipe;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * @param recipeId id of recipe to delete ingredient from
 * @param ingredient ingredient to delete
 */
public record DeleteIngredientInRecipeRequest(
        @NotNull(
                message = "recipe id cannot be null"
        )
        Long recipeId,
        @Valid
        IngredientInRecipe ingredient
) {}
