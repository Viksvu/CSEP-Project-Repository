package client.utils;

import commons.IngredientInRecipe;
import commons.Unit;

import java.util.List;

public class RecipeTotalNutritionCalc {

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

    private static boolean isFormalUnit(Unit unit) {
        return switch (unit) {
            case GRAM, KILOGRAM, MILLILITER, LITER -> true;
            default -> false;
        };
    }

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
