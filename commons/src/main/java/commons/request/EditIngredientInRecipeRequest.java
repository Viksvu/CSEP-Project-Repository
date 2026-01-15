package commons.request;

import commons.IngredientInRecipe;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * @param recipeId id of recipe where to edit ingredient from
 * @param ingredient edited ingredient
 */
public record EditIngredientInRecipeRequest(
    @NotNull(
            message = "recipe id cannot be null"
    )
    Long recipeId,
    @Valid
    IngredientInRecipe ingredient
) {}
