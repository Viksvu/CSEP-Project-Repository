package commons.request;

import commons.Recipes;
import jakarta.validation.Valid;

/**
 * Add a recipe
 * @param recipe recipe to add
 */
public record AddRecipeRequest(
        @Valid
        Recipes recipe
) implements PostRequest {
    @Override
    public String serverPath() {
        return "api/recipe/add";
    }
}
