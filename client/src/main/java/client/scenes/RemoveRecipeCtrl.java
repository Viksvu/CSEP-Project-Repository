package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Recipes;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class RemoveRecipeCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private ChoiceBox<Recipes> choiceBox;

    @FXML
    private Label removeRecipeLabel;

    @FXML
    private Button removeRecipeRemove;

    @FXML
    private Button removeRecipeCancel;

    /**
     * A constructor for remove
     * recipe controller.
     *
     * @param mainCtrl the main controller.
     */
    @Inject
    public RemoveRecipeCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Removes recipe from list.
     */
    public void confirmRemoveRecipe() {
        //mainCtrl.removeRecipeFromList(choiceBox.getValue());
        //choiceBox.getItems().clear();
        if(choiceBox.getValue()==null) return;
        server.removeRecipe(choiceBox.getValue());
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
        var serverRecipes = server.getRecipes();
        var data = FXCollections.observableArrayList(serverRecipes);
        choiceBox.setItems(data);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        removeRecipeLabel.setText(resourceBundle.getString("removeRecipe.label"));
        removeRecipeCancel.setText(resourceBundle.getString("removeRecipe.cancel"));
        removeRecipeRemove.setText(resourceBundle.getString("removeRecipe.remove"));
        try {
            setup();
        } catch (NullPointerException e) {
            mainCtrl.showOverview();
        }
    }

    /**
     * Pressing enter key should remove recipe
     * Pressing escape key should cancel
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()){
            case ENTER:
                confirmRemoveRecipe();
                break;
            case ESCAPE:
                cancel();
        }
    }
}
