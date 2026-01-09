package commons;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PrinterTest {

    @Test
    void print() {
        try {
            Recipes myRecipes = new Recipes("Pizza");
            IngredientInRecipe ingredientInRecipe =
                    new IngredientInRecipe(new Ingredients("Cheese"));
            ingredientInRecipe.setRecipes(myRecipes);
            ingredientInRecipe.setUnit(Unit.KILOGRAM);
            ingredientInRecipe.setQuantity(10);
            myRecipes.addIngredient(ingredientInRecipe);
            myRecipes.addPreparationStep(new PreparationStep("Melt the cheese"));
            Printer.print(myRecipes);
            Scanner sc = new Scanner(new File("test.md"));
            String text = "";
            while (sc.hasNextLine()) {
                text += sc.nextLine()+"\n";
            }
            assertEquals(
                    "# Pizza\n" +
                            "- Cheese (10 kilograms)\n" +
                    "Preparation Steps:\n" +
                    "1. Melt the cheese", text.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
