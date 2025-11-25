/**
 * TODO: NOTE: Class will most likely be replaced by a class out of commons
 */
package server.temp;

public class Ingredient {
    private int id;
    private int quantity;
    private String name;

    /**
     * Create a new ingredient
     * @param id id of ingredient
     * @param quantity quantity of ingredient
     * @param name name of ingredient
     */
    public Ingredient(int id, int quantity, String name) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }
}
