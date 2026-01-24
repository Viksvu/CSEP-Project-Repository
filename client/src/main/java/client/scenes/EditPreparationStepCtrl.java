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

public class EditPreparationStepCtrl implements Initializable {


    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private Recipes recipe;
    private PreparationStep preparationStep;
    private ResourceBundle bundle;
    @FXML
    private TextField nameField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button canc;

    @FXML
    private Label prep;

    /**
     * Constructor
     *
     * @param mainCtrl main controller
     * @param server   functions to interact to server
     */
    @Inject
    public EditPreparationStepCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.recipe = null;
    }

    /**
     * initialises all fields
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setText("");
        errorLabel.setVisible(false);
        this.bundle=resourceBundle;
    }

    /**
     * updates teh scene language
     * @param resourceBundle the resource bundle
     */
    public void updateLanguage(ResourceBundle resourceBundle) {
        this.bundle = resourceBundle;
        canc.setText(resourceBundle.getString("editStep.Cancel"));
        prep.setText(resourceBundle.getString("editStep.PreparationStep"));
        // Example if you have prompt text:
        // nameField.setPromptText(resourceBundle.getString("prepStep.edit.prompt"));
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
     * Gets the index of
     * the current prep step
     * @return the prep step index.
     */
    public int getIndex(){
        for(int i=0;i<recipe.getPreparationSteps().size();i++){
            if(recipe.getPreparationSteps().get(i).equals(preparationStep)){
                return i;
            }
        }
        return -1;
    }
    /**
     * Adds an ingredient to the recipe that was selected
     */
    public void add() {
        String name = nameField.getText();
        try {
            int index=getIndex();
            if(index==-1){
                System.err.println("No such prep in recipe");
                return;
            }
            preparationStep.setDescription(name);
            server.editPreparationStepFromRecipe(preparationStep, recipe, index);
            mainCtrl.showOverview();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
            System.out.println(e.getMessage());
            errorLabel.setVisible(true);
        }
    }

    /**
     * Provide the recipe to add the preparation step to
     *
     * @param recipe recipe to add preparation step to
     */
    public void provideRecipe(Recipes recipe) {
        this.recipe = recipe;
    }

    /**
     * A setter of preparation step
     *
     * @param preparationStep the preparation step
     */
    public void providePrepStep(PreparationStep preparationStep) {
        this.preparationStep=preparationStep;
        nameField.setText(preparationStep.getDescription());
    }
}
