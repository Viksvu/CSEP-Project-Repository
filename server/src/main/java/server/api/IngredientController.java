package server.api;

import commons.Ingredients;
import commons.request.AddIngredientRequest;
import commons.request.DeleteIngredientRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.IngredientsService;

import java.util.ArrayList;
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
     * @param request request with ingredient to add
     * @return ok if added, bad request if something went wrong
     */
    @PostMapping("/add")
    public ResponseEntity<Ingredients>
    add(@RequestBody @Valid AddIngredientRequest request) {
        Ingredients savedIngredient = ingredientsService
                .addIngredient(request.ingredient());
        return ResponseEntity.ok(savedIngredient);
    }

    /**
     * Delete an ingredient
     *
     * @param request request with ingredient to delete
     * @return ok if deleted, bad request if something went wrong
     */
    @PostMapping("/delete")
    public ResponseEntity<Ingredients> remove
    (@RequestBody @Valid DeleteIngredientRequest request) {
        if (request.ingredient().getId() == null || request.ingredient().getId() == -1) {
            return ResponseEntity.badRequest().build();
        }
        ingredientsService.deleteIngredient(request.ingredient().getId());
        return ResponseEntity.ok(request.ingredient());
    }
}
