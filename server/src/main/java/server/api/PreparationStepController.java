package server.api;

import commons.PreparationStep;
import commons.Recipes;
import commons.request.AddPreparationStepRequest;
import commons.request.DeletePreparationStepRequest;
import commons.request.EditPreparationStepRequest;
import commons.request.ListPreparationStepRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.RecipeService;

import java.util.List;

@RestController
@RequestMapping("/api/prep-step")
public class PreparationStepController {

    public final RecipeService recipeService;

    /**
     * Constructor for PreparationStepController
     * @param recipeService spring-injected recipe service
     */
    public PreparationStepController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Lists the preparation steps of a recipe
     * @param request request with required information
     * @return a List of all the preparation steps from the database.
     */
    @GetMapping("/list")
    public ResponseEntity<List<PreparationStep>> getPreparationSteps(
            @RequestBody @Valid ListPreparationStepRequest request) {
        Recipes recipe = recipeService.getRecipeByIdSafe(request.recipeId());
        if (recipe == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(recipe.getPreparationSteps());
    }

    /**
     * Adds a preparation step to any given recipe
     * @param request request with required information
     * @return the preparation step if successful, else return
     * a bad request.
     */
    @PostMapping("/add")
    public ResponseEntity<PreparationStep> addPreparationStep(
            @RequestBody @Valid AddPreparationStepRequest request) {
        Recipes recipe = recipeService.getRecipeByIdSafe(request.recipeId());
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        recipe.addPreparationStep(request.preparationStep());
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(request.preparationStep());
    }

    /**
     * Adds a preparation step to any given recipe
     * @param request request with required information
     * @return the preparation step if successful, else return
     * a bad request.
     */
    @PostMapping("/edit")
    public ResponseEntity<PreparationStep> editPreparationStep(
            @RequestBody @Valid EditPreparationStepRequest request) {
        Recipes recipe = recipeService.getRecipeByIdSafe(request.recipeId());
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }

        List<PreparationStep> steps = recipe.getPreparationSteps();
        if (request.index() >= steps.size()) {
            return ResponseEntity.notFound().build();
        }

        steps.get(request.index()).setDescription(request.preparationStep().getDescription());
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(request.preparationStep());
    }

    /**
     * Deletes a recipe from the list of all recipes
     * @param request request with required information
     * @return preparation step deleted if successful
     * and bad request if not successful
     */
    @PostMapping("/delete")
    public ResponseEntity<PreparationStep> deletePreparationStep(
            @RequestBody @Valid DeletePreparationStepRequest request) {
        Recipes recipe = recipeService.getRecipeByIdSafe(request.recipeId());
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        recipe.removePreparationStep(request.preparationStep());
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(request.preparationStep());
    }
}