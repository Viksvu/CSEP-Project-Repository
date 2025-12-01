package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.IngredientInRecipe;
import commons.Recipes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class RecipeOverviewCtrl implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

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

    ObservableList<Recipes> data;
    ObservableList<IngredientInRecipe> ingredientsData;
    ObservableList<String> preparationsData;

    //IMPORTANT: Change String to Recipe
    // ObservableList<String> recipeObservableList;

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

    }



    /**
     * Refreshes the split panes and the content in the ListViews.
     */
    public void refresh() {
        splitPaneRefreshButton.setDividerPosition(0, 0.10090361445783134);
        splitNameDetails.setDividerPosition(0, 0.29797979797979796);
        var serverRecipes = server.getRecipes();
        data = FXCollections.observableArrayList(serverRecipes);

        //ObservableList<String> recipeList
            // = FXCollections.observableArrayList(server.getRecipes());
        recipeListView.setItems(data);
        ingredientListView.setItems(ingredientsData);

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
     * Handles recipes being clicked showing corresponding ingredients and
     * preparations steps
//     * @param actionEvent
     */

    public void recipeClicked(MouseEvent actionEvent) {

        Recipes selectedRecipe =
                recipeListView
                .getSelectionModel()
                .getSelectedItem();
        if (!ingredientsData.contains(selectedRecipe.getName())) {
            //ingredientsData.add("You are looking at some ingredients of "
//                +actionEvent.getPickResult().toString());
//                +actionEvent.getTarget().toString());
                    //+ recipeListView.getSelectionModel().getSelectedItem().toString());
        }

        updateIngredients(selectedRecipe);
        updatePreparations(selectedRecipe);
    }

    /**
     * Updates the ingredients section
     * @param recipes the selected recipe will be
     *                passed on as a parameter
     */
    public void updateIngredients(Recipes recipes) {
        var ingredients = recipes.getIngredients();
        ingredientsData = FXCollections.observableArrayList(ingredients);
    }

    /**
     * Updates the preparation steps section
     * @param recipes the selected recipe will e
     *                passed on as a parameter
     */
    public void updatePreparations(Recipes recipes) {
        return;
    }

}
