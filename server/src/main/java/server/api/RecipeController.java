package server.api;

import commons.Recipes;
import commons.request.AddRecipeRequest;
import commons.request.CloneRecipeRequest;
import commons.request.DeleteRecipeRequest;
import commons.request.RenameRecipeRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.RecipeService;
import server.services.RecipeSocketService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    RecipeService recipeService;
    RecipeSocketService recipeSocketService;

    /**
     * Public constructor for RecipeController
     * Springboot handles the dependency injection for the recipe service
     * @param recipeService service used for crud operations on recipes
     */
    public RecipeController(RecipeService recipeService,
                            RecipeSocketService recipeSocketService) {
        this.recipeService = recipeService;
        this.recipeSocketService=recipeSocketService;
    }

    /**
     * Get a list of all known recipes
     *
     * @return list of all recipes
     */
    @GetMapping("/list")
    public List<Recipes> getAll() {
        Iterable<Recipes> allRecipesIterable = recipeService.getAllRecipes();
        List<Recipes> allRecipes = new ArrayList<>();
        allRecipesIterable.forEach(r -> {
            Recipes recipe = new Recipes(r.getName());
            recipe.setId(r.getId());
            allRecipes.add(r);
        });
        return allRecipes;
    }


    /**
     * Returns needed recipe to the client
     *
     * @return the recipe
     */
    @GetMapping("/get")
    public ResponseEntity<Recipes> get(@RequestParam Long id) throws RecipeService.RecipeNotFoundException {
        if (id == 0) return ResponseEntity.badRequest().build();
        Recipes recipe = recipeService.getRecipeById(id);
        return ResponseEntity.ok(recipe);
    }


    /**
     * Add a recipe
     * @param request request with recipe to add
     * @return newly saved recipe
     */
    @PostMapping("/add")
    public ResponseEntity<Recipes> add(
            @RequestBody @Valid AddRecipeRequest request) {
        Recipes savedRecipe = recipeService.addRecipe(request.recipe());
        recipeSocketService.recipeAdded(savedRecipe.getId());
        return ResponseEntity.ok(savedRecipe);
    }

    /**
     * Delete a recipe
     *
     * @param request request with recipe to delete
     * @return ok if deleted, bad request if something went wrong
     */
    @PostMapping("/delete")
    public ResponseEntity<Recipes> remove(
            @RequestBody @Valid DeleteRecipeRequest request) {
        if (request.recipe().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        recipeService.deleteRecipe(request.recipe().getId());
        return ResponseEntity.ok(request.recipe());
    }

    /**
     * Rename a recipe
     * @param request request with required information
     * @return newly set name
     */
    @PostMapping("/rename")
    public ResponseEntity<String> rename(
            @RequestBody @Valid RenameRecipeRequest request) {
        Recipes recipe = recipeService.getRecipeByIdSafe(request.recipeId());
        if (recipe == null) {
            return ResponseEntity.badRequest().build();
        }
        recipe.setName(request.newName());
        recipeSocketService.recipeAdded(recipe.getId());
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(request.newName());
    }

    /**
     * Clones a given recipe
     * @param request request with required information
     * @return the cloned recipe
     */
    @PostMapping("/clone")
    public ResponseEntity<Recipes> cloneRecipe(
            @RequestBody @Valid CloneRecipeRequest request) {
        if (!getAll().contains(request.recipe()))
            return ResponseEntity.badRequest().build();
        Recipes retRecipe = request.recipe().cloneRecipes(request.newName());
        retRecipe.setRecipeOnIngredients();
        recipeService.addRecipe(retRecipe);
        recipeSocketService.recipeAdded(retRecipe.getId());
        return ResponseEntity.ok(retRecipe);
    }
}
