package commons.request;

import commons.PreparationStep;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record EditPreparationStepRequest(
        @NotNull(
                message = "recipe id cannot be null"
        )
        Long recipeId,
        @Min(
                value = 0,
                message = "negative index is not allowed"
        )
        int index,
        @Valid
        PreparationStep preparationStep
) {}