package client.scenes;

import client.EditButtonOptions;
import client.EditButtonShoppingList;
import client.commonsClient.IngredientInShoppingList;
import client.commonsClient.ShoppingList;
import client.utils.ServerUtils;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ShoppingListCtrl implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private ShoppingList shoppingList;
    private ObservableList<IngredientInShoppingList> items
            = FXCollections.observableArrayList();

    @FXML
    private ListView<IngredientInShoppingList> shoppingListView;
    @FXML
    private Label totalItemsLabel;



    @FXML
    private AnchorPane ingredientsPane;

    /**
     * Constructor
     *
     * @param mainCtrl the controllers of all the panes store
     *                 the main controller as an object
     * @param server   communication to server.
     */
    @Inject
    public ShoppingListCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shoppingListView.setItems(items);
    }

    /**
     * Clicking cancel should clear whatever the user entered in
     * the text field and then show the overview
     */
    public void goBack() {
        mainCtrl.showOverview();
    }

    /**
     * refreshes the list view
     */
    public void refresh() {
        items.setAll(shoppingList.getShoppingList());
        addEditButtonToIngredient();
    }

    /**
     * Edit the selected ingredient
     *
     * @param ingredient the selected ingredient
     */
    public void editIngredient(IngredientInShoppingList ingredient) {
        mainCtrl.showEditIngredient(ingredient);
    }

    /**
     * adds an ingredient from input.
     */
    public void addIngredient() {
        mainCtrl.showAddIngredient();
        totalItemsLabel.setText(
                "Total: " + shoppingList.getShoppingList().size() + " items");
    }

    /**
     * Adds selected recipe ingredients.
     */
    public void addRecipe(){
       mainCtrl.showAddRecipeIngredientsOverview();
    }
        /**
     * Clears the full list
     */
    public void clearList() {
        shoppingList.resetShoppingList();
        shoppingListView.getItems().clear();
        ingredientsPane.getChildren().clear();

    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }


    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    /**
     * Adds an edit button next to the name of the ingredient
     */
    public void addEditButtonToIngredient() {
        ingredientsPane.getChildren().clear();
        ingredientsPane.getChildren().addAll(shoppingListView);
        if (!items.isEmpty()) {
            int numIngredients = items.size();
            for (int i = 0; i < numIngredients; i++) {
                EditButtonShoppingList deleteButton =
                        new EditButtonShoppingList(
                                items.get(i),
                                "delete",
                                i,
                                shoppingListView,
                                this, shoppingList,
                                EditButtonOptions.REMOVE_INGREDIENT
                        );
                EditButtonShoppingList editButton =
                        new EditButtonShoppingList(
                                items.get(i),
                                "edit",
                                i,
                                shoppingListView,
                                this, shoppingList,
                                EditButtonOptions.EDIT_INGREDIENT
                        );
                HBox buttonBox = new HBox(8); // 8 px space
                buttonBox.getChildren().addAll(deleteButton, editButton);
                ingredientsPane.getChildren().add(buttonBox);
            }
        }
    }
}
