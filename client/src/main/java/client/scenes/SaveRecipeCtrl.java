package client.scenes;

import com.google.inject.Inject;
import commons.Printer;
import commons.Recipes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SaveRecipeCtrl implements Initializable {
    private Recipes recipesToSave;

    private final MainCtrl mainCtrl;

    @FXML
    private TextField filenameField;

    /**
     * Sets which Recipe to save
     * @param recipes
     */
    public void provideRecipe(Recipes recipes) {
        this.recipesToSave = recipes;
    }

    /**
     * Initialises the SaveRecipeCtrl
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (recipesToSave == null) {return;}
        filenameField.setText(recipesToSave.getName());
    }

    /**
     * Constructir
     * @param mainCtrl
     */
    @Inject
    public SaveRecipeCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        recipesToSave = null;
    }

    /**
     * Determines the action of the save button
     */
    public void save() {
        mainCtrl.showOverview();
        try {
            Printer.print(recipesToSave, filenameField.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Command that should be called if enter is passed
     * @param keyEvent key pressed
     */
    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            save();
        }
    }
}
