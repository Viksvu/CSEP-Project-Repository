package server.api;

import commons.Ingredients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.temp.Ingredient;
import server.services.IngredientsService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {
    private final IngredientsService ingredientsService;

    /**
     * Constructor for IngredientController
     * Springboot handles the dependency injection
     * @param ingredientsService the database service for saving ingredients
     */
    public IngredientController(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    /**
     * Get a list of all known ingredients
     *
     * @return list of all ingredients
     */
    @GetMapping("/list")
    public List<Ingredients> getAll() {
        Iterable<Ingredients> ingredientIterable = ingredientsService
                .getAllIngredients();
        List<Ingredients> allIngredients = new ArrayList<>();
        ingredientIterable.forEach(allIngredients::add);
        return allIngredients;
    }

    /**
     * Add an ingredient
     * For creating a new ingredient ID, initialize Ingredient with id = -1
     *
     * @param ingredient ingredient to add
     * @return ok if added, bad request if something went wrong
     */
    @PostMapping("/add")
    public ResponseEntity<Ingredients>
    add(@RequestBody Ingredients ingredient) {
        if (ingredient == null)
            return ResponseEntity.badRequest().build();

        String name = ingredient.getName();
        if (!isValidName(name))
            return ResponseEntity.badRequest().build();
        Ingredients savedIngredient = ingredientsService
                .addIngredient(ingredient);
        return ResponseEntity.ok(savedIngredient);
    }

    /**
     * Delete an ingredient
     *
     * @param ingredient ingredient to delete
     * @return ok if deleted, bad request if something went wrong
     */
    @PostMapping("/delete")
    public ResponseEntity<Ingredients> remove
    (@RequestBody Ingredients ingredient) {
        if (ingredient == null)
            return ResponseEntity.badRequest().build();

        if (ingredient.getId() == null || ingredient.getId() == -1) {
            return ResponseEntity.badRequest().build();
        }
        ingredientsService.deleteIngredient(ingredient.getId());
        return ResponseEntity.ok(ingredient);
    }

    /**
     * Check if an ingredient name is valid
     *
     * @param name ingredients name
     * @return true if valid
     */
    private boolean isValidName(String name) {
        return name != null && !name.isEmpty();
    }

    static class IngredientComparator implements Comparator<Ingredient> {
        @Override
        public int compare(Ingredient o1, Ingredient o2) {
            return Integer.compare(o1.getId(), o2.getId());
        }
    }
}
