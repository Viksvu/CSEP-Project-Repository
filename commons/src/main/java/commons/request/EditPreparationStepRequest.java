package commons.request;

import commons.PreparationStep;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Edit a preparation step in an ingredient
 * @param recipeId id of recipe to edit preparation step from
 * @param index index of preparation step in preparation step list
 * @param preparationStep edited preparation step
 */
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