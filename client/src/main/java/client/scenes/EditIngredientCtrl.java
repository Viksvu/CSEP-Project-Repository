package client.scenes;

import client.utils.ServerUtils;
import jakarta.inject.Inject;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class EditIngredientCtrl implements Initializable {

    private MainCtrl mainCtrl;
    private ServerUtils server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * A constructor for the edit
     * ingredient ctrl
     *
     * @param mainCtrl the main controller
     * @param server the main server
     */
    @Inject
    public EditIngredientCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }




}
