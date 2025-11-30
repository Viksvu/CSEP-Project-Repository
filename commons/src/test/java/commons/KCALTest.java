package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KCALTest {
    private KCAL calc;  //calc is short for calculator, 67, mango, brr brr patapim, does who know

    @BeforeEach
    void setUp()
    {calc = new KCAL();}

    @Test
    void calculator_Gram(){
        Ingredients sugar = new Ingredients("Sugar", 387, "White sugar", Unit.GRAM);
        IngredientInRecipe ingredientUse = new IngredientInRecipe();
        ingredientUse.setTempIngredient(sugar);
        ingredientUse.setQuantity(200);
        ingredientUse.setUnit(Unit.GRAM);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(774, kcal);
    }

    @Test
    void calculator_Cup(){
        Ingredients flour = new Ingredients("Flour", 364, "Wheat flour", Unit.GRAM);
        IngredientInRecipe ingredientUse = new IngredientInRecipe();
        ingredientUse.setTempIngredient(flour);
        ingredientUse.setQuantity(2);
        ingredientUse.setUnit(Unit.CUP);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(1747, kcal);
    }

    @Test
    void calculator_Teaspoon(){
        Ingredients salt = new Ingredients("Salt", 0, "Table sugar", Unit.GRAM);
        IngredientInRecipe ingredientUse = new IngredientInRecipe();
        ingredientUse.setTempIngredient(salt);
        ingredientUse.setQuantity(3);
        ingredientUse.setUnit(Unit.GRAM);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(0, kcal);
    }

    @Test
    void calculator_Milliliter(){
        Ingredients water = new Ingredients("Water", 0, "Filtered water", Unit.MILLILITER);
        IngredientInRecipe ingredientUse = new IngredientInRecipe();
        ingredientUse.setTempIngredient(water);
        ingredientUse.setQuantity(500);
        ingredientUse.setUnit(Unit.MILLILITER);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(0, kcal);
    }

    void calculator_Milliliter2() {
        Ingredients oil = new Ingredients("Oil", 880, "Olive oil", Unit.MILLILITER);
        IngredientInRecipe ingredientUse = new IngredientInRecipe();
        ingredientUse.setTempIngredient(oil);
        ingredientUse.setQuantity(100);
        ingredientUse.setUnit(Unit.MILLILITER);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(880, kcal);
    }
        @Test
        void calculatorTotal () {
            Ingredients sugar = new Ingredients("Sugar", 387, "White sugar", Unit.GRAM);
            Ingredients butter = new Ingredients("Butter", 717, "Unsalted butter", Unit.GRAM);

            IngredientInRecipe sugerUse = new IngredientInRecipe();
            sugerUse.setTempIngredient(sugar);
            sugerUse.setQuantity(100);
            sugerUse.setUnit(Unit.GRAM);

            IngredientInRecipe butterUse = new IngredientInRecipe();
            sugerUse.setTempIngredient(butter);
            sugerUse.setQuantity(50);
            sugerUse.setUnit(Unit.GRAM);

            Recipes recipe = new Recipes();
            recipe.setIngredients(List.of(sugerUse, butterUse));

            int total = calc.calculateTotal(recipe);
            assertEquals(746, total);
        }

        @Test
        void convertUnknown () {
            Ingredients ing = new Ingredients("Test", 100, "test", null);

            IngredientInRecipe ingredientUse = new IngredientInRecipe();
            ingredientUse.setTempIngredient(ing);
            ingredientUse.setQuantity(100);
            ingredientUse.setUnit(null);

            assertThrows(IllegalArgumentException.class, () -> calc.calculator(ingredientUse));
        }
    }