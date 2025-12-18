package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarbsTest {

    private final Carbs carbsCalc = new Carbs();

    @Test
    void calculate_withGrams() {
        Ingredients pasta = new Ingredients("Pasta", 25,
                5.0, 75.0, 141.0);
        IngredientInRecipe iir = new IngredientInRecipe();
        iir.setIngredient(pasta);
        iir.setQuantity(200);
        iir.setUnit(Unit.GRAM);

        double result = carbsCalc.calculate(iir);
        assertEquals(150.0, result, 0.01);
    }

    @Test
    void calculateTotal() {
        Ingredients bread = new Ingredients("Bread", 49,
                9.0, 49.0, 265.0);
        Ingredients butter = new Ingredients("Butter", 0,
                0.1, 0.0, 717.0);

        IngredientInRecipe breadInRecipe = new IngredientInRecipe();
        breadInRecipe.setIngredient(bread);
        breadInRecipe.setQuantity(100);
        breadInRecipe.setUnit(Unit.GRAM);

        IngredientInRecipe butterInRecipe = new IngredientInRecipe();
        butterInRecipe.setIngredient(butter);
        butterInRecipe.setQuantity(50);
        butterInRecipe.setUnit(Unit.GRAM);

        Recipes recipe = new Recipes();
        recipe.getIngredients().add(breadInRecipe);
        recipe.getIngredients().add(butterInRecipe);

        double totalCarbs = carbsCalc.calculateTotal(recipe);
        assertEquals(49.0, totalCarbs, 0.01);
    }
}