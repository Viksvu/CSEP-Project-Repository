package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FatTest {

    private final Fat fatCalc = new Fat();

    @Test
    void calculate() {
        Ingredients avocado = new Ingredients("Avocado", 15,
                2.0, 9.0, 160.0);
        IngredientInRecipe iir = new IngredientInRecipe();
        iir.setIngredient(avocado);
        iir.setQuantity(150);
        iir.setUnit(Unit.GRAM);

        double result = fatCalc.calculate(iir);
        assertEquals(3.0, result, 0.01);
    }

    @Test
    void calculateTotal() {
        Ingredients nuts = new Ingredients("Nuts", 50,
                15.0, 20.0, 607.0);
        Ingredients oil = new Ingredients("Oil", 0,
                0.0, 0.0, 884.0);

        IngredientInRecipe nutsInRecipe = new IngredientInRecipe();
        nutsInRecipe.setIngredient(nuts);
        nutsInRecipe.setQuantity(100);
        nutsInRecipe.setUnit(Unit.GRAM);

        IngredientInRecipe oilInRecipe = new IngredientInRecipe();
        oilInRecipe.setIngredient(oil);
        oilInRecipe.setQuantity(30);
        oilInRecipe.setUnit(Unit.GRAM);

        Recipes recipe = new Recipes();
        recipe.getIngredients().add(nutsInRecipe);
        recipe.getIngredients().add(oilInRecipe);

        double totalFat = fatCalc.calculateTotal(recipe);
        assertEquals(15.0, totalFat, 0.01);
    }
}