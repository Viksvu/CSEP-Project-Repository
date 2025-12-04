package client.scenes;

import com.google.inject.Inject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RecipeOverviewCtrl implements Initializable {

    private final MainCtrl mainCtrl;

    @FXML
    public TextField searchField;

    @FXML
    private SplitPane splitPaneRefreshButton;

    @FXML
    private SplitPane splitNameDetails;

    //IMPORTANT: Change String type to Recipe.
    @FXML
    private ListView<String> recipeListView;

    //IMPORTANT: Potential change String type to Ingredient.
    @FXML
    private ListView<String> ingredientListView;

    //IMPORTANT: Type change likely
    // not needed in future but usage will have to be modified
    @FXML
    private ListView<String> preparationsListView;


    //IMPORTANT: Change String to Recipe
    // ObservableList<String> recipeObservableList;

    /**
     * Recipe overview controller constructor.
     *
     * @param mainCtrl main controller.
     */
    @Inject
    public RecipeOverviewCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        //this.recipeObservableList = FXCollections.observableArrayList();
        //splitPaneRefreshButton = new SplitPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> recipeObservableList = mainCtrl.getRecipes();
        //recipeObservableList.add("Test String 1");
        // Adding anything to the recipeObservableList
        // will also add to the ListView of Recipes
        recipeListView.setItems(recipeObservableList);
    }

    //IMPORTANT: Must be updated once ServerUtils are prepared.
    // Currently, it only places all the split panes at the desired locations

    /**
     * Refreshes the split panes and the content in the ListViews.
     */
    public void refresh() {
        splitPaneRefreshButton.setDividerPosition(0, 0.10090361445783134);
        splitNameDetails.setDividerPosition(0, 0.29797979797979796);
        ObservableList<String> recipeObservableList = mainCtrl.getRecipes();
        recipeListView.setItems(recipeObservableList);

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
     * The button search has been clicked
     */
    public void searchInit(){
        String text=searchField.getText();
        mainCtrl.applySearchFilter(text);
        mainCtrl.applySorting(text);
    }


}
