package client.utils;

import commons.IngredientInRecipe;
import commons.Unit;

import java.util.List;

public class RecipeTotalNutritionCalc {

    /**
     * Calculates the total kcal per 100g of a list of ingredients.
     */
    public static int calculateKcalPer100g(List<IngredientInRecipe> ingredients) {
        double totalGrams = 0;
        double totalKcal = 0;

        for (IngredientInRecipe ing : ingredients) {
            if (!isFormalUnit(ing.getUnit())) continue;

            double grams = toGrams(ing.getQuantity(), ing.getUnit());
            totalGrams += grams;

            totalKcal += (grams / 100.0)
                    * ing.getIngredient().getKcalPer100g();
        }

        if (totalGrams == 0) return 0;
        return (int) Math.round((totalKcal / totalGrams) * 100);
    }

    /**
     * Calculates total fat per 100g of recipe
     */
    public static double calculateFatPer100g(List<IngredientInRecipe> ingredients) {
        return calculateMacroPer100g(ingredients, Macro.FAT);
    }

    /**
     * Calculates total carbs per 100g of recipe
     */
    public static double calculateCarbsPer100g(List<IngredientInRecipe> ingredients) {
        return calculateMacroPer100g(ingredients, Macro.CARBS);
    }

    /**
     * Calculates total protein per 100g of recipe
     */
    public static double calculateProteinPer100g(List<IngredientInRecipe> ingredients) {
        return calculateMacroPer100g(ingredients, Macro.PROTEIN);
    }


    /**
     * Calculates the specified macro nutrient per 100g of the recipe.
     * @param ingredients
     * @param macro
     * @return
     */
    private static double calculateMacroPer100g(
            List<IngredientInRecipe> ingredients, Macro macro) {

        double totalGrams = 0;
        double totalMacro = 0;

        for (IngredientInRecipe ing : ingredients) {
            if (!isFormalUnit(ing.getUnit())) continue;

            double grams = toGrams(ing.getQuantity(), ing.getUnit());
            totalGrams += grams;

            double valuePer100g = switch (macro) {
                case FAT -> ing.getIngredient().getFatPer100g();
                case CARBS -> ing.getIngredient().getCarbsPer100g();
                case PROTEIN -> ing.getIngredient().getProteinPer100g();
            };

            totalMacro += (grams / 100.0) * valuePer100g;
        }

        if (totalGrams == 0) return 0;
        return Math.round((totalMacro / totalGrams) * 100.0) / 100.0;
    }

    private enum Macro {
        FAT, CARBS, PROTEIN
    }

    /**
     * Checks if the unit is formal (gram, kilogram, milliliter, liter)
     * @param unit
     * @return
     */
    private static boolean isFormalUnit(Unit unit) {
        return switch (unit) {
            case GRAM, KILOGRAM, MILLILITER, LITER -> true;
            default -> false;
        };
    }

    /**
     * Converts the amount to grams based on the unit.
     * @param amount
     * @param unit
     * @return
     */
    private static double toGrams(double amount, Unit unit) {
        return switch (unit) {
            case GRAM -> amount;
            case KILOGRAM -> amount * 1000;
            case MILLILITER -> amount;
            case LITER -> amount * 1000;
            default -> 0;
        };
    }
}
