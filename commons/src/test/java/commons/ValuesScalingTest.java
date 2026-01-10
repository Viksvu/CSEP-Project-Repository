package commons;

import commons.util.ValuesScaling;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValuesScalingTest {

    @Test
    void testScaleFactor() {
        Ingredients flour = new Ingredients("Flour");
        IngredientInRecipe ingredient = new IngredientInRecipe(flour);
        ingredient.setQuantity(500);
        ingredient.setUnit(Unit.GRAM);

        String scaled = ValuesScaling.getScaledAmount(ingredient, 2.0);
        assertEquals("1 kilogram", scaled);
    }

    @Test
    void testNonScalingUnit() {
        Ingredients eggs = new Ingredients("Eggs");
        IngredientInRecipe ingredient = new IngredientInRecipe(eggs);
        ingredient.setQuantity(3);
        ingredient.setUnit(Unit.PIECE);

        String scaled = ValuesScaling.getScaledAmount(ingredient, 1.0);
        assertEquals("3 piece", scaled);
    }

    @Test
    void testNonScalableUnitPiece() {
        Ingredients eggs = new Ingredients("Eggs");
        IngredientInRecipe ingredient = new IngredientInRecipe(eggs);
        ingredient.setQuantity(3);
        ingredient.setUnit(Unit.PIECE);

        String scaled = ValuesScaling.getScaledAmount(ingredient, 2.0);
        assertEquals("6 piece", scaled);
    }

    @Test
    void testScaleRecipeIngredients() {
        Ingredients flour = new Ingredients("Flour");
        Ingredients sugar = new Ingredients("Sugar");

        IngredientInRecipe i1 = new IngredientInRecipe(flour);
        i1.setQuantity(500);
        i1.setUnit(Unit.GRAM);

        IngredientInRecipe i2 = new IngredientInRecipe(sugar);
        i2.setQuantity(200);
        i2.setUnit(Unit.GRAM);

        Recipes recipe = new Recipes("Cake");
        recipe.setIngredients(List.of(i1, i2));

        double scaleFactor = 2.0;
        List<IngredientInRecipe> scaledIngredients = ValuesScaling.scaleRecipeIngredients(recipe, scaleFactor);

        assertEquals(500, i1.getQuantity());
        assertEquals(200, i2.getQuantity());

        assertEquals(1000, scaledIngredients.get(0).getQuantity());
        assertEquals(400, scaledIngredients.get(1).getQuantity());

        assertNotSame(i1, scaledIngredients.get(0));
        assertNotSame(i2, scaledIngredients.get(1));

        assertEquals(Unit.GRAM, scaledIngredients.get(0).getUnit());
        assertEquals(Unit.GRAM, scaledIngredients.get(1).getUnit());
    }

    @Test
    void testScaleRecipeWithDifferentUnits() {
        Ingredients milk = new Ingredients("Milk");
        Ingredients butter = new Ingredients("Butter");
        Ingredients egg = new Ingredients("Egg");

        IngredientInRecipe i1 = new IngredientInRecipe(milk);
        i1.setQuantity(1200);
        i1.setUnit(Unit.MILLILITER);

        IngredientInRecipe i2 = new IngredientInRecipe(butter);
        i2.setQuantity(800);
        i2.setUnit(Unit.GRAM);

        IngredientInRecipe i3 = new IngredientInRecipe(egg);
        i3.setQuantity(3);
        i3.setUnit(Unit.PIECE);

        Recipes recipe = new Recipes("Breakfast");
        recipe.setIngredients(List.of(i1, i2, i3));

        double scaleFactor = 1.0;
        List<IngredientInRecipe> scaledIngredients = ValuesScaling.scaleRecipeIngredients(recipe, scaleFactor);

        String butterScaled = ValuesScaling.getScaledAmount(scaledIngredients.get(1), 1.0);
        String eggScaled = ValuesScaling.getScaledAmount(scaledIngredients.get(2), 1.0);

        assertEquals("800 gram", butterScaled);
        assertEquals("3 piece", eggScaled);

        assertEquals(1200, scaledIngredients.get(0).getQuantity());
        assertEquals(800, scaledIngredients.get(1).getQuantity());
        assertEquals(3, scaledIngredients.get(2).getQuantity());
    }
}
