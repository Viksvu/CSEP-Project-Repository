package server.api;

import commons.IngredientInRecipe;
import commons.Recipes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.RecipeService;
import server.services.TempRecipeService;

import java.util.List;

@RestController
@RequestMapping("/api/recipeingredient")
public class IngredientInRecipeController {
    RecipeService recipeService;

    public IngredientInRecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Get all Ingredients in a Recipe
      * @param id id of recipe
     * @return List with IngredientInRecipe
     */
    @GetMapping("/get")
    public ResponseEntity<List<IngredientInRecipe>> get(@RequestParam long id) {
        if (id == 0) return ResponseEntity.badRequest().build();
        Recipes recipe;
        try {
            recipe = recipeService.getRecipeById(id);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(recipe.getIngredients());
    }

    /**
     * Add an ingredient to a recipe
     * @param id id of the recipe
     * @param ingredient ingredient to add to the recipe
     * @return added ingredient
     */
    @PostMapping("/add")
    public ResponseEntity<IngredientInRecipe> add(
            @RequestParam long id,
            @RequestBody IngredientInRecipe ingredient) {
        if (ingredient == null) return ResponseEntity.badRequest().build();

        Recipes recipe;
        try {
            recipe = recipeService.getRecipeById(id);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
        recipe.addIngredient(ingredient);
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(ingredient);
    }

    /**
     * Delete an ingredient from a recipe
     * @param id id of recipe
     * @param ingredient ingredient to delete
     * @return deleted ingredient
     */
    @PostMapping("/delete")
    public ResponseEntity<IngredientInRecipe> delete(
            @RequestParam long id,
            @RequestBody IngredientInRecipe ingredient) {
        if (ingredient == null) return ResponseEntity.badRequest().build();

        Recipes recipe;
        try {
            recipe = recipeService.getRecipeById(id);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
        recipe.removeIngredient(ingredient);
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(ingredient);
    }
}
