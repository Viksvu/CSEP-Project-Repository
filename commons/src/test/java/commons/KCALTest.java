package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class KCALTest {
    private KCAL calc;  //calc is short for calculator, 67, mango, does who know

    @BeforeEach
    void setUp() {
        calc = new KCAL();
    }

    @Test
    void calculator_Gram() {
        Ingredients sugar = new Ingredients("Sugar", 387, 0.0, 100.0, 387.0);
        IngredientInRecipe ingredientUse = new IngredientInRecipe(sugar);
        ingredientUse.setQuantity(200);
        ingredientUse.setUnit(Unit.GRAM);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(774, kcal);
    }

    @Test
    void calculator_Gram2() {
        Ingredients tomato = new Ingredients("Tomato", 19, 0.9, 3.9, 19.0);
        IngredientInRecipe ingredientUse = new IngredientInRecipe(tomato);
        ingredientUse.setQuantity(300);
        ingredientUse.setUnit(Unit.GRAM);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(57, kcal);
    }

    @Test
    void calculator_Gram3() {
        Ingredients cheese = new Ingredients("Cheese", 400, 25.0, 1.3, 400.0);
        IngredientInRecipe ingredientUse = new IngredientInRecipe(cheese);
        ingredientUse.setQuantity(500);
        ingredientUse.setUnit(Unit.GRAM);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(2000, kcal);
    }

    @Test
    void calculator_Gram4() {
        Ingredients pasta = new Ingredients("Pasta", 141, 5.0, 75.0, 141.0);
        IngredientInRecipe ingredientUse = new IngredientInRecipe(pasta);
        ingredientUse.setQuantity(125);
        ingredientUse.setUnit(Unit.GRAM);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(176, kcal);
    }

    @Test
    void calculator_Cup() {
        Ingredients flour = new Ingredients("Flour", 364, 10.0, 76.0, 364.0);
        IngredientInRecipe ingredientUse = new IngredientInRecipe(flour);
        ingredientUse.setUnit(Unit.CUP);
        ingredientUse.setQuantity(2);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(1747, kcal);
    }

    @Test
    void calculator_Teaspoon() {
        Ingredients salt = new Ingredients("Salt", 0, 0.0, 0.0, 0.0);
        IngredientInRecipe ingredientUse = new IngredientInRecipe(salt);
        ingredientUse.setQuantity(3);
        ingredientUse.setUnit(Unit.TEASPOON);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(0, kcal);
    }

    @Test
    void calculator_Milliliter() {
        Ingredients water = new Ingredients("Water", 0, 0.0, 0.0, 0.0);
        IngredientInRecipe ingredientUse = new IngredientInRecipe(water);
        ingredientUse.setQuantity(500);
        ingredientUse.setUnit(Unit.MILLILITER);


        int kcal = calc.calculator(ingredientUse);
        assertEquals(0, kcal);
    }

    void calculator_Milliliter2() {
        Ingredients oil = new Ingredients("Oil", 880, 0.0, 0.0, 100.0);
        IngredientInRecipe ingredientUse = new IngredientInRecipe(oil);
        ingredientUse.setQuantity(100);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(880, kcal);
    }

}
