package client.scenes;

import client.commonsClient.ShoppingList;
import client.utils.ServerUtils;
import commons.Recipes;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AddRecipeIngredientsCtrl implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private ShoppingList shoppingList;
    private ObservableList<Recipes> data;
    @FXML
    private ChoiceBox<Recipes> choiceBox;

    @FXML
    private Label arioLabel;

    @FXML
    private Button arioOkButton;

    @FXML
    private Button arioCancelButton;


    /**
     * Constructor for controller
     * @param mainCtrl main contrl.
     * @param server server
     */
    @Inject
    public AddRecipeIngredientsCtrl(MainCtrl mainCtrl, ServerUtils server,
                                    ShoppingList shoppingList) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.shoppingList=shoppingList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Updates the UI elements to the new selected language.
     * @param resourceBundle the resource bundle corresponding to the new language.
     */
    public void updateLanguage(ResourceBundle resourceBundle) {
        arioLabel.setText(resourceBundle.getString("ario.label"));
        arioCancelButton.setText(resourceBundle.getString("ario.cancel"));
        arioOkButton.setText(resourceBundle.getString("ario.ok"));
    }

    /**
     * Shows overview when canceled.
     */
    public void cancel() {
        mainCtrl.showShoppingList();
    }

    /**
     * ChoiceBox setter.
     * @param data
     */
    public void setChoiceBox(ObservableList<Recipes> data) {
        this.data=data;
        choiceBox.setItems(this.data);
    }

    /**
     * Parses all ingredients in a recipe.
     */
    public void addRecipeIngredients(){
        if(choiceBox.getValue()==null) return;
        Recipes recipe= choiceBox.getValue();
        mainCtrl.showOverviewList(recipe);

    }

    /**
     * Pressing enter key should remove recipe
     * Pressing escape key should cancel
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()){
            case ENTER:
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
     * the choice box is updated
     * with a new or changed recipe in the db
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
