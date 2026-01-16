package commons.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Rename an recipe
 * @param recipeId id of recipe to rename
 * @param newName new name of recipe
 */
public record RenameRecipeRequest(
        @NotNull(
                message = "Recipe id cannot be null"
        )
        Long recipeId,
        @NotBlank(
                message = "New name cannot be blank"
        )
        String newName
) implements PostRequest {
        @Override
        public String serverPath() {
                return "api/recipe/rename";
        }
}
