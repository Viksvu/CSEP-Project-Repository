package client.commonsClient;

import commons.IngredientInRecipe;
import commons.Ingredients;
import commons.Recipes;
import commons.Unit;

import java.util.Objects;

public class IngredientInShoppingList {

    private Ingredients ingredient;
    private int quantity;
    private Recipes recipe;
    private Unit unit;

    /**
     * A constructor for IISL, this just constructs an ingredient directly
     *
     * @param ingredient the ingredient.
     * @param quantity   the quantity.
     */
    public IngredientInShoppingList(Ingredients ingredient,
                                    int quantity, Unit unit) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;

    }

    /**
     * A constructor for IISL, takes an IIR
     *
     * @param ingredientInRecipe the ingredient from recipe.
     */
    public IngredientInShoppingList(IngredientInRecipe ingredientInRecipe) {
        this.ingredient = ingredientInRecipe.getIngredient();
        this.quantity = ingredientInRecipe.getQuantity();
        this.recipe = ingredientInRecipe.getRecipes();
        this.unit = ingredientInRecipe.getUnit();
    }

    /**
     * No arg constructor. For JPA.
     */
    public IngredientInShoppingList() {

    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Ingredients getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredients ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public Recipes getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipes recipe) {
        this.recipe = recipe;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IngredientInShoppingList that = (IngredientInShoppingList) o;
        return quantity == that.quantity &&
                Objects.equals(ingredient, that.ingredient) &&
                Objects.equals(recipe, that.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredient, quantity, recipe);
    }


    @Override
    public String toString() {
        if (quantity == 1) {
            return ingredient.getName()
                    + " (" + quantity + " " + unit.toString()
                    .replaceAll("/s", "") + ")";
        }
        String s = ingredient.getName()
                + " (" + quantity + " " + unit.toString()
                .replaceAll("/", "") + ")";
        if (recipe != null) s += "(" + recipe.getName() + ")";
        return s;
    }
}
