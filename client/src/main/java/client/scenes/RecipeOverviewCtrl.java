package client.scenes;

import client.EditButton;
import client.EditButtonOptions;
import client.commonsClient.ShoppingList;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.IngredientInRecipe;
import commons.Recipes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RecipeOverviewCtrl implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private final ShoppingList shoppingList = new ShoppingList();
    ObservableList<Recipes> data;
    ObservableList<Recipes> data1;
    ObservableList<IngredientInRecipe> ingredientsData;
    ObservableList<String> preparationsData;
    // Button someButton;
    ArrayList<Button> ingredientButtons;

    @FXML
    public TextField searchField;


    @FXML
    private SplitPane splitPaneRefreshButton;
    @FXML
    private SplitPane splitNameDetails;
    //IMPORTANT: Change String type to Recipe.
    @FXML
    private ListView<Recipes> recipeListView;
    //IMPORTANT: Potential change String type to Ingredient.
    @FXML
    private ListView<IngredientInRecipe> ingredientListView;
    //IMPORTANT: Type change likely
    // not needed in future but usage will have to be modified
    @FXML
    private ListView<String> preparationsListView;
    @FXML
    private AnchorPane ingredientsPane;

    @FXML
    private Button addIngredientButton;
    //IMPORTANT: Change String to Recipe
    // ObservableList<String> recipeObservableList;

    @FXML
    private Button addToShop;

    private Recipes lastSelectedRecipe;

    /**
     * Recipe overview controller constructor.
     *
     * @param mainCtrl main controller.
     */
    @Inject
    public RecipeOverviewCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        //this.recipeObservableList = FXCollections.observableArrayList();
        //splitPaneRefreshButton = new SplitPane();
        this.server = server;
        this.ingredientsData = FXCollections.observableArrayList();
        ingredientButtons = new ArrayList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //ObservableList<String> recipeObservableList = mainCtrl.getRecipes();
        //ObservableList<Recipes> recipeList =
        // FXCollections.observableArrayList(server.getRecipes());
        //recipeObservableList.add("Test String 1");
        // Adding anything to the recipeObservableList
        // will also add to the ListView of Recipes
        //recipeListView.setItems(recipeObservableList);
        //recipeListView.setEditable(true);
        data = FXCollections.observableArrayList();
        data1 = FXCollections.observableArrayList();
    }

    /**
     * Refreshes the split panes and the content in the ListViews.
     */
    public void refresh() {
        splitPaneRefreshButton.setDividerPosition(0, 0.10090361445783134);
        splitNameDetails.setDividerPosition(0, 0.29797979797979796);
        var serverRecipes = server.getRecipes();
        data = FXCollections.observableArrayList(serverRecipes);
        if (getSelectedRecipe() != null) {
            lastSelectedRecipe = getSelectedRecipe();
        }

        //ObservableList<String> recipeList
        // = FXCollections.observableArrayList(server.getRecipes());

        try {
            if (lastSelectedRecipe == null) {
                updateIngredients(getSelectedRecipe());
                updatePreparations(getSelectedRecipe());
            } else {
                updateIngredients(lastSelectedRecipe);
                updatePreparations(lastSelectedRecipe);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!data1.isEmpty()) recipeListView.setItems(data1);
        else recipeListView.setItems(data);
        ingredientListView.setItems(ingredientsData);
        addEditButtonToIngredient();

    }

    /**
     * Adds an edit button next to the name of the ingredient
     */
    public void addEditButtonToIngredient() {
        ingredientsPane.getChildren().clear();
        ingredientsPane.getChildren().addAll(ingredientListView);
        ingredientsPane.getChildren().add(addIngredientButton);
        ingredientsPane.getChildren().add(addToShop);
        if (!ingredientsData.isEmpty()) {
            int numIngredients = ingredientsData.size();
            for (int i = 0; i < numIngredients; i++) {
                EditButton editButton =
                        new EditButton(
                                ingredientsData.get(i),
                                "delete",
                                i,
                                ingredientListView,
                                server,
                                lastSelectedRecipe,
                                this,
                                EditButtonOptions.REMOVE_INGREDIENT
                        );
                // TO DO: REPLACE EDIT TEXT WITH PENCIL ICON

                ingredientsPane.getChildren().add(editButton);
            }
        }
    }

    /**
     * Shows the scene of "add recipe"
     */
    public void addRecipe() {
        mainCtrl.showAdd();
    }

    /**
     * Shows the scene of remove recipe
     */
    public void removeRecipe() {
        mainCtrl.showRemove();
    }

    /**
     * Adds ab ingredient to the recipe
     */
    public void addIngredient() {
        Recipes selectedRecipe =
                recipeListView
                        .getSelectionModel()
                        .getSelectedItem();
        mainCtrl.showAddIngredient(selectedRecipe);
    }

    /**
     * Checks which Recipe from the ListView has been
     * selected.
     *
     * @return a recipe object
     */
    public Recipes getSelectedRecipe() {
        Recipes ret = recipeListView
                .getSelectionModel()
                .getSelectedItem();
        if (ret != null) {
            this.lastSelectedRecipe = ret;
        }
        return ret;
    }

    /**
     * Handles recipes being clicked showing corresponding ingredients and
     * preparations steps
     *
     * @param actionEvent the event.
     */

    public void recipeClicked(MouseEvent actionEvent) {
        refresh();
    }

    /**
     * Updates the ingredients section
     *
     * @param recipes the selected recipe will be
     *                passed on as a parameter
     */
    public void updateIngredients(Recipes recipes) {
        var ingredients = server.getIngredientsInRecipes(recipes);
        ingredientsData = FXCollections.observableArrayList(ingredients);
    }

    /**
     * Updates the preparation steps section
     *
     * @param recipes the selected recipe will e
     *                passed on as a parameter
     */
    public void updatePreparations(Recipes recipes) {
        return;
    }

    /**
     *
     * The button search has been clicked
     */
    public void searchInit() {
        String text = searchField.getText();
        refresh();
        mainCtrl.applySearchFilter(text);
        mainCtrl.applySorting(text);
        data1 = mainCtrl.getSortedRecipes();
        refresh();
    }

    /**
     * Adds selected recipes
     * ingredients to shopping list overview
     */
    public void addToOverViewIngredients() {
        mainCtrl.showOverviewList(lastSelectedRecipe);
    }


    /**
     * Shows the current shopping list
     */
    public void openShoppingList() {
        mainCtrl.showShoppingList();
    }


    public ObservableList<Recipes> getData() {
        return data;
    }
}
