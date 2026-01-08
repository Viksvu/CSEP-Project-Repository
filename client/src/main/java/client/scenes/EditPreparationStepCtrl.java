package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.PreparationStep;
import commons.Recipes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditPreparationStepCtrl implements Initializable {


    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private Recipes recipe;
    private PreparationStep preparationStep;
    @FXML
    private TextField nameField;

    @FXML
    private Label errorLabel;

    /**
     * Constructor
     * @param mainCtrl main controller
     * @param server functions to interact to server
     */
    @Inject
    public EditPreparationStepCtrl(MainCtrl mainCtrl, ServerUtils server) {
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
     * Cancel just clears the fields and
     * shows back the overview field
     */
    public void cancel() {
        nameField.clear();
        mainCtrl.showOverview();
    }

    public int getPrepIndex(){
        List<PreparationStep> temp=recipe.getPreparationSteps();
        for(int i=0;i<temp.size();i++){
        if(temp.get(i).equals(this.preparationStep)){
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
            int index=getPrepIndex();
            if(index==-1) {
                System.err.println("Prep step not in recipe");
                return;
            }
            PreparationStep preparationStep = new PreparationStep();
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
     * @param recipe recipe to add preparation step to
     */
    public void provideRecipe(Recipes recipe) {
        this.recipe = recipe;
    }

    /**
     * A setter of preparation step
     * @param preparationStep the preparation step
     */
    public void providePrepStep(PreparationStep preparationStep){

    }
}
