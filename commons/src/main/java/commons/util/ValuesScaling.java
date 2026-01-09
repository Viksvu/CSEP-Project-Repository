package commons.util;

import commons.IngredientInRecipe;
import commons.Recipes;
import commons.Unit;

import java.util.List;
import java.util.stream.Collectors;

public class ValuesScaling {

    /**
     *
     * This scales the amount from 1000g to 1kg and vice versa
     *
     * @param ingredient
     * @param scaleFactor
     * @return
     */
    public static String getScaledAmount(IngredientInRecipe ingredient, double scaleFactor) {

        Unit unit = ingredient.getUnit();
        double quantity = ingredient.getQuantity();

        if (!isScalable(unit)) {
            return unit.toString();
        }

        double scaledQuantity = quantity * scaleFactor;

        if (unit == Unit.GRAM && scaledQuantity >= 1000) {
            return format(scaledQuantity / 1000) + " " + Unit.KILOGRAM.toString()
                    .replaceAll("/s", "");
        }

        if (unit == Unit.MILLILITER && scaledQuantity >= 1000) {
            return format(scaledQuantity / 1000) + " " + Unit.LITER.toString()
                    .replaceAll("/s", "");
        }

        if (unit == Unit.KILOGRAM && scaledQuantity < 1) {
            return format(scaledQuantity * 1000) + " " + Unit.GRAM.toString()
                    .replaceAll("/s", "");
        }

        if (unit == Unit.LITER && scaledQuantity < 1) {
            return format(scaledQuantity * 1000) + " " + Unit.MILLILITER.toString()
                    .replaceAll("/s", "");
        }

        return format(scaledQuantity) + " " + unit.toString().replaceAll("/s", "");
    }

    /**
     *
     * this checks if the unit is scalable
     *
     * @param unit
     * @return
     */
    private static boolean isScalable(Unit unit) {
        return switch (unit) {
            case GRAM, KILOGRAM, LITER, MILLILITER, TABLE_SPOON,
                 TEASPOON, CUP, PINCH, PIECE -> true;
            default -> false;
        };
    }

    /**
     *
     * this formats the double value to remove trailing zeros
     *
     * @param value
     * @return
     */
    private static String format(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            return String.format("%.2f", value);
        }
    }

    /**
     *
     * scales the ingredients in a recipe by a given factor
     *
     * @param recipe
     * @param scaleFactor
     * @return
     */
    public static List<IngredientInRecipe> scaleRecipeIngredients(Recipes recipe, double scaleFactor) {
        return recipe.getIngredients()
                .stream()
                .map(ingredient -> {
                    IngredientInRecipe cloned = ingredient.cloneIngredientInRecipe();

                    cloned.setQuantity((int) Math.round(cloned.getQuantity() * scaleFactor));
                    return cloned;
                })
                .collect(Collectors.toList());
    }
}

