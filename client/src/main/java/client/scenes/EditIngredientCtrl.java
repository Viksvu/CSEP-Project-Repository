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
import javafx.scene.control.Button;
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

    @FXML
    private Label editIngredientLabel;

    @FXML
    private Label editIngredientQuantity;

    @FXML
    private Label editIngredientUnit;

    @FXML
    private Button editIngredientCancelButton;

    @FXML
    private Button editIngredientChangeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        unitBox.setItems(FXCollections.observableArrayList(Unit.values()));
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }

    /**
     * Updates the UI elements to the new selected language.
     * @param resourceBundle the resource bundle corresponding to the new language.
     */
    public void updateLanguage(ResourceBundle resourceBundle) {
        editIngredientLabel.setText(resourceBundle.getString("editIngredient.label"));
        editIngredientQuantity.setText(resourceBundle.getString("editIngredient.quantity"));
        editIngredientUnit.setText(resourceBundle.getString("editIngredient.unit"));
        editIngredientCancelButton.setText(resourceBundle.getString("editIngredient.cancel"));
        editIngredientChangeButton.setText(resourceBundle.getString("editIngredient.change"));
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
        if (previousScene.getRoot().getId().equals("overviewList")) {
            mainCtrl.showOverviewList();
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
            ingredientInRecipe.setIngredient(ingredient);
            ingredientInRecipe.setRecipes(recipe);
            ingredientInRecipe.setUnit(unit);
            ingredientInRecipe.setQuantity(quantityInt);
            server.editIngredientInRecipe(ingredientInRecipe, recipe);
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
            ingredientInShoppingList.getIngredient().setName(name);
            ingredientInShoppingList.setQuantity(quantityInt);
            ingredientInShoppingList.setUnit(unit);
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


    /**
     * Sets the current ingredient to edit
     * for shopping list ingredients
     * @param ingredientInShoppingList the ingredient.
     */
    public void setIngredient(IngredientInShoppingList ingredientInShoppingList){
        this.ingredientInShoppingList=ingredientInShoppingList;
        nameField.setText(ingredientInShoppingList.getIngredient().getName());
        String s= ""+ ingredientInShoppingList.getQuantity();
        quantityField.setText(s);
        unitBox.setValue(ingredientInShoppingList.getUnit());
        errorLabel.setVisible(false);

    }

    /**
     * Sets the current ingredient to edit for recipe
     * ingredients
     * @param ingredientInRecipe the ingredient.
     */
    public void setIngredient(IngredientInRecipe ingredientInRecipe){
        this.ingredientInRecipe=ingredientInRecipe;
        nameField.setText(ingredientInRecipe.getIngredient().getName());
        String s= ""+ ingredientInRecipe.getQuantity();
        quantityField.setText(s);
        unitBox.setValue(ingredientInRecipe.getUnit());
        errorLabel.setVisible(false);

    }






}
