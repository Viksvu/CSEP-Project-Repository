package commons.request;

import commons.IngredientInRecipe;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Delete an ingredient from a recipe
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
) implements PostRequest {
        @Override
        public String serverPath() {
                return "api/recipeingredient/delete";
        }
}
