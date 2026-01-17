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
    private RecipeSocketService recipeSocketService;
    /**
     * Constructor for RecipeService.
     * Serves as dependency injection for RecipeRepository.
     * @param recipeRepository The repository for recipes.
     */
    public RecipeService(RecipeRepository recipeRepository,
                         IngredientsRepository ingredientsRepository,
                         RecipeSocketService recipeSocketService) {
        this.recipeRepository = recipeRepository;
        this.ingredientsRepository = ingredientsRepository;
        this.recipeSocketService=recipeSocketService;
    }

    /**
     * Adds a new recipe to the database.
     * @param recipe The recipe to be added.
     * @return The saved recipe. Returns the saved recipe with generated ID.
     */
    public Recipes addRecipe(Recipes recipe) {
        for (IngredientInRecipe ingredient : recipe.getIngredients()) {
            ingredient.setRecipes(recipe);
            if (ingredient.getIngredient().getId() == null ) {
                ingredientsRepository.save(ingredient.getIngredient());
            }
        }
        Recipes ret= recipeRepository.save(recipe);
        recipeSocketService.recipeUpdated(ret.getId());
        return ret;
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
                .orElseThrow(() -> new RecipeNotFoundException(recipeId));
    }

    /**
     * Retrieves a recipe by its ID.
     * Returns null if the recipe doesn't exist
     * @param recipeId The ID of the recipe to retrieve.
     * @return The recipe with the specified ID.
     */
    public Recipes getRecipeByIdSafe(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .orElse(null);
    }

    /**
     * Deletes a recipe by its ID.
     * @param recipeId The ID of the recipe to delete.
     */
    public void deleteRecipe(Long recipeId) {
        recipeSocketService.recipeDeleted(recipeId);
        recipeRepository.deleteById(recipeId);
    }

    public static class RecipeNotFoundException extends RuntimeException {
        /**
         * RecipeNotFoundException constructor
         * @param recipeId the id of the recipe which wasn't found
         */
        public RecipeNotFoundException(Long recipeId) {
            super(String.format("Recipe %s not found", recipeId));
        }
    }
}
