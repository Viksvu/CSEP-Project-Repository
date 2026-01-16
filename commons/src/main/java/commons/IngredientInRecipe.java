package commons;

import com.fasterxml.jackson.annotation.JsonBackReference;
import commons.util.ValuesScaling;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
public class IngredientInRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    private Ingredients ingredient;

    @Min(
        value = 0,
        message = "cannot add negative quantity"
    )
    private int quantity;

    @NotNull(
        message = "unit cannot be null"
    )
    @Enumerated(EnumType.STRING)
    private Unit unit;

    @ManyToOne(optional = false)
    @JsonBackReference
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

    /**
     * constructor for testing
     * @param ingredient the ingredient
     * @param quantity the quantity
     * @param unit the unit
     */
    public IngredientInRecipe(Ingredients ingredient, int quantity, Unit unit) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
    }

    /**
     * Clones "this" by cloning all the attributes
     * @return the clone
     */
    public IngredientInRecipe cloneIngredientInRecipe(){
        IngredientInRecipe ingredientInRecipe =
                new IngredientInRecipe(ingredient.cloneIngredients());
        ingredientInRecipe.setQuantity(this.quantity);
        ingredientInRecipe.setUnit(this.unit);
        return ingredientInRecipe;
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

    public Ingredients getIngredient() {
        return ingredient;
    }

    public void setIngredient(final Ingredients tempIngredient) {
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
        double scaleFactor = 1.0; // we need to change this one if we want dynamic scaling
        String scaledAmount = ValuesScaling.getScaledAmount(this, scaleFactor);
        if (getQuantity() == 1) {
        return getIngredient().getName()
                + " (" + scaledAmount + ")";
    }
        return getIngredient().getName()
                + " (" + scaledAmount + ")";
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
