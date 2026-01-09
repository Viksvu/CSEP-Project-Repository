package commons.util;

import commons.IngredientInRecipe;
import commons.Ingredients;
import commons.Recipes;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class NutritionTotalTest {

    @Test
    void testCalculateTotals(){
        Ingredients sugar = new Ingredients();
        sugar.setName("Sugar");
        sugar.setKcalPer100g(4);
        sugar.setCarbsPer100g(1);
        sugar.setFatPer100g(0);
        sugar.setProteinPer100g(0);

        Ingredients butter = new Ingredients();
        butter.setName("Butter");
        butter.setKcalPer100g(7);
        butter.setCarbsPer100g(0);
        butter.setFatPer100g(1);
        butter.setProteinPer100g(0);

        IngredientInRecipe i1 = new IngredientInRecipe();
        i1.setIngredient(sugar);
        i1.setQuantity(100);

        IngredientInRecipe i2 = new IngredientInRecipe();
        i2.setIngredient(butter);
        i2.setQuantity(50);

        Recipes recipes = new Recipes();
        recipes.setIngredients(Arrays.asList(i1, i2));

        NutritionTotal nutritionTotal = new NutritionTotal();

        assertEquals(100 * 4 + 50 * 7, nutritionTotal.calculateTotalKcal(recipes));
        assertEquals(100 * 1 + 50 * 0, nutritionTotal.calculateTotalCarbs(recipes));
        assertEquals(100 * 0 + 50 * 1, nutritionTotal.calculateTotalFat(recipes));
        assertEquals(100 * 0 + 50 * 0, nutritionTotal.calculateTotalProtein(recipes));
    }

}