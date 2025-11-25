package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.temp.Ingredient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {
    // TODO: ingredients should be replaced with appropriate JpaRepository Class
    public List<Ingredient> ingredients = new ArrayList<>();

    /**
     * Get a list of all known ingredients
     * @return list of all ingredients
     */
    @GetMapping("/list")
    public List<Ingredient> getAll() {
        return this.ingredients;
    }

    /**
     * Add an ingredient
     * @param ingredient ingredient to add
     * @return ok if added, bad request if something went wrong
     */
    @PostMapping("/add")
    public ResponseEntity<Ingredient> add(@RequestBody Ingredient ingredient) {
        if (ingredient == null)
            return ResponseEntity.badRequest().build();

        String name = ingredient.getName();
        if (!isValidName(name))
            return ResponseEntity.badRequest().build();

        int id = ingredient.getId();
        if (id == -1) {
            id = getNewID();
            ingredient = new Ingredient(id, name);
        }

        this.ingredients.add(ingredient);
        return ResponseEntity.ok(ingredient);
    }

    /**
     * Delete an ingredient
     * @param ingredient ingredient to delete
     * @return ok if deleted, bad request if something went wrong
     */
    @PostMapping("/delete")
    public ResponseEntity<Ingredient> remove(@RequestBody Ingredient ingredient) {
        if (ingredient == null)
            return ResponseEntity.badRequest().build();

        if (!ingredientExists(ingredient.getId()))
            return ResponseEntity.badRequest().build();

        this.ingredients.removeIf(r -> r.getId() == ingredient.getId());
        return ResponseEntity.ok(ingredient);
    }

    /**
     * Get a new unused id for a new ingredient
     * @return new unused ingredient id
     */
    private int getNewID() {
        List<Ingredient> ingredients = this.getAll();
        ingredients.sort(new IngredientComparator());

        if (ingredients.isEmpty()) return 0;

        int id = ingredients.getLast().getId();
        while (ingredientExists(id)) {
            id++;
        }

        return ingredients.getLast().getId() + 1;
    }

    /**
     * Check if an ingredient name is valid
     * @param name ingredients name
     * @return true if valid
     */
    private boolean isValidName(String name) {
        return name != null && !name.isEmpty();
    }

    /**
     * Check if an ingredient already exists
     * @param id id of the ingredient
     * @return true if already existing
     */
    private boolean ingredientExists(int id) {
        return this.ingredients.stream()
            .anyMatch(r -> r.getId() == id);
    }

    static class IngredientComparator implements Comparator<Ingredient> {
        @Override
        public int compare(Ingredient o1, Ingredient o2) {
            return Integer.compare(o1.getId(), o2.getId());
        }
    }
}
