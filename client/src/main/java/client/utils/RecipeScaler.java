package client.utils;

import commons.IngredientInRecipe;
import commons.Recipes;
import commons.util.ValuesScaling;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeScaler {

    /**
     * Scales the ingredients of a recipe by a given factor.
     * @param recipe
     * @param scaleFactor
     * @return
     */
    public static List<IngredientInRecipe> scaleIngredients(
            Recipes recipe, double scaleFactor) {


        ValuesScaling.setScaleFactor(scaleFactor);

        return recipe.getIngredients()
                .stream()
                .map(ing -> {
                    IngredientInRecipe clone = ing.cloneIngredientInRecipe();
                    clone.setQuantity((int) (ing.getQuantity() * scaleFactor));
                    return clone;
                })
                .collect(Collectors.toList());
    }
}
