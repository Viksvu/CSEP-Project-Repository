package server.api;


import commons.PreparationStep;
import commons.Recipes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.RecipeService;

import java.util.List;

@RestController
@RequestMapping("/api/prep-step")
public class PreparationStepController {
    @Autowired
    public RecipeService recipeService;

    /**
     * Lists the preparation steps of a recipe
     * @param recipeId id of recipe to fetch preparation steps from
     * @return a List of all the preparation steps from the database.
     */
    @GetMapping("/list")
    public ResponseEntity<List<PreparationStep>> getPreparationSteps(@RequestParam Long recipeId) {
        Recipes recipe = recipeService.getRecipeByIdSafe(recipeId);
        if (recipe == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(recipe.getPreparationSteps());
    }

    /**
     * Adds a preparation step to any given recipe
     * @param recipeId get id of recipe to add to
     * @param preparationStep to add
     * @return the preparation step if successful, else return
     * a bad request.
     */
    @PostMapping("/add")
    public ResponseEntity<PreparationStep> addPreparationStep(
            @RequestParam Long recipeId,
            @RequestBody PreparationStep preparationStep) {
        if (isEmpty(preparationStep)) {
            return ResponseEntity.badRequest().build();
        }
        Recipes recipe = recipeService.getRecipeByIdSafe(recipeId);
        if (recipe == null) return ResponseEntity.notFound().build();
        recipe.addPreparationStep(preparationStep);
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(preparationStep);
    }

    /**
     * Adds a preparation step to any given recipe
     * @param recipeId get id of recipe to add to
     * @param preparationStep to add
     * @return the preparation step if successful, else return
     * a bad request.
     */
    @PostMapping("/edit")
    public ResponseEntity<PreparationStep> editPreparationStep(
            @RequestParam Long recipeId,
            @RequestParam int index,
            @RequestBody PreparationStep preparationStep) {
        if (isEmpty(preparationStep)) {
            return ResponseEntity.badRequest().build();
        }
        Recipes recipe = recipeService.getRecipeByIdSafe(recipeId);
        if (recipe == null) return ResponseEntity.notFound().build();
        recipe.getPreparationSteps().get(index).setDescription(preparationStep.getDescription());
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(preparationStep);
    }

    /**
     * Deletes a recipe from the list of all recipes
     * @param recipeId id of recipe to delete step from
     * @param preparationStep to be deleted
     * @return preparation step deleted if successful
     * and bad request if not successful
     */
    @PostMapping("/delete")
    public ResponseEntity<PreparationStep> deletePreparationStep(
            @RequestParam Long recipeId,
            @RequestBody PreparationStep preparationStep
    ) {
        if (isEmpty(preparationStep)) {
            return ResponseEntity.badRequest().build();
        }
        Recipes recipe = recipeService.getRecipeByIdSafe(recipeId);
        if (recipe == null) return ResponseEntity.notFound().build();
        recipe.removePreparationStep(preparationStep);
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(preparationStep);
    }

    /**
     * Check if a preparation step is null or empty
     * @param ps PreparationStep to check
     * @return true if null or empty
     */
    private boolean isEmpty(PreparationStep ps) {
        return ps.getDescription().isEmpty();
    }
}