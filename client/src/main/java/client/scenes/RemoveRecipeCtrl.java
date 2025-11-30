package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RemoveRecipeCtrl implements Initializable {
    private final MainCtrl mainCtrl;

    @FXML
    private ChoiceBox<String> choiceBox;

    /**
     * A constructor for remove
     * recipe controller.
     *
     * @param mainCtrl the main controller.
     */
    @Inject
    public RemoveRecipeCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;

    }

    /**
     * Removes recipe from list.
     */
    public void confirmRemoveRecipe() {
        mainCtrl.removeRecipeFromList(choiceBox.getValue());
        //choiceBox.getItems().clear();
        mainCtrl.showOverview();
    }

    /**
     * Shows overview when canceled.
     */
    public void cancel() {
        mainCtrl.showOverview();
    }

    /**
     * Populates the choice box
     */
    public void setup() {
        choiceBox.setItems(mainCtrl.getRecipes());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            choiceBox.setItems(mainCtrl.getRecipes());
        } catch (NullPointerException e) {
            mainCtrl.showOverview();
        }
    }
}
