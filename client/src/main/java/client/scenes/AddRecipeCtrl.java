package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Recipes;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddRecipeCtrl implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private TextField nameField;


    /**
     * Constructor
     *
     * @param mainCtrl the controllers of all the panes store
     *                 the main controller as an object
     */
    @Inject
    public AddRecipeCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //In case we have initialisation steps in the future
    }

    /**
     * Clicking cancel should clear whatever the user entered in
     * the text field and then show the overview
     */
    public void cancel() {
        nameField.clear();
        mainCtrl.showOverview();
    }

    /**
     * IMPORTANT: REPLACE THE CURRENT WAY TO CREATE A RECIPE
     * WITH JUST new Recipe(recipeName); once connection with
     * database has been established
     * Clicking add should add the entered recipe to the list
     * and the overview should is then shown
     */
    public void add() {
        String recipeName = nameField.getText();
        try {
            ArrayList<Recipes> currRecipes = (ArrayList<Recipes>) server.getRecipes();
            for (int i=0;i<currRecipes.size();i++){
                if(currRecipes.get(i).getName().equals(recipeName)){
                    return;
                }
            }
        }catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        try {
            server.addRecipe(new Recipes(recipeName));
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        mainCtrl.showOverview();
    }

    /**
     * In case enter key is pressed assume that user wants to add
     * In case escape key is pressed assume that use wants to cancel
     * @param e the pressed key
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                add();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }
}
