package commons.request;

import jakarta.validation.constraints.NotNull;

public record ListPreparationStepRequest(
        @NotNull(
                message = "recipe id cannot be null"
        )
        Long recipeId
) {}
