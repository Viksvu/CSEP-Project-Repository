package client.scenes;

import client.commonsClient.IngredientInShoppingList;
import client.commonsClient.ShoppingList;
import client.utils.ServerUtils;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ShoppingListCtrl implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private ShoppingList shoppingList;
    @FXML
    private ListView<IngredientInShoppingList> shoppingListView;

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
        shoppingListView.setItems(FXCollections.observableArrayList());
        shoppingList = new ShoppingList();
    }

    /**
     * Clicking cancel should clear whatever the user entered in
     * the text field and then show the overview
     */
    public void goBack() {
        mainCtrl.showOverview();
    }

    /**
     * adds an ingredient from input.
     */
    public void addIngredient() {
        mainCtrl.showAddIngredient(shoppingList);
    }
    /**
     * Removes selected ingredient
     */
    public void removeSelected() {

    }

    /**
     * Clears the full list
     */
    public void clearList() {

    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }


    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

}
