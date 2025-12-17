package server.api;

import commons.Recipes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.RecipeService;

import java.util.ArrayList;
import java.util.Comparator;
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
        allRecipesIterable.forEach(allRecipes::add);
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
        if (recipe == null)
            return ResponseEntity.badRequest().build();

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
        if (recipe == null)
            return ResponseEntity.badRequest().build();

        if (recipe.getId() == 0) {
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
        if (!isValidName(name))
            return ResponseEntity.badRequest().build();
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
     * Check if a recipe name is valid
     *
     * @param name recipes name
     * @return true if valid
     */
    private boolean isValidName(String name) {
        return name != null && !name.isEmpty();
    }

    static class RecipeComparator implements Comparator<Recipes> {
        @Override
        public int compare(Recipes o1, Recipes o2) {
            return Long.compare(o1.getId(), o2.getId());
        }
    }
}
