package client.utils;

import commons.IngredientInRecipe;

import java.util.List;

public class MacroCalc {

    /**
     * Calculates the total fat content of a list of ingredients.
     * @param ingredients
     * @return
     */
    public static double totalFat(List<IngredientInRecipe> ingredients) {
        return ingredients.stream()
                .mapToDouble(i ->
                        (i.getQuantity() / 100.0)
                                * i.getIngredient().getFatPer100g())
                .sum();
    }

    /**
     * Calculates the total fat content of a list of ingredients.
     * @param ingredients
     * @return
     */
    public static double totalCarbs(List<IngredientInRecipe> ingredients) {
        return ingredients.stream()
                .mapToDouble(i ->
                        (i.getQuantity() / 100.0)
                                * i.getIngredient().getCarbsPer100g())
                .sum();
    }

    /**
     * Calculates the total protein content of a list of ingredients.
     * @param ingredients
     * @return
     */
    public static double totalProtein(List<IngredientInRecipe> ingredients) {
        return ingredients.stream()
                .mapToDouble(i ->
                        (i.getQuantity() / 100.0)
                                * i.getIngredient().getProteinPer100g())
                .sum();
    }
}
