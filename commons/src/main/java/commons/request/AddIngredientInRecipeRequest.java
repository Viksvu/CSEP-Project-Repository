package commons.request;

import commons.IngredientInRecipe;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Add an ingredient to a recipe
 * @param recipeId id of recipe to add ingredient to
 * @param ingredient ingredient to add to recipe
 */
public record AddIngredientInRecipeRequest(
        @NotNull(
                message = "recipe id cannot be null"
        )
        Long recipeId,
        @Valid
        IngredientInRecipe ingredient
) {}
