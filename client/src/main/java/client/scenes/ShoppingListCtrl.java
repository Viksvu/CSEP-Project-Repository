package client.scenes;

import client.EditButton;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public ShoppingListCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shoppingListView.setItems(items);
        ImageView iv = new ImageView(new Image(
                getClass().getResourceAsStream("/pictures/save.png")));
        iv.setFitHeight(20);
        iv.setFitWidth(20);
        downloadShoppingListButton.setGraphic(iv);
    }
    /**
     * Updates the UI elements to the new selected language.
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
        shoppingListView.setCellFactory(lv -> new ListCell<>(){
            @Override
            protected void updateItem(IngredientInShoppingList item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    EditButton<IngredientInShoppingList> deleteButton =
                            new EditButton<>(item,"delete",getIndex(),
                                    shoppingListView,server,
                                    ShoppingListCtrl.this,EditButtonOptions.REMOVE_INGREDIENT,
                                    shoppingList);
                    EditButton<IngredientInShoppingList> editButton =
                            new EditButton<>(
                                    item,"edit",getIndex(),
                                    shoppingListView,server,
                                    ShoppingListCtrl.this,
                                    EditButtonOptions.EDIT_INGREDIENT,
                                    shoppingList);

                    HBox row=new HBox(8,deleteButton,editButton);
                    row.setPickOnBounds(false);
                    setText(item.toString());
                    setGraphic(row);

                    //   setContentDisplay(ContentDisplay.RIGHT);
                    setGraphicTextGap(-80);
                    setTextOverrun(OverrunStyle.ELLIPSIS);
                }
            }
        });
    }

    /**
     * Shows the saveRecipe scene. (Even if it is called saveRecipe, it
     * is used for both, saveRecipe and saveShoppingList)
     */
    public void downloadShoppingList() {
        mainCtrl.showSaveRecipe(this.shoppingList, this);
    }
}
