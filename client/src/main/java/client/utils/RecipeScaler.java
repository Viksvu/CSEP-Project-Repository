package client.utils;

import commons.IngredientInRecipe;
import commons.Recipes;


import java.util.List;
import java.util.stream.Collectors;

public class RecipeScaler {

    public static List<IngredientInRecipe> scaleIngredients(
            Recipes recipe, double scaleFactor) {

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
