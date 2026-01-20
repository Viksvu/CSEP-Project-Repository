package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Recipes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class RemoveRecipeCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private ObservableList<Recipes> data;
    @FXML
    private ChoiceBox<Recipes> choiceBox;

    @FXML
    private Label removeRecipeLabel;

    @FXML
    private Button removeRecipeRemove;

    @FXML
    private Button removeRecipeCancel;

    /**
     * A constructor for remove
     * recipe controller.
     *
     * @param mainCtrl the main controller.
     */
    @Inject
    public RemoveRecipeCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Removes recipe from list.
     */
    public void confirmRemoveRecipe() {
        //mainCtrl.removeRecipeFromList(choiceBox.getValue());
        //choiceBox.getItems().clear();
        if(choiceBox.getValue()==null) return;
        server.removeRecipe(choiceBox.getValue());
        mainCtrl.showOverview();
    }

    /**
     * Shows overview when canceled.
     */
    public void cancel() {
        mainCtrl.showOverview();
    }

    /**
     * Populates the choice box
     */
    public void setup() {
        var serverRecipes = server.getRecipes();
        this.data = FXCollections.observableArrayList(serverRecipes);
        choiceBox.setItems(this.data);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setup();
        } catch (NullPointerException e) {
            mainCtrl.showOverview();
        }
    }

    /**
     * Updates the UI elements to the new selected language.
     * @param resourceBundle the resource bundle corresponding to the new language.
     */
    public void updateLanguage(ResourceBundle resourceBundle) {
        removeRecipeLabel.setText(resourceBundle.getString("removeRecipe.label"));
        removeRecipeCancel.setText(resourceBundle.getString("removeRecipe.cancel"));
        removeRecipeRemove.setText(resourceBundle.getString("removeRecipe.remove"));
    }

    /**
     * Pressing enter key should remove recipe
     * Pressing escape key should cancel
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()){
            case ENTER:
                confirmRemoveRecipe();
                break;
            case ESCAPE:
                cancel();
        }
    }

    /**
     * If propagated
     * from another client
     * the choice box
     * losses a removed recipe value
     * @param id the recipe id
     */
    public void removeRecipeFromListView(long id){
            for(Recipes recipe:data){
                if(recipe.getId()==id){
                    data.remove(recipe);
                    break;
                }
            }
    }

    /**
     * If propagated
     * from another client
     * the choice box is added
     * with a new recipe in the db
     * @param id the recipe id
     */
    public void addRecipeToListView(long id) {
        Recipes recipeNew = server.getRecipe(id);
        for (int i = 0; i < data.size(); i++) {
            Recipes recipe = data.get(i);
            if (recipe.getId() == id) {
                recipe.setName(recipeNew.getName());
                data.set(i, recipe);
                return;
            }
        }
        data.add(recipeNew);
    }



}
