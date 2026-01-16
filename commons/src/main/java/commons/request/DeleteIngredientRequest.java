package commons.request;

import commons.Ingredients;
import jakarta.validation.Valid;

/**
 * Delete an ingredient
 * @param ingredient ingredient to delete
 */
public record DeleteIngredientRequest(
        @Valid
        Ingredients ingredient
) implements PostRequest {
    @Override
    public String serverPath() {
        return "api/ingredient/delete";
    }
}
