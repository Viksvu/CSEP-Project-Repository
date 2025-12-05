package server.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import server.database.IngredientsRepository;
import commons.Ingredients;

import java.util.Optional;

/**
 * Service class for managing ingredients.
 * Provides methods to add, retrieve, and delete ingredients.
 * Uses IngredientsRepository for database interactions.
 */

@Service
@Transactional
public class IngredientsService {
    private final IngredientsRepository ingredientsRepository;

    /**
     * Constructor for IngredientsService.
     * Serves as dependency injection for IngredientsRepository.
     * @param ingredientsRepository The repository for ingredients.
     */
    public IngredientsService(IngredientsRepository ingredientsRepository) {
        this.ingredientsRepository = ingredientsRepository;
    }

    /**
     * Adds a new ingredient to the database.
     * @param ingredient The ingredient to be added.
     * @return The saved ingredient.
     * Returns the saved ingredient with generated ID.
     */
    public Ingredients addIngredient(Ingredients ingredient) {
        return ingredientsRepository.save(ingredient);
    }

    /**
     * Retrieves all ingredients from the database.
     * At the moment, it is unclear whether this method will be used.
     * @return An iterable collection of all ingredients.
     */
    public Iterable<Ingredients> getAllIngredients() {
        return ingredientsRepository.findAll();
    }

    /**
     * Deletes an ingredient by its ID.
     * @param ingredientId The ID of the ingredient to delete.
     */
    public void deleteIngredient(Long ingredientId) {
        ingredientsRepository.deleteById(ingredientId);
    }

    /**
     * Retrieves an ingredient by its ID.
     * @param ingredientId The ID of the ingredient to retrieve.
     * @return An Optional containing the
     * ingredient if found, or empty if not found.
     */
    public Optional<Ingredients> getIngredientById(Long ingredientId) {
        return ingredientsRepository.findById(ingredientId);
    }
}
