package commons.request;

import commons.Recipes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Clone a recipe
 * @param recipe recipe to clone
 * @param newName name of cloned recipe
 */
public record CloneRecipeRequest(
    @NotNull(
            message = "Cloned recipe cannot be null"
    )
    Recipes recipe,
    @NotBlank(
            message = "New recipe name cannot be blank"
    )
    String newName
) implements PostRequest {
    @Override
    public String serverPath() {
        return "api/recipe/clone";
    }
}
