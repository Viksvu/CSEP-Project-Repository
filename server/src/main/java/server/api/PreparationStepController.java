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
    public List<PreparationStep> getPreparationSteps(@RequestParam Long recipeId) {
        Recipes recipe = recipeService.getRecipeById(recipeId);
        if (recipe == null) return null;
        return recipe.getPreparationSteps();
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
        if (isEmptyOrNull(recipeId) ||  isEmptyOrNull(preparationStep)) {
            return ResponseEntity.badRequest().build();
        }
        Recipes recipe = recipeService.getRecipeById(recipeId);
        if (recipe == null) return ResponseEntity.notFound().build();
        recipe.addPreparationStep(preparationStep);
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
        if (isEmptyOrNull(recipeId) ||  isEmptyOrNull(preparationStep)) {
            return ResponseEntity.badRequest().build();
        }
        Recipes recipe = recipeService.getRecipeById(recipeId);
        if (recipe == null) return ResponseEntity.badRequest().build();
        recipe.removePreparationStep(preparationStep);
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(preparationStep);
    }

    /**
     * For known objects: Preparation Step and Recipes and for
     * Strings, checks if any parameters are null or empty.
     * @param o Object to check for null parameters
     * @return a boolean determining whether it is null
     */
    private static boolean isEmptyOrNull(Object o) {
        return switch (o) {
            case null -> true;
            case String s -> s.isEmpty();
            case PreparationStep preparationStep ->
                    preparationStep.getDescription().isEmpty();
            case Recipes recipes -> recipes.getName().isEmpty();
            default -> false;
        };
    }
}