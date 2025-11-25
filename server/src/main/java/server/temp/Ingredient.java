/**
 * TODO: NOTE: Class will most likely be replaced by a class out of commons
 */
package server.temp;

public class Ingredient {
    private int id;
    private String name;

    /**
     * Create a new ingredient
     * @param id id of ingredient
     * @param name name of ingredient
     */
    public Ingredient(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
