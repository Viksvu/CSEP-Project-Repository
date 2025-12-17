package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Ingredients;
import commons.Recipes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPreparationStepCtrl implements Initializable {


    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private Recipes recipe;
    private Scene previousScene;

    @FXML
    private TextField nameField;

    @FXML
    private Label errorLabel;

    /**
     * Constructor
     *
     * @param mainCtrl
     * @param server
     */
    @Inject
    public AddPreparationStepCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.recipe = null;
    }

    /**
     * initialises all fields
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }

    /**
     * Cancel just clears the fields and
     * shows back the overview field
     */
    public void cancel() {
        nameField.clear();
        mainCtrl.showOverview();
    }

    /**
     * Based on the previous scene this method
     * decides where to add the parsed ingredient
     */
    public void add() {
        addToRecipe();
    }

    /**
     * Adds an ingredient to the recipe that was selected
     */
    public void addToRecipe() {
        String name = nameField.getText();
        String quantity = null;
        int quantityInt;
        try {
            Ingredients ingredient = new Ingredients(name, 0);
            server.addIngredientToDatabase(ingredient);

            // TODO

//            server.addIngredientToRecipe(ingredientInRecipe, recipe);
            mainCtrl.showOverview();
        } catch (Exception e) {
            //errorLabel.setText("Quantity must be a valid number");
            errorLabel.setText(e.getMessage());
            System.out.println(e.getMessage());
            errorLabel.setVisible(true);
        }

    }

    /**
     * sets the previous scene
     *
     * @param previousScene the previous scene.
     */
    public void previousSceneSetter(Scene previousScene) {
        this.previousScene = previousScene;
    }

    /**
     * Provide the recipe to add the preparation step to
     * @param recipe recipe to add preparation step to
     */
    public void provideRecipe(Recipes recipe) {
        this.recipe = recipe;
    }
}
