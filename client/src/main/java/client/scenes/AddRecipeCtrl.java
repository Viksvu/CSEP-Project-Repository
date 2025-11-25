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

    @Inject
    public AddRecipeCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //In case we have initialisation steps in the future
    }

    public void cancel(){
        mainCtrl.showOverview();
    }

    public void add(){
        mainCtrl.addRecipeToList(nameField.getText());
        mainCtrl.showOverview();
    }
}
