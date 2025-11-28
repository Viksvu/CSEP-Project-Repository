package commons;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class IngredientInRecipe extends TempIngredient {

    private int quantity;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_id")
    private Recipes recipes;

    public IngredientInRecipe() {
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(final Unit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IngredientInRecipe that = (IngredientInRecipe) o;
        return quantity == that.quantity && unit == that.unit && Objects.equals(recipes, that.recipes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, unit, recipes);
    }
}

