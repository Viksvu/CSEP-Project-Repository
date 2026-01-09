package commons;

import commons.util.ValuesScaling;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValuesScalingTest {

    @Test
    void testScaleFactor() {
        IngredientInRecipe ingredient = new IngredientInRecipe();
        ingredient.setQuantity(500);
        ingredient.setUnit(Unit.GRAM);
        ingredient.setIngredient(new Ingredients("Flour"));

        String scaled = ValuesScaling.getScaledAmount(ingredient, 2.00);
        assertEquals("1 kilogram", scaled);
    }

    @Test
    void testNonScalingUnit() {
        IngredientInRecipe ingredient = new IngredientInRecipe();
        ingredient.setQuantity(3);
        ingredient.setUnit(Unit.PIECE);
        ingredient.setIngredient(new Ingredients("Eggs"));

        String scaled = ValuesScaling.getScaledAmount(ingredient, 1.00);
        assertEquals("3 piece", scaled);
    }
    @Test
    void testNonScalableUnitPiece() {
        IngredientInRecipe ingredient = new IngredientInRecipe();
        ingredient.setQuantity(3);
        ingredient.setUnit(Unit.PIECE);
        ingredient.setIngredient(new Ingredients("Eggs"));

        String scaled = ValuesScaling.getScaledAmount(ingredient, 2.0);
        assertEquals("6 piece", scaled);
    }

}