package commons.request;

import commons.PreparationStep;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DeletePreparationStepRequest(
        @NotNull(
                message = "recipe id cannot be null"
        )
        Long recipeId,
        @Valid
        PreparationStep preparationStep
) {}