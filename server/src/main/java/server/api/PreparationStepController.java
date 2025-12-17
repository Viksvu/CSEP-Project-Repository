package server.api;


import commons.PreparationStep;
import commons.Recipes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.TempRecipeService;

import java.util.List;

@RestController
@RequestMapping("/api/prep-step")
public class PreparationStepController {
    public TempRecipeService recipeService = TempRecipeService.get();
    private List<Recipes> allRecipes = recipeService.getAllRecipes();

    /**
     * Lists the preparation steps of a recipe
     * @param recipes to list the preparation steps of
     * @return a List of all the preparation steps from the database.
     */
    @GetMapping("/list")
    public List<PreparationStep> getPreparationSteps(@RequestBody Recipes recipes) {
        long id = recipes.getId();
        Recipes recipe = recipeService.getRecipeById(id);
        return recipe.getPreparationSteps();
    }

    /**
     * Adds a preparation step to any given recipe
     * @param recipes recipe to add the step
     * @param preparationStep to add
     * @return the preparation step if successful, else return
     * a bad request.
     */
    @PostMapping("/add")
    public ResponseEntity<PreparationStep> addPreparationStep(
            @RequestBody Recipes recipes,
            @RequestParam PreparationStep preparationStep) {
        if (isEmptyOrNull(recipes) ||  isEmptyOrNull(preparationStep)) {
            return ResponseEntity.badRequest().build();
        }
        long id = recipes.getId();
        Recipes recipe = recipeService.getRecipeById(id);
        recipe.addPreparationStep(preparationStep);
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(preparationStep);
    }

    /**
     * Deletes a recipe from the list of all recipes
     * @param recipes to delete step from
     * @param preparationStep to be deleted
     * @return preparation step deleted if successful
     * and bad request if not successful
     */
    @PostMapping("/delete")
    public ResponseEntity<PreparationStep> deletePreparationStep(
            @RequestBody Recipes recipes,
            @RequestParam PreparationStep preparationStep
    ) {
        if (isEmptyOrNull(recipes) ||  isEmptyOrNull(preparationStep)) {
            return ResponseEntity.badRequest().build();
        }
        long id = recipes.getId();
        Recipes recipe = recipeService.getRecipeById(id);
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
