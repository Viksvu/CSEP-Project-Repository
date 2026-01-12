package client.scenes;

import client.EditButtonOptions;
import client.EditButtonShoppingList;
import client.commonsClient.IngredientInShoppingList;
import client.commonsClient.ShoppingList;
import client.utils.ServerUtils;
import commons.IngredientInRecipe;
import commons.Recipes;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OverviewListCtrl implements Initializable {

    private MainCtrl mainCtrl;
    private ServerUtils server;
    private ShoppingList shoppingList;
    private ObservableList<IngredientInShoppingList> items
            = FXCollections.observableArrayList();
    private Recipes currRecipe;
    private Scene previousScene;
    @FXML
    private ListView<IngredientInShoppingList> overviewListView;

    @FXML
    private AnchorPane overviewListPane;

    @FXML
    private Button addIngredient;

    @FXML
    private Text overviewListLabel;

    @FXML
    private Button overviewListAddButton;

    /**
     * A constructor for overview controller
     *
     * @param mainCtrl the mainctrl
     * @param server   the server.
     */
    @Inject
    public OverviewListCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        overviewListLabel.setText(resourceBundle.getString("overviewList.label"));
        overviewListAddButton.setText(resourceBundle.getString("overviewList.add"));
        items.clear();
        overviewListView.setItems(items);
    }

    /**
     * Clears the buffer
     */
    public void clear() {
        shoppingList.getBufferList().clear();
        refresh();
    }

    /**
     * Refreshes the stage
     */
    public void refresh() {
        items.setAll(shoppingList.getBufferList());
        addEditButtonToIngredient();
    }


    /**
     * add ingredient scene
     */
    public void addIngredient() {
        mainCtrl.showAddIngredient();
    }

    /**
     * Adds add ingredients from some recipe
     *
     * @param ingredientInRecipeList the ingredients in a recipe
     * @param recipe                 the recipe
     *                               the list of ingredients
     */
    public void addIngredients(List<IngredientInRecipe> ingredientInRecipeList
            ,Recipes recipe) {
        this.currRecipe=recipe;
        for (IngredientInRecipe ingredientInRecipe : ingredientInRecipeList) {
            IngredientInShoppingList ingredient=
                    new IngredientInShoppingList(ingredientInRecipe);
            ingredient.setRecipe(null);
            shoppingList.getBufferList()
                    .add(ingredient);
        }
    }

    /**
     * Go back to the prev scene
     */
    public void goBack() {
        if (previousScene.getRoot().getId().equals("addRepIngrs")) {
            mainCtrl.showShoppingList();
        }
        if (previousScene.getRoot().getId().equals("overview")) {
            mainCtrl.showOverview();
        }
    }

    /**
     * Add the whole buffer
     * to the shopping list
     */
    public void addToShoppingList() {
        for (IngredientInShoppingList ingredient:shoppingList.getBufferList() ) {
            ingredient.setRecipe(currRecipe);
        }
        shoppingList.addOverviewToShoppingList();
        goBack();
    }

    /**
     * Setter for the shopping list
     *
     * @param shoppingList the shopping list
     */
    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
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
     * sets the previous scene
     *
     * @param previousScene the previous scene.
     */
    public void previousSceneSetter(Scene previousScene) {
        this.previousScene = previousScene;
    }

    /**
     * Adds an edit button next to the name of the ingredient
     */
    public void addEditButtonToIngredient() {
        overviewListPane.getChildren().clear();
        overviewListPane.getChildren().addAll(overviewListView);
        overviewListPane.getChildren().add(addIngredient);
        if (!items.isEmpty()) {
            int numIngredients = items.size();
            for (int i = 0; i < numIngredients; i++) {
                EditButtonShoppingList deleteButton =
                        new EditButtonShoppingList(
                                items.get(i),
                                "delete",
                                i,
                                overviewListView,
                                this, shoppingList,
                                EditButtonOptions.REMOVE_INGREDIENT
                        );
                EditButtonShoppingList editButton =
                        new EditButtonShoppingList(
                                items.get(i),
                                "edit",
                                i,
                                overviewListView,
                                this, shoppingList,
                                EditButtonOptions.EDIT_INGREDIENT
                        );
                HBox buttonBox = new HBox(8); // 8 px space
                buttonBox.setPickOnBounds(false);
                buttonBox.getChildren().addAll(deleteButton, editButton);
                overviewListPane.getChildren().add(buttonBox);


            }
        }
    }
}
