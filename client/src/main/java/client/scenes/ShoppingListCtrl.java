package client.scenes;

import client.EditButton;
import client.EditButtonOptions;
import client.commonsClient.IngredientInShoppingList;
import client.commonsClient.ShoppingList;
import client.utils.ServerUtils;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ShoppingListCtrl implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private final ShoppingList shoppingList;

    private final ObservableList<IngredientInShoppingList> items =
            FXCollections.observableArrayList();

    @FXML
    private ListView<IngredientInShoppingList> shoppingListView;

    @FXML
    private Label totalItemsLabel;

    @FXML
    private Label shoppingListLabel;

    @FXML
    private Button shoppingListBackButton;

    @FXML
    private Button shoppingListAddRecipeButton;

    @FXML
    private Button shoppingListAddIngredients;

    @FXML
    private Button shoppingListClearButton;

    @FXML
    private AnchorPane ingredientsPane;

    @FXML
    private Button downloadShoppingListButton;

    /**
     * A constructor for the controller
     * @param mainCtrl
     * @param server
     * @param shoppingList
     */
    @Inject
    public ShoppingListCtrl(MainCtrl mainCtrl,
                            ServerUtils server,
                            ShoppingList shoppingList) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.shoppingList = shoppingList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shoppingListView.setItems(items);
        Label emptyLabel = new Label(
                "Your shopping list is empty\nAdd ingredients to get started"
        );
        emptyLabel.setStyle("""
                -fx-text-fill: gray;
                -fx-font-size: 14px;
                -fx-alignment: center;
                """);
        shoppingListView.setPlaceholder(emptyLabel);
        shoppingListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(IngredientInShoppingList item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("");
                    return;
                }
                EditButton<IngredientInShoppingList> deleteButton =
                        new EditButton<>(
                                item,
                                "delete",
                                getIndex(),
                                shoppingListView,
                                server,
                                ShoppingListCtrl.this,
                                EditButtonOptions.REMOVE_INGREDIENT,
                                shoppingList
                        );
                EditButton<IngredientInShoppingList> editButton =
                        new EditButton<>(
                                item,
                                "edit",
                                getIndex(),
                                shoppingListView,
                                server,
                                ShoppingListCtrl.this,
                                EditButtonOptions.EDIT_INGREDIENT,
                                shoppingList
                        );
                deleteButton.setDisable(item.isChecked());
                editButton.setDisable(item.isChecked());
                HBox row = new HBox(8, deleteButton, editButton);
                row.setPickOnBounds(false);
                setText(item.toString());
                setGraphic(row);
                setGraphicTextGap(-80);
                setTextOverrun(OverrunStyle.ELLIPSIS);
                updateStyle(item);
            }
            {
                setOnMouseClicked(event -> {
                    if (!isEmpty()) {
                        IngredientInShoppingList item = getItem();
                        item.setChecked(!item.isChecked());
                        FXCollections.sort(items, Comparator.
                                comparing(IngredientInShoppingList::isChecked)
                        );
                        updateStyle(item);
                    }
                });
            }
            private void updateStyle(IngredientInShoppingList item) {
                if (item.isChecked()) {
                    setStyle("-fx-strikethrough: true; -fx-text-fill: gray;");
                } else {
                    setStyle("-fx-strikethrough: false; -fx-text-fill: black;");
                }
            }
        });
        ImageView iv = new ImageView(
                new Image(getClass().getResourceAsStream("/pictures/save.png")));
        iv.setFitWidth(20);
        iv.setFitHeight(20);
        downloadShoppingListButton.setGraphic(iv);
    }

    /**
     * Updates the language
     * @param resourceBundle
     */
    public void updateLanguage(ResourceBundle resourceBundle) {
        shoppingListLabel.setText(resourceBundle.getString("shoppingList.label"));
        shoppingListAddIngredients.setText(resourceBundle.getString("shoppingList.addIngredient"));
        shoppingListAddRecipeButton.setText(resourceBundle.getString("shoppingList.addRecipe"));
        shoppingListBackButton.setText(resourceBundle.getString("shoppingList.back"));
        shoppingListClearButton.setText(resourceBundle.getString("shoppingList.clear"));
    }

    /**
     * Goes back to overview
     */
    public void goBack() {
        mainCtrl.showOverview();
    }

    /**
     * Refreshes the page
     */
    public void refresh() {
        items.setAll(shoppingList.getShoppingList());
        totalItemsLabel.setText(
                "Total: " + items.size() + " items"
        );
    }

    /**
     * Edits an ingredient
     * @param ingredient
     */
    public void editIngredient(IngredientInShoppingList ingredient) {
        mainCtrl.showEditIngredient(ingredient);
    }

    /**
     * Adds an ingredient
     */
    public void addIngredient() {
        mainCtrl.showAddIngredient();
    }

    /**
     * Adds a recipe
     */
    public void addRecipe() {
        mainCtrl.showAddRecipeIngredientsOverview();
    }

    /**
     * clears the list
     */
    public void clearList() {
        shoppingList.resetShoppingList();
        items.clear();
        refresh();
    }
    /**
     * Downloads the shopping list
     */
    public void downloadShoppingList() {
        mainCtrl.showSaveRecipe(this.shoppingList, this);
    }
}
