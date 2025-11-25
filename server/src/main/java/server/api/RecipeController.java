package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.temp.Recipe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    // TODO: recipes should be replaced with appropriate JpaRepository Class
    public List<Recipe> recipes = new ArrayList<>();

    /**
     * Get a list of all known recipes
     * @return list of all recipes
     */
    @GetMapping("/list")
    public List<Recipe> getAll() {
        return this.recipes;
    }

    /**
     * Add a recipe
     * For creating a new recipe ID, initialize Recipe with id = -1
     * @param recipe recipe to add
     * @return ok if added, bad request if something went wrong
     */
    @PostMapping("/add")
    public ResponseEntity<Recipe> add(@RequestBody Recipe recipe) {
        if (recipe == null)
            return ResponseEntity.badRequest().build();

        String name = recipe.getName();
        if (!isValidName(name))
            return ResponseEntity.badRequest().build();

        int id = recipe.getId();
        if (id == -1) {
            id = getNewID();
            recipe = new Recipe(id, name);
        }

        this.recipes.add(recipe);
        return ResponseEntity.ok(recipe);
    }

    /**
     * Delete a recipe
     * @param recipe recipe to delete
     * @return ok if deleted, bad request if something went wrong
     */
    @PostMapping("/delete")
    public ResponseEntity<Recipe> remove(@RequestBody Recipe recipe) {
        if (recipe == null)
            return ResponseEntity.badRequest().build();

        if (!recipeExists(recipe.getId()))
            return ResponseEntity.badRequest().build();

        this.recipes.removeIf(r -> r.getId() == recipe.getId());
        return ResponseEntity.ok(recipe);
    }

    /**
     * Rename a recipe
     * @param id id of recipe to rename
     * @param name new name for the recipe
     * @return newly set name
     */
    @PostMapping("/rename")
    public ResponseEntity<String> rename(@RequestParam int id, @RequestBody String name) {
        if (!recipeExists(id) || !isValidName(name))
            return ResponseEntity.badRequest().build();

        this.recipes.stream()
                .filter(r -> r.getId() == id)
                .forEach(r -> r.setName(name));

        return ResponseEntity.ok(name);
    }

    /**
     * Get a new unused id for a new recipe
     * @return new unused recipe id
     */
    private int getNewID() {
        List<Recipe> recipes = this.getAll();
        recipes.sort(new RecipeComparator());

        if (recipes.isEmpty()) return 0;

        int id = recipes.getLast().getId();
        while (recipeExists(id)) {
            id++;
        }

        return recipes.getLast().getId() + 1;
    }

    /**
     * Check if a recipe name is valid
     * @param name recipes name
     * @return true if valid
     */
    private boolean isValidName(String name) {
        return name != null && !name.isEmpty();
    }

    /**
     * Check if a recipe already exists
     * @param id id of the recipe
     * @return true if already existing
     */
    private boolean recipeExists(int id) {
        return this.recipes.stream()
            .anyMatch(r -> r.getId() == id);
    }

    static class RecipeComparator implements Comparator<Recipe> {
        @Override
        public int compare(Recipe o1, Recipe o2) {
            return Integer.compare(o1.getId(), o2.getId());
        }
    }
}
