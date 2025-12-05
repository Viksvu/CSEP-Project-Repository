package server.services;

import commons.IngredientInRecipe;
import commons.Recipes;
import jakarta.transaction.Transactional;
import server.database.IngredientsRepository;
import server.database.RecipeRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for managing recipes.
 * Provides methods to add, retrieve, and delete recipes.
 * Uses RecipeRepository for database interactions.
 */

@Service
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientsRepository ingredientsRepository;

    /**
     * Constructor for RecipeService.
     * Serves as dependency injection for RecipeRepository.
     * @param recipeRepository The repository for recipes.
     */
    public RecipeService(RecipeRepository recipeRepository,
                         IngredientsRepository ingredientsRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientsRepository = ingredientsRepository;
    }

    /**
     * Adds a new recipe to the database.
     * @param recipe The recipe to be added.
     * @return The saved recipe. Returns the saved recipe with generated ID.
     */
    public Recipes addRecipe(Recipes recipe) {
        for (IngredientInRecipe ingredient : recipe.getIngredients()) {
            ingredient.setRecipes(recipe);
            if (ingredient.getTempIngredient().getId() == null ) {
                ingredientsRepository.save(ingredient.getTempIngredient());
            }
        }
        return recipeRepository.save(recipe);
    }

    /**
     * Retrieves all recipes from the database.
     * @return An iterable collection of all recipes.
     */
    public Iterable<Recipes> getAllRecipes() {
        return recipeRepository.findAll();
    }

    /**
     * Retrieves a recipe by its ID.
     * @param recipeId The ID of the recipe to retrieve.
     * @return The recipe with the specified ID.
     * @throws RuntimeException if the recipe is not found.
     */
    public Recipes getRecipeById(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
    }

    /**
     * Deletes a recipe by its ID.
     * @param recipeId The ID of the recipe to delete.
     */
    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }
}
