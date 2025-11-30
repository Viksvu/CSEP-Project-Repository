package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Recipes;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

import java.net.URL;
import java.util.ResourceBundle;

public class AddRecipeCtrl implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private TextField nameField;


    /**
     * Constructor
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
    public void cancel(){
        nameField.clear();
        mainCtrl.showOverview();
    }

    /**
     * Clicking add should add the entered recipe to the list
     * and the overview should is then shown
     */
    public void add(){
        String recipeName = nameField.getText();
        try{
            server.addRecipe(new Recipes(recipeName));
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        mainCtrl.addRecipeToList(recipeName);
        mainCtrl.showOverview();
    }
}
