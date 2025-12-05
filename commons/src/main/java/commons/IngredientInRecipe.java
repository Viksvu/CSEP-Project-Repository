package commons;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class IngredientInRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(optional = false)
    private Ingredients ingredient;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @ManyToOne(optional = false)
    private Recipes recipes;

    /**
     * No-args constructor for JPA
     */
    public IngredientInRecipe() {}

    /**
     * constructor for testing
     * @param ingredient the ingredient
     */
    public IngredientInRecipe(Ingredients ingredient) {
        this.ingredient = ingredient;
    }

        public Recipes getRecipes() {
        return recipes;
    }

    public void setRecipes(Recipes recipes) {
        this.recipes = recipes;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(final Unit unit) {
        this.unit = unit;
    }

    public Ingredients getTempIngredient() {
        return ingredient;
    }

    public void setTempIngredient(final Ingredients tempIngredient) {
        this.ingredient = tempIngredient;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IngredientInRecipe that = (IngredientInRecipe) o;
        return quantity == that.quantity
                && Objects.equals(ingredient, that.ingredient)
                && unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredient, quantity, unit);
    }

    //Need to decide if only ingredient name should be shown or also quantity
    @Override
    public String toString() {
        if (getQuantity()==1) {
            return getTempIngredient().getName()
                    + " (" + getQuantity() + " " + getUnit()
                    .toString().replaceAll("/s", "") + ")";
        }
        return getTempIngredient().getName()
                + " (" + getQuantity() + " " + getUnit()
                .toString().replaceAll("/", "") + ")";
    }
}
