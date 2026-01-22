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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.ListCell;

import java.net.URL;
import java.util.Comparator;
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
     * Constructor
     *
     * @param mainCtrl the controllers of all the panes store
     *                 the main controller as an object
     * @param server   communication to server.
     */
    @Inject
    public ShoppingListCtrl(MainCtrl mainCtrl, ServerUtils server,
                            ShoppingList shoppingList) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.shoppingList=shoppingList;
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

        shoppingListView.setCellFactory(lv -> new ListCell<IngredientInShoppingList>() {
            @Override
            protected void updateItem(IngredientInShoppingList item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString()); // or item.getName()
                    updateStyle(item);
                }
            }

            {
                setOnMouseClicked(event -> {
                    if (!isEmpty()) {
                        IngredientInShoppingList item = getItem();

                        // toggle checked state
                        item.setChecked(!item.isChecked());

                        // move checked items to the bottom
                        FXCollections.sort(items,
                                Comparator.comparing(IngredientInShoppingList::isChecked));

                        updateStyle(item);
                        addEditButtonToIngredient();
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
        ImageView iv = new ImageView(new Image(
                getClass().getResourceAsStream("/pictures/save.png")));
        iv.setFitHeight(20);
        iv.setFitWidth(20);
        downloadShoppingListButton.setGraphic(iv);

    }

    /**
     * Updates the UI elements to the new selected language.
     *
     * @param resourceBundle the resource bundle corresponding to the new language.
     */
    public void updateLanguage(ResourceBundle resourceBundle) {
        shoppingListLabel.setText(resourceBundle.getString("shoppingList.label"));
        shoppingListAddIngredients.setText(resourceBundle.getString("shoppingList.addIngredient"));
        shoppingListAddRecipeButton.setText(resourceBundle.getString("shoppingList.addRecipe"));
        shoppingListBackButton.setText(resourceBundle.getString("shoppingList.back"));
        shoppingListClearButton.setText(resourceBundle.getString("shoppingList.clear"));
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
        totalItemsLabel.setText(
                "Total: " + shoppingList.getShoppingList().size() + " items");
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
    }

    /**
     * Adds selected recipe ingredients.
     */
    public void addRecipe() {
        mainCtrl.showAddRecipeIngredientsOverview();
    }

    /**
     * Clears the full list
     */
    public void clearList() {
        shoppingList.resetShoppingList();
        shoppingListView.getItems().clear();
        ingredientsPane.getChildren().clear();
        refresh();

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
                deleteButton.setDisable(items.get(i).isChecked());
                editButton.setDisable(items.get(i).isChecked());
                HBox buttonBox = new HBox(8); // 8 px space
                buttonBox.setPickOnBounds(false);
                buttonBox.getChildren().addAll(deleteButton, editButton);
                ingredientsPane.getChildren().add(buttonBox);
            }
        }
    }

    /**
     * Shows the saveRecipe scene. (Even if it is called saveRecipe, it
     * is used for both, saveRecipe and saveShoppingList)
     */
    public void downloadShoppingList() {
        mainCtrl.showSaveRecipe(this.shoppingList, this);
    }
}
