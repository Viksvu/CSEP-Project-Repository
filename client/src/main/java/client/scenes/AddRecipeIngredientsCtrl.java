package client.scenes;

import client.commonsClient.ShoppingList;
import client.utils.ServerUtils;
import commons.Recipes;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AddRecipeIngredientsCtrl implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private ShoppingList shoppingList;
    @FXML
    private ChoiceBox<Recipes> choiceBox;


    /**
     * Constructor for controller
     * @param mainCtrl main contrl.
     * @param server server
     */
    @Inject
    public AddRecipeIngredientsCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        choiceBox.setItems(data);
    }

    /**
     * shopping list setter
     * @param shoppingList the shopping list.
     */
    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
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


}
