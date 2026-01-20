package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.PreparationStep;
import commons.Recipes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPreparationStepCtrl implements Initializable {


    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private Recipes recipe;

    @FXML
    private TextField nameField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label addPrepStepLable;

    @FXML
    private Button addPrepStepCancel;

    @FXML
    private Button addPrepStepOk;

    /**
     * Constructor
     * @param mainCtrl main controller
     * @param server functions to interact to server
     */
    @Inject
    public AddPreparationStepCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.recipe = null;
    }

    /**
     * initialises all fields
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }

    /**
     * Updates the UI elements to the new selected language.
     * @param resourceBundle the resource bundle corresponding to the new language.
     */
    public void updateLanguage(ResourceBundle resourceBundle) {
        addPrepStepLable.setText(resourceBundle.getString("addPrepStep.label"));
        addPrepStepCancel.setText(resourceBundle.getString("addPrepStep.cancel"));
        addPrepStepOk.setText(resourceBundle.getString("addPrepStep.ok"));
    }

    /**
     * Cancel just clears the fields and
     * shows back the overview field
     */
    public void cancel() {
        nameField.clear();
        mainCtrl.showOverview();
    }

    /**
     * Adds an ingredient to the recipe that was selected
     */
    public void add() {
        String name = nameField.getText();
        try {
            PreparationStep preparationStep = new PreparationStep();
            preparationStep.setDescription(name);
            server.addPreparationStepToRecipe(preparationStep, recipe);
            mainCtrl.showOverview();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
            System.out.println(e.getMessage());
            errorLabel.setVisible(true);
        }
    }

    /**
     * Provide the recipe to add the preparation step to
     * @param recipe recipe to add preparation step to
     */
    public void provideRecipe(Recipes recipe) {
        this.recipe = recipe;
    }
}
