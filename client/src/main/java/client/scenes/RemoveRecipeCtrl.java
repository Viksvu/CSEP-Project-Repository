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

    @Inject
    public RemoveRecipeCtrl(MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;

    }

    public void confirmRemoveRecipe(){
        mainCtrl.showOverview();
    }

    public void cancel() {
        mainCtrl.showOverview();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            choiceBox.setItems(mainCtrl.getRecipes());
        } catch (NullPointerException e) {
            return;
        }
    }
}
