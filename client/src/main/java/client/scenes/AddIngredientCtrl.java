package client.scenes;

import client.commonsClient.IngredientInShoppingList;
import client.commonsClient.ShoppingList;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.IngredientInRecipe;
import commons.Ingredients;
import commons.Recipes;
import commons.Unit;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddIngredientCtrl implements Initializable {


    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private Recipes recipe;
    private ShoppingList shoppingList;
    private Scene previousScene;

    @FXML
    private TextField nameField;

    @FXML
    private TextField quantityField;

    @FXML
    private ChoiceBox<Unit> unitBox;

    @FXML
    private Label errorLabel;

    /**
     * Constructor
     *
     * @param mainCtrl
     * @param server
     */
    @Inject
    public AddIngredientCtrl(MainCtrl mainCtrl, ServerUtils server) {
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
        unitBox.setItems(FXCollections.observableArrayList(Unit.values()));
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }

    /**
     * Cancel just clears the fields and
     * shows back the overview field
     */
    public void cancel() {
        nameField.clear();
        quantityField.clear();
        if (previousScene.getRoot().getId().equals("shoppingList")) {
            mainCtrl.showShoppingList();
        }
        else if (previousScene.getRoot().getId().equals("overview")) {
            mainCtrl.showOverview();
        }
        else if(previousScene.getRoot().getId().equals("overviewList")){
            mainCtrl.showOverviewList();
        }
    }

    /**
     * Based on the previous scene this method
     * decides where to add the parsed ingredient
     */
    public void add() {
        if (previousScene.getRoot().getId().equals("shoppingList")) {
            addToShoppingList();
        }
        else if (previousScene.getRoot().getId().equals("overview")) {
            addToRecipe();
        }
        else if (previousScene.getRoot().getId().equals("overviewList")) {
            addToOverview();
        }

    }
    //MUST MODIFY: UNIT.TOSTRING() BEING USED SINCE
    // INGREDIENTS IS NOT REFACTORED YET!

    /**
     * Adds an ingredient to the recipe that was selected
     */
    public void addToRecipe() {
        String name = nameField.getText();
        String quantity = null;
        int quantityInt;
        try {
            quantity = quantityField.getText();
            quantityInt = Integer.parseInt(quantity);
            Unit unit = unitBox.getSelectionModel().getSelectedItem();
            Ingredients ingredient = new Ingredients(name, 0, 0.0, 0.0, 0.0);
            server.addIngredientToDatabase(ingredient);
            IngredientInRecipe ingredientInRecipe = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());
            ingredientInRecipe.setIngredient(ingredient);
            ingredientInRecipe.setRecipes(recipe);
            ingredientInRecipe.setUnit(unit);
            ingredientInRecipe.setQuantity(quantityInt);
            server.addIngredientToRecipe(ingredientInRecipe, recipe);
            mainCtrl.showOverview();
        } catch (Exception e) {
            //errorLabel.setText("Quantity must be a valid number");
            errorLabel.setText(e.getMessage());
            System.out.println(e.getMessage());
            errorLabel.setVisible(true);
        }

    }

    /**
     * Used by mainCtrl to "tell" AddIngredientCtrl
     * which recipe the ingredient is being added to.
     *
     * @param recipe
     */
    public void provideRecipe(Recipes recipe) {
        this.recipe = recipe;
    }

    /**
     * Used by mainCtrl to "tell" AddIngredientCtrl
     * which shopping list the ingredient is being added to.
     *
     * @param shoppingList the shopping list.
     */
    public void provideShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }


    /**
     * Add an ingredient
     * directly to the shopping list.
     */
    public void addToShoppingList() {
        String name = nameField.getText();
        String quantity = null;
        int quantityInt;
        try {
            quantity = quantityField.getText();
            quantityInt = Integer.parseInt(quantity);
            Unit unit = unitBox.
                    getSelectionModel().getSelectedItem();
            Ingredients ingredient = new Ingredients(name, 0,
                    0.0, 0.0, 0.0);
            IngredientInShoppingList ingredientInShoppingList
                    = new IngredientInShoppingList(
                    ingredient, quantityInt, unit);
            shoppingList.addIngredientDirectly(ingredientInShoppingList);
            mainCtrl.showShoppingList();
        } catch (Exception e) {
            //errorLabel.setText("Quantity must be a valid number");
            errorLabel.setText(e.getMessage());
            System.out.println(e.getMessage());
            errorLabel.setVisible(true);
        }
    }


    /**
     * Add an ingredient
     * directly to the overview list.
     */
    public void addToOverview() {
        String name = nameField.getText();
        String quantity = null;
        int quantityInt;
        try {
            quantity = quantityField.getText();
            quantityInt = Integer.parseInt(quantity);
            Unit unit = unitBox.
                    getSelectionModel().getSelectedItem();
            Ingredients ingredient = new Ingredients(name, 0, 0.0, 0.0, 0.0);
            IngredientInShoppingList ingredientInShoppingList
                    = new IngredientInShoppingList(
                    ingredient, quantityInt, unit);
            shoppingList.
                    addIngredientDirectlyToOverview(ingredientInShoppingList);
            mainCtrl.showOverviewList();
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

}
