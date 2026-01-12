package server.api;

import commons.Recipes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.RecipeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    RecipeService recipeService;

    /**
     * Public constructor for RecipeController
     * Springboot handles the dependency injection for the recipe service
     * @param recipeService service used for crud operations on recipes
     */
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
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
     * Add a recipe
     * For creating a new recipe ID, initialize Recipe with id = -1
     *
     * @param recipe recipe to add
     * @return ok if added, bad request if something went wrong
     */
    @PostMapping("/add")
    public ResponseEntity<Recipes> add(@RequestBody Recipes recipe) {
        String name = recipe.getName();
        if (!isValidName(name))
            return ResponseEntity.badRequest().build();
        Recipes savedRecipe = recipeService.addRecipe(recipe);
        return ResponseEntity.ok(savedRecipe);
    }

    /**
     * Delete a recipe
     *
     * @param recipe recipe to delete
     * @return ok if deleted, bad request if something went wrong
     */
    @PostMapping("/delete")
    public ResponseEntity<Recipes> remove(@RequestBody Recipes recipe) {
        if (recipe.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        recipeService.deleteRecipe(recipe.getId());
        return ResponseEntity.ok(recipe);
    }

    /**
     * Rename a recipe
     *
     * @param id   id of recipe to rename
     * @param name new name for the recipe
     * @return newly set name
     */
    @PostMapping("/rename")
    public ResponseEntity<String> rename
    (@RequestParam Long id, @RequestBody String name) {
        Recipes recipe;
        try {
            recipe = recipeService.getRecipeById(id);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
        recipe.setName(name);
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(name);
    }

    /**
     * Clones a given recipe
     * @param recipe recipe to clone
     * @param newName name of cloned recipe
     * @return the cloned recipe
     */
    @PostMapping("/clone")
    public ResponseEntity<Recipes> cloneRecipe(@RequestBody Recipes recipe,
                                               @RequestParam String newName)
    {
        if (!getAll().contains(recipe))
            return ResponseEntity.badRequest().build();
        Recipes retRecipe = recipe.cloneRecipes(newName);
        retRecipe.setRecipeOnIngredients();
        recipeService.addRecipe(retRecipe);
        return ResponseEntity.ok(retRecipe);
    }

    /**
     * Check if a recipe name is valid
     *
     * @param name recipes name
     * @return true if valid
     */
    private boolean isValidName(String name) {
        return name != null && !name.isEmpty();
    }
}
