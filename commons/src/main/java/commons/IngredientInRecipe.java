package commons;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class IngredientInRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "temp_ingredient_id")
    private Ingredients tempIngredient;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_id")
    private Recipes recipes;

    /**
     * No-args constructor for JPA
     */
    public IngredientInRecipe() {}

    /**
     * constructor for testing
     * @param tempIngredient the ingredient
     */
    public IngredientInRecipe(Ingredients tempIngredient) {
        this.tempIngredient = tempIngredient;
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
        return tempIngredient;
    }

    public void setTempIngredient(final Ingredients tempIngredient) {
        this.tempIngredient = tempIngredient;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IngredientInRecipe that = (IngredientInRecipe) o;
        return quantity == that.quantity
                && Objects.equals(tempIngredient, that.tempIngredient)
                && unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tempIngredient, quantity, unit);
    }
}
