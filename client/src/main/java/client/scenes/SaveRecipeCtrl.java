package client.scenes;

import client.commonsClient.ShoppingList;
import com.google.inject.Inject;
import commons.Printable;
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
    private Printable thing;

    private final MainCtrl mainCtrl;

    @FXML
    private TextField filenameField;

    /**
     * Sets which Recipe to save
     * @param recipes
     */
    @Deprecated
    public void provideRecipe(Recipes recipes) {
        this.thing = recipes;
    }

    /**
     * Sets which Shopping List to save
     * @param shoppingList
     */
    @Deprecated
    public void provideShoppingList(ShoppingList shoppingList) {
        this.thing = shoppingList;
    }

    /**
     * Sets which Printable thing to save
     * @param thing
     */
    public void provideThing(Printable thing) {
        this.thing = thing;
    }

    /**
     * Initialises the SaveRecipeCtrl
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (thing == null) {return;}
        if (thing instanceof Recipes recipe) {
            filenameField.setText(recipe.getName());
        }
        else if (thing instanceof ShoppingList shoppingList) {
            filenameField.setText("MyShoppingList");
        }

    }

    /**
     * Constructir
     * @param mainCtrl
     */
    @Inject
    public SaveRecipeCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        thing = null;
    }

    /**
     * Determines the action of the save button
     */
    public void save() {
        mainCtrl.showOverview();
        try {
            Printer.print(thing, filenameField.getText());
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
        else if (keyEvent.getCode() == KeyCode.ESCAPE) {
            filenameField.clear();
            mainCtrl.showOverview();
        }
    }
}
