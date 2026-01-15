package commons.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
