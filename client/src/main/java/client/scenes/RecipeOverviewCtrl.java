package client.scenes;

import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;

import java.net.URL;
import java.util.ResourceBundle;

public class RecipeOverviewCtrl implements Initializable {

    private final MainCtrl mainCtrl;


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

    //IMPORTANT: Type change likely not needed in future but usage will have to be modified
    @FXML
    private ListView<String> preparationsListView;


    //IMPORTANT: Change String to Recipe
    private ObservableList<String> recipeObservableList;

    @Inject
    public RecipeOverviewCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.recipeObservableList = FXCollections.observableArrayList();
        //splitPaneRefreshButton = new SplitPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recipeObservableList.add("Test String 1"); // Adding anything to the recipeObservableList will also add to the ListView of Recipes
        recipeListView.setItems(recipeObservableList);
    }

    //IMPORTANT: Must be updated once ServerUtils are prepared. Currently, it only places all the split panes at the desired locations
    public void refresh() {
        splitPaneRefreshButton.setDividerPosition(0, 0.10090361445783134);
        splitNameDetails.setDividerPosition(0, 0.29797979797979796);
    }

    public void addRecipe() {
        mainCtrl.showAdd();
    }

    public void removeRecipe() {
        return;
    }
}
