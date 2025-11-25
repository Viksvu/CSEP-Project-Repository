/**
 * TODO: NOTE: Class will most likely be replaced by a class out of commons
 */
package server.temp;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private List<Ingredient> ingredients = new ArrayList<>();
    private String name;
    private int id;

    /**
     * Create a new recipe
     * @param id int recipe id
     * @param name string recipe name
     */
    public Recipe(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String newName) {
        this.name = newName;
    }
}
