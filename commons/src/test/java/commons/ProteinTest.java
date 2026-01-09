package commons;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ProteinTest {

    private final Protein proteinCalc = new Protein();

    @Test
    void calculate_withGrams() {
        Ingredients chicken = new Ingredients("Chicken", 31,
                3.6, 0.0, 165.0);
        IngredientInRecipe iir = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());
        iir.setIngredient(chicken);
        iir.setQuantity(200);
        iir.setUnit(Unit.GRAM);

        double result = proteinCalc.calculate(iir);
        assertEquals(330.0, result, 0.01);
    }

    @Test
    void calculate_withKilograms() {
        Ingredients beef = new Ingredients("Beef", 26,
                0.0, 0.0, 250.0);
        IngredientInRecipe iir = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());
        iir.setIngredient(beef);
        iir.setQuantity(1);
        iir.setUnit(Unit.KILOGRAM);

        double result = proteinCalc.calculate(iir);

        assertEquals(2500.0, result, 0.01);
    }


    @Test
    void calculate_withPiece() {
        Ingredients egg = new Ingredients("Egg", 13,
                1.1, 0.0, 155.0);
        IngredientInRecipe iir = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());
        iir.setIngredient(egg);
        iir.setQuantity(3);
        iir.setUnit(Unit.PIECE);

        double result = proteinCalc.calculate(iir);
        assertEquals(232.5, result, 0.01);
    }

    @Test
    void calculateTotal_multipleIngredients() {
        Ingredients fish = new Ingredients("Fish", 22,
                2.0, 0.0, 206.0);
        Ingredients rice = new Ingredients("Rice", 7,
                0.5, 28.0, 130.0);

        IngredientInRecipe fishInRecipe = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());
        fishInRecipe.setIngredient(fish);
        fishInRecipe.setQuantity(300);
        fishInRecipe.setUnit(Unit.GRAM);

        IngredientInRecipe riceInRecipe = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());
        riceInRecipe.setIngredient(rice);
        riceInRecipe.setQuantity(200);
        riceInRecipe.setUnit(Unit.GRAM);

        Recipes recipe = new Recipes();
        recipe.addIngredient(fishInRecipe);
        recipe.addIngredient(riceInRecipe);

        double totalProtein = proteinCalc.calculateTotal(recipe);

        assertEquals(878.0, totalProtein, 0.01);
    }
}