package server.api;

import commons.IngredientInRecipe;
import commons.Ingredients;
import commons.Recipes;
import commons.Unit;
import commons.request.AddIngredientInRecipeRequest;
import commons.request.DeleteIngredientInRecipeRequest;
import commons.request.EditIngredientInRecipeRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.RecipeService;
import java.util.List;

@RestController
@RequestMapping("/api/recipeingredient")
public class IngredientInRecipeController {
    RecipeService recipeService;

    /**
     * Public constructor for the controller
     * Needed for spring boot to take care of the dependency injection
     * @param recipeService the service needed to do crud operations on recipes
     */
    public IngredientInRecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Get all Ingredients in a Recipe
      * @param id id of recipe
     * @return List with IngredientInRecipe
     */
    @GetMapping("/get")
    public ResponseEntity<List<IngredientInRecipe>> get(@RequestParam Long id) {
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
     * @param request information about what to add
     * @return added ingredient
     */
    @PostMapping("/add")
    public ResponseEntity<IngredientInRecipe> add(
            @RequestBody @Valid AddIngredientInRecipeRequest request) {
        Recipes recipe = this.recipeService
                .getRecipeByIdSafe(request.recipeId());
        if (recipe == null) {
            return ResponseEntity.badRequest().build();
        }
        recipe.addIngredient(request.ingredient());
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(request.ingredient());
    }


    /**
     * Add an ingredient to a recipe
     * @param request information about what to edit to what
     * @return added ingredient
     */
    @PostMapping("/edit")
    public ResponseEntity<IngredientInRecipe> edit(
            @RequestBody @Valid EditIngredientInRecipeRequest request) {
        Recipes recipe = this.recipeService
                .getRecipeByIdSafe(request.recipeId());
        if (recipe == null) {
            return ResponseEntity.badRequest().build();
        }
        Ingredients ingredient = request.ingredient().getIngredient();
        int quantity = request.ingredient().getQuantity();
        Unit unit = request.ingredient().getUnit();

        for (IngredientInRecipe ingredientInRecipe : recipe.getIngredients()) {
            if (!ingredientInRecipe.getId()
                    .equals(request.ingredient().getId())) continue;
            ingredientInRecipe.setIngredient(ingredient);
            ingredientInRecipe.setQuantity(quantity);
            ingredientInRecipe.setUnit(unit);
        }
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(request.ingredient());
    }


    /**
     * Delete an ingredient from a recipe
     * @param request with all needed information
     * @return deleted ingredient
     */
    @PostMapping("/delete")
    public ResponseEntity<IngredientInRecipe> delete(
            @RequestBody @Valid DeleteIngredientInRecipeRequest request) {
        Recipes recipe = this.recipeService
                .getRecipeByIdSafe(request.recipeId());
        if (recipe == null) {
            return ResponseEntity.badRequest().build();
        }
        recipe.removeIngredient(request.ingredient());
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(request.ingredient());
    }
}
