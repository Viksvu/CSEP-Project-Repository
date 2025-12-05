package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Recipes;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class RemoveRecipeCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private ChoiceBox<Recipes> choiceBox;

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
