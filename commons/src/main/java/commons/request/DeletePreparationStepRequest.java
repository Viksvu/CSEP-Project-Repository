package commons.request;

import commons.PreparationStep;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Delete a preparation step from a recipe
 * @param recipeId id of recipe to delete preparation step from
 * @param preparationStep preparation step to delete
 */
public record DeletePreparationStepRequest(
        @NotNull(
                message = "recipe id cannot be null"
        )
        Long recipeId,
        @Valid
        PreparationStep preparationStep
) {}