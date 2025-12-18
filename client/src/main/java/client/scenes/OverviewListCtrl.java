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
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class OverviewListCtrl implements Initializable {

    private MainCtrl mainCtrl;
    private ServerUtils server;
    private ShoppingList shoppingList;
    private ObservableList<IngredientInShoppingList> items
            = FXCollections.observableArrayList();
    @FXML
    private ListView<IngredientInShoppingList> overviewListView;
    
    @FXML
    private AnchorPane overviewListPane;

    @Inject
    public OverviewListCtrl(MainCtrl mainCtrl, ServerUtils server){
        this.mainCtrl=mainCtrl;
        this.server=server;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        overviewListView.setItems(items);
    }

    public void refresh(){
        items.setAll(shoppingList.getShoppingList());
        addEditButtonToIngredient();
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }


    /**
     * Adds an edit button next to the name of the ingredient
     */
    public void addEditButtonToIngredient() {
        overviewListPane.getChildren().clear();
        overviewListPane.getChildren().addAll(overviewListPane);
        if (!items.isEmpty()) {
            int numIngredients = items.size();
            for (int i = 0; i < numIngredients; i++) {
                EditButtonShoppingList editButton =
                        new EditButtonShoppingList(
                                items.get(i),
                                "delete",
                                i,
                                overviewListView,
                                this, shoppingList,
                                EditButtonOptions.REMOVE_INGREDIENT
                        );


                overviewListPane.getChildren().add(editButton);
            }
        }
    }
}
