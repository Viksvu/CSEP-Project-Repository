package server.services;

import commons.Recipes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing recipes.
 * Provides methods to add, retrieve, and delete recipes.
 * Uses RecipeRepository for database interactions.
 */
// TODO remove this class and integrate the real Recipe Service
@Service
@Deprecated
public class TempRecipeService {
    @Deprecated
    private static TempRecipeService tempService;

    /**
     * Get a Temporary Recipe Service Instance
     * This should be removed when Recipe Service is refactored/implemented
     * with new Recipe class
     * @return
     */
    @Deprecated
    public static TempRecipeService get() {
        if (tempService == null) tempService = new TempRecipeService();
        return tempService;
    }

    private final List<Recipes> recipes = new ArrayList<>();

    /**
     * Adds a new recipe to the database.
     * @param recipe The recipe to be added.
     * @return The saved recipe. Returns the saved recipe with generated ID.
     */
    public Recipes addRecipe(Recipes recipe) {
        this.recipes.add(recipe);
        return recipe;
    }

    /**
     * Retrieves all recipes from the database.
     * @return An iterable collection of all recipes.
     */
    public List<Recipes> getAllRecipes() {
        return this.recipes;
    }

    /**
     * Retrieves a recipe by its ID.
     * @param recipeId The ID of the recipe to retrieve.
     * @return The recipe with the specified ID.
     * @throws RuntimeException if the recipe is not found.
     */
    public Recipes getRecipeById(Long recipeId) {
        return this.recipes.stream()
                .filter(r -> r.getId() == recipeId)
                .findFirst().orElse(null);
    }

    /**
     * Deletes a recipe by its ID.
     * @param recipeId The ID of the recipe to delete.
     */
    public void deleteRecipe(Long recipeId) {
        this.recipes.removeIf(r -> r.getId() == recipeId);
    }
}