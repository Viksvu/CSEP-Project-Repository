package commons.request;

import commons.PreparationStep;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Add a preparation step to a recipe
 * @param recipeId id of recipe to add preparation step to
 * @param preparationStep preparation step to add
 */
public record AddPreparationStepRequest(
        @NotNull(
                message = "recipe id cannot be null"
        )
        Long recipeId,
        @Valid
        PreparationStep preparationStep
) {}