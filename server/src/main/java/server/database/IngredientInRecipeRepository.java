package server.database;

import commons.IngredientInRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface for IngredientInRecipe entity.
 * Makes available custom query to query all recipe
 * IDs that use a specific ingredient ID.
 */
public interface IngredientInRecipeRepository
        extends JpaRepository<IngredientInRecipe, Long> {
    /**
     * Finds all recipe IDs that use the specified ingredient ID.
     * @param ingredientId The ID of the ingredient.
     * @return A list of recipe IDs that use the specified ingredient.
     */
    @Query("SELECT i.recipes.id FROM IngredientInRecipe i" +
            " WHERE i.ingredient.id = :ingredientId")
    List<Long> findRecipeIdsByIngredientId(Long ingredientId);
}
