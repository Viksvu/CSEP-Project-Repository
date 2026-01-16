package client.utils;


import commons.IngredientInRecipe;
import commons.Recipes;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.List;

public class RecipeController {

    @FXML
    private TextField scaleField;

    private Recipes currentRecipe;

    /**
     * Sets the current recipe to be displayed and manipulated.
     * @param recipe
     */
    public void setRecipe(Recipes recipe) {
        this.currentRecipe = recipe;
    }

    /**
     * This method is called when the user wants to scale the recipe.
     * It reads the scale factor from the input field, scales the ingredients,
     * and updates the recipe accordingly.
     */
    @FXML
    private void onScaleRecipe() {
        try {
            double scaleFactor = Double.parseDouble(scaleField.getText());

            List<IngredientInRecipe> scaledIngredients =
                    RecipeScaler.scaleIngredients(currentRecipe, scaleFactor);



        } catch (NumberFormatException e) {
            System.out.println("Invalid scale factor");
        }
    }
}
