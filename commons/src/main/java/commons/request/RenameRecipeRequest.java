package commons.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
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
) {}
