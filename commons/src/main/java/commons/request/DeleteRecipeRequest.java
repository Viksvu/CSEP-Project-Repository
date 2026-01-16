package commons.request;

import commons.Recipes;
import jakarta.validation.Valid;

/**
 * Delete a recipe
 * @param recipe recipe to delete
 */
public record DeleteRecipeRequest(
        @Valid
        Recipes recipe
) implements PostRequest{
    @Override
    public String serverPath() {
        return "api/recipe/delete";
    }
}
