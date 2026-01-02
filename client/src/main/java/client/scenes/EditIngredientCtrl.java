package client.scenes;

import client.commonsClient.IngredientInShoppingList;
import client.commonsClient.ShoppingList;
import client.utils.ServerUtils;
import commons.IngredientInRecipe;
import commons.Ingredients;
import commons.Recipes;
import commons.Unit;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditIngredientCtrl implements Initializable {

    private MainCtrl mainCtrl;
    private ServerUtils server;
    private IngredientInRecipe ingredientInRecipe;
    private IngredientInShoppingList ingredientInShoppingList;
    private ShoppingList shoppingList;
    private Recipes recipe;
    private Scene previousScene;


    @FXML
    private TextField nameField;

    @FXML
    private TextField quantityField;

    @FXML
    private ChoiceBox<Unit> unitBox;

    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        unitBox.setItems(FXCollections.observableArrayList(Unit.values()));
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }

    /**
     * A constructor for the edit
     * ingredient ctrl
     *
     * @param mainCtrl the main controller
     * @param server the main server
     */
    @Inject
    public EditIngredientCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.recipe=null;
    }


    /**
     * Cancel just clears the fields and
     * shows back the original field
     */
    public void cancel() {
        nameField.clear();
        quantityField.clear();
        if (previousScene.getRoot().getId().equals("shoppingList")) {
            mainCtrl.showShoppingList();
        }
        if (previousScene.getRoot().getId().equals("overview")) {
            mainCtrl.showOverview();
        }
    }

    /**
     * Based on the previous scene this method
     * decides where to add the edited ingredient
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
    /**
     * adds the changed ingredient to the recipe that was selected
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
            IngredientInRecipe ingredientInRecipe = new IngredientInRecipe();
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
     * Used by mainCtrl to "tell" EditIngredientCtrl
     * which recipe the ingredient is being added to.
     *
     * @param recipe the recipe to add to.
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
     * Adds the edited ingredient
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
     * Adds the edited ingredient
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
            ingredientInShoppingList.getIngredient().setName(name);
            ingredientInShoppingList.setQuantity(quantityInt);
            ingredientInShoppingList.setUnit(unit);
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
     * Sets the current ingredient to edit
     * @param ingredientInShoppingList the ingredient.
     */
    public void setIngredient(IngredientInShoppingList ingredientInShoppingList){
        this.ingredientInShoppingList=ingredientInShoppingList;
        nameField.setText(ingredientInShoppingList.getIngredient().getName());
        String s= ""+ ingredientInRecipe.getQuantity();
        quantityField.setText(s);
        unitBox.setValue(ingredientInShoppingList.getUnit());
    }






}
