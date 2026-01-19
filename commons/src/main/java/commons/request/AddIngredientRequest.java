package commons.request;

import commons.Ingredients;
import jakarta.validation.Valid;

/**
 * Add an ingredient
 * @param ingredient ingredient to add
 */
public record AddIngredientRequest(
        @Valid
        Ingredients ingredient
) implements PostRequest {
    @Override
    public String serverPath() {
        return "api/ingredient/add";
    }
}
