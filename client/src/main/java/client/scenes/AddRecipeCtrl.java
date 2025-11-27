package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddRecipeCtrl implements Initializable {

    private final MainCtrl mainCtrl;

    @FXML
    private TextField nameField;


    /**
     * Constructor
     * @param mainCtrl the controllers of all the panes store
     *                 the main controller as an object
     */
    @Inject
    public AddRecipeCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
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
        mainCtrl.addRecipeToList(nameField.getText());
        mainCtrl.showOverview();
    }
}
