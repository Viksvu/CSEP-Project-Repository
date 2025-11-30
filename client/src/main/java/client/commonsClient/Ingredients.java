package client.commonsClient;

import java.util.Objects;

public class Ingredients {


    private String name;
    private String ingredient;

    /**
     * No-args constructor for JPA.
     */
    public Ingredients() {
    }

    /**
     * Constructor for in use code.
     *
     * @param name the name of the ingredient.
     * @param quantity the quantity of the ingredient.
     * @param ingredient the ingredient itself.
     * @param unit the unit of measurement for the ingredient.
     */
    public Ingredients(String name, double quantity,
                       String ingredient, String unit) {
        this.name = name;
        this.ingredient = ingredient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ingredients that = (Ingredients) o;
        return Objects.equals(name, that.name)
                && Objects.equals(ingredient, that.ingredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ingredient);
    }
}
