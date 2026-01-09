package commons.util;

import commons.Recipes;
import commons.IngredientInRecipe;

/**
 * calculates total nutrition values for a recipe
 */
public class NutritionTotal {

    /**
     *
     * calculates total kcal for a recipe
     *
     * @param recipe
     * @return
     */
    public int calculateTotalKcal(Recipes recipe) {
        return recipe.getIngredients()
                .stream()
                .mapToInt(this::calculateKcalForIngredient)
                .sum();
    }

    /**
     *
     * calculates total carbs for a recipe
     *
     * @param recipe
     * @return
     */
    public int calculateTotalCarbs(Recipes recipe) {
        return recipe.getIngredients()
                .stream()
                .mapToInt(this::calculateCarbsForIngredient)
                .sum();
    }

    /**
     *
     * calculates total fat for a recipe
     *
     * @param recipe
     * @return
     */
    public int calculateTotalFat(Recipes recipe) {
        return recipe.getIngredients()
                .stream()
                .mapToInt(this::calculateFatForIngredient)
                .sum();
    }

    /**
     *
     * calculates total protein for a recipe
     *
     * @param recipe
     * @return
     */
    public int calculateTotalProtein(Recipes recipe) {
        return recipe.getIngredients()
                .stream()
                .mapToInt(this::calculateProteinForIngredient)
                .sum();
    }


    /**
     *
     * calculates kcal for each ingredient in recipe
     *
     * @param ingredient
     * @return
     */
    private int calculateKcalForIngredient(IngredientInRecipe ingredient) {
        return (int) (ingredient.getQuantity()
                * ingredient.getIngredient().getKcalPer100g());
    }

    /**
     *
     * calculates carbs for each ingredient in recipe
     *
     * @param ingredient
     * @return
     */
    private int calculateCarbsForIngredient(IngredientInRecipe ingredient) {
        return (int) (ingredient.getQuantity()
                * ingredient.getIngredient().getCarbsPer100g());
    }

    /**
     *
     * calculates fat for each ingredient in recipe
     *
     * @param ingredient
     * @return
     */
    private int calculateFatForIngredient(IngredientInRecipe ingredient) {
        return (int) (ingredient.getQuantity()
                * ingredient.getIngredient().getFatPer100g());
    }

    /**
     *
     * calculates protein for each ingredient in recipe
     *
     * @param ingredient
     * @return
     */
    private int calculateProteinForIngredient(IngredientInRecipe ingredient) {
        return (int) (ingredient.getQuantity()
                * ingredient.getIngredient().getProteinPer100g());
    }
}
