package commons;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class IngredientInRecipe {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @ManyToOne
    private TempIngredient tempIngredient;
    private int quantity;
    private Unit unit;
    @ManyToOne
    private Recipes recipes;
    public IngredientInRecipe() {
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
    public TempIngredient getTempIngredient() {
        return tempIngredient;
    }
    public void setTempIngredient(final TempIngredient tempIngredient) {
        this.tempIngredient = tempIngredient;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IngredientInRecipe that = (IngredientInRecipe) o;
        return quantity == that.quantity && Objects.equals(tempIngredient, that.tempIngredient) && unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tempIngredient, quantity, unit);
    }
}
