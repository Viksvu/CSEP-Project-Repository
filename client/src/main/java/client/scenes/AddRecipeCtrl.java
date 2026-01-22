package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.RecipeLanguage;
import commons.Recipes;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddRecipeCtrl implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private TextField nameField;

    @FXML
    private Button addRecipeOkButton;

    @FXML
    private Button addRecipeCancelButton;

    @FXML
    private Label addRecipeLabel;

    @FXML
    private ChoiceBox<RecipeLanguage> languageBox;
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
        languageBox.getItems().addAll(RecipeLanguage.values());
    }

    /**
     * Updates the UI elements to the new selected language.
     * @param resourceBundle the resource bundle corresponding to the new language.
     */
    public void updateLanguage(ResourceBundle resourceBundle) {
        addRecipeOkButton.setText(resourceBundle.getString("addRecipe.ok"));
        addRecipeCancelButton.setText(resourceBundle.getString("addRecipe.cancel"));
        addRecipeLabel.setText(resourceBundle.getString("addRecipe.label"));
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
     * Sets the choice box to the current app language
     * @param locale
     */
    public void setLanguage (Locale locale) {
        switch (locale.getLanguage()) {
            case "en":
                languageBox.getSelectionModel().select(0);
                break;
            case "es":
                languageBox.getSelectionModel().select(3);
                break;
            case "de":
                languageBox.getSelectionModel().select(2);
                break;
            case "nl":
                languageBox.getSelectionModel().select(1);
                break;
            default:
                System.err.println(locale.getLanguage() + " has not " +
                        "been implemented in the choicebox!");
        }
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
            ArrayList<Recipes> currRecipes =
                    (ArrayList<Recipes>) server.getRecipes();
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
            Recipes ret = new Recipes(recipeName);
            ret.setLanguage(languageBox.getSelectionModel().getSelectedItem());
            server.addRecipe(ret);
            System.out.println(languageBox.getSelectionModel().getSelectedItem());
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
