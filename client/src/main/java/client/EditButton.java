package client;

import client.commonsClient.IngredientInShoppingList;
import client.commonsClient.ShoppingList;
import client.scenes.OverviewListCtrl;
import client.scenes.RecipeOverviewCtrl;
import client.scenes.ShoppingListCtrl;
import client.utils.ServerUtils;
import commons.IngredientInRecipe;
import commons.PreparationStep;
import commons.Recipes;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class EditButton<K> extends Button {
    final private K object;
    final private int index;
    final private ListView<K> parent;
    private ServerUtils server;
    final private Recipes recipe;
    final private ShoppingList shoppingList;
    private RecipeOverviewCtrl ctrl;
    private EditButtonOptions option;
    private Initializable shoppingOverviewListCtrl;

    /**
     * Constructor for edit object button
     * @param object to which this button is associated
     * @param s for button text
     */
    public EditButton(K object,
                      String s, int index, ListView<K> parent,
                      ServerUtils server,
                      Recipes recipe, RecipeOverviewCtrl ctrl,
                      EditButtonOptions option) {
        super(s);
        this.object = object;
        this.index = index;
        this.parent = parent;
        this.server = server;
        this.recipe = recipe;
        this.ctrl=ctrl;
        this.option=option;
        this.shoppingList = null;
        double containerHeight = parent.getLayoutBounds().getHeight();
        super.setTranslateX(
                9 * parent.getItems().get(index).toString().length()
        );
        addButton();
    }

    /**
     * Constructor for Shopping List Edit and Delete buttons
     * @param object next to which button is placed
     * @param s
     * @param index
     * @param parent
     * @param server
     * @param ctrl
     * @param option
     */
    public EditButton(K object,
                      String s, int index, ListView<K> parent,
                      ServerUtils server, OverviewListCtrl ctrl,
                      EditButtonOptions option, ShoppingList shoppingList) {
        super(s);
        this.object = object;
        this.index = index;
        this.parent = parent;
        this.server = server;
        this.recipe = null;
        this.shoppingOverviewListCtrl =ctrl;
        this.option=option;
        this.shoppingList=shoppingList;
        double containerHeight = parent.getLayoutBounds().getHeight();
        super.setTranslateX(
                9 * parent.getItems().get(index).toString().length()
        );
        addButton();
    }

    /**
     * Actually allows to edit by creating a new
     */
    public void addButton() {
        this.setOnAction(event -> {
            if (this.option.equals(EditButtonOptions.REMOVE_INGREDIENT)
                    && object instanceof IngredientInRecipe ingredient) {
                server.removeIngredientFromRecipe(ingredient, recipe);
                sleep();
                ctrl.refresh();
            }
            if (this.option.equals(EditButtonOptions.REMOVE_STEP)
                    && object instanceof PreparationStep step) {
                server.deletePreparationStepToRecipe(step, recipe);
                sleep();
                ctrl.refresh();
            }
            if (this.option.equals(EditButtonOptions.EDIT_INGREDIENT)
                    && object instanceof IngredientInRecipe ingredient) {
                ctrl.editIngredient(ingredient, recipe);
                ctrl.refresh();
            }
            if (this.option.equals(EditButtonOptions.EDIT_STEP)
                    && object instanceof PreparationStep step) {
                ctrl.editPreparationStep(step);
                ctrl.refresh();
            }
            if (object instanceof IngredientInShoppingList ingredient
            && shoppingOverviewListCtrl instanceof OverviewListCtrl overviewListCtrl) {
                if (this.option == EditButtonOptions.REMOVE_INGREDIENT) {
                    shoppingList.getBufferList().remove(ingredient);
                    (overviewListCtrl).refresh();
                }
                else if(this.option == EditButtonOptions.EDIT_INGREDIENT){
                    (overviewListCtrl).editIngredient(ingredient);
                    (overviewListCtrl).refresh();
                }
            }
            if (object instanceof IngredientInShoppingList ingredient
            && shoppingOverviewListCtrl instanceof ShoppingListCtrl shoppingListCtrl) {
                if (this.option == EditButtonOptions.REMOVE_INGREDIENT) {
                    shoppingList.getShoppingList().remove(ingredient);
                    shoppingListCtrl.refresh();
                }
                else if(this.option == EditButtonOptions.EDIT_INGREDIENT){
                    shoppingListCtrl.editIngredient(ingredient);
                    shoppingListCtrl.refresh();
                }
            }

        });
    }

    /**
     * Wait for some time
     */
    private void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException _) {

        }
    }
}
