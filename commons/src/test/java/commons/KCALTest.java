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
        Ingredients sugar = new Ingredients("Sugar", 387);
        IngredientInRecipe ingredientUse = new IngredientInRecipe();
        ingredientUse.setTempIngredient(sugar);
        ingredientUse.setQuantity(200);
        ingredientUse.setUnit(Unit.GRAM);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(774, kcal);
    }

    @Test
    void calculator_Gram2() {
        Ingredients tomato = new Ingredients("Tomato", 19);
        IngredientInRecipe ingredientUse = new IngredientInRecipe();
        ingredientUse.setTempIngredient(tomato);
        ingredientUse.setQuantity(300);
        ingredientUse.setUnit(Unit.GRAM);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(57, kcal);
    }

    @Test
    void calculator_Gram3() {
        Ingredients cheese = new Ingredients("Cheese", 400);
        IngredientInRecipe ingredientUse = new IngredientInRecipe();
        ingredientUse.setTempIngredient(cheese);
        ingredientUse.setQuantity(500);
        ingredientUse.setUnit(Unit.GRAM);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(2000, kcal);
    }

    @Test
    void calculator_Gram4() {
        Ingredients pasta = new Ingredients("Pasta", 141);
        IngredientInRecipe ingredientUse = new IngredientInRecipe();
        ingredientUse.setTempIngredient(pasta);
        ingredientUse.setQuantity(125);
        ingredientUse.setUnit(Unit.GRAM);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(176, kcal);
    }

    @Test
    void calculator_Cup() {
        Ingredients flour = new Ingredients("Flour", 364);
        IngredientInRecipe ingredientUse = new IngredientInRecipe();
        ingredientUse.setUnit(Unit.CUP);
        ingredientUse.setTempIngredient(flour);
        ingredientUse.setQuantity(2);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(1747, kcal);
    }

    @Test
    void calculator_Teaspoon() {
        Ingredients salt = new Ingredients("Salt", 0);
        IngredientInRecipe ingredientUse = new IngredientInRecipe();
        ingredientUse.setTempIngredient(salt);
        ingredientUse.setQuantity(3);
        ingredientUse.setUnit(Unit.TEASPOON);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(0, kcal);
    }

    @Test
    void calculator_Milliliter() {
        Ingredients water = new Ingredients("Water", 0);
        IngredientInRecipe ingredientUse = new IngredientInRecipe();
        ingredientUse.setTempIngredient(water);
        ingredientUse.setQuantity(500);
        ingredientUse.setUnit(Unit.MILLILITER);


        int kcal = calc.calculator(ingredientUse);
        assertEquals(0, kcal);
    }

    void calculator_Milliliter2() {
        Ingredients oil = new Ingredients("Oil", 880);
        IngredientInRecipe ingredientUse = new IngredientInRecipe();
        ingredientUse.setTempIngredient(oil);
        ingredientUse.setQuantity(100);

        int kcal = calc.calculator(ingredientUse);
        assertEquals(880, kcal);
    }
//        @Test
//        void calculatorTotal () {
//            Ingredients sugar = new Ingredients("Sugar", 387, "White sugar", Unit.GRAM);
//            Ingredients butter = new Ingredients("Butter", 717, "Unsalted butter", Unit.GRAM);
//
//            IngredientInRecipe sugerUse = new IngredientInRecipe();
//            sugerUse.setTempIngredient(sugar);
//            sugerUse.setQuantity(100);
//            sugerUse.setUnit(Unit.GRAM);
//
//            IngredientInRecipe butterUse = new IngredientInRecipe();
//            sugerUse.setTempIngredient(butter);
//            sugerUse.setQuantity(50);
//            sugerUse.setUnit(Unit.GRAM);
//
//            Recipes recipe = new Recipes();
//            recipe.setIngredients(List.of(sugerUse, butterUse));
//
//            int total = calc.calculateTotal(recipe);
//            assertEquals(746, total);
//        }

//        @Test
//        void convertUnknown () {
//            Ingredients ing = new Ingredients("Test", 100, "test", null);
//
//            IngredientInRecipe ingredientUse = new IngredientInRecipe();
//            ingredientUse.setTempIngredient(ing);
//            ingredientUse.setQuantity(100);
//            ingredientUse.setUnit(null);
//
//            assertThrows(IllegalArgumentException.class, () -> calc.calculator(ingredientUse));
//        }
//    }
}
