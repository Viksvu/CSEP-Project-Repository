package client;

import client.scenes.OverviewListCtrl;
import client.scenes.RecipeOverviewCtrl;
import client.scenes.ShoppingListCtrl;
import client.utils.ServerUtils;
import commons.IngredientInRecipe;
import commons.PreparationStep;
import commons.Recipes;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class EditButton<K> extends Button {
    final private K object;
    final private int index;
    final private ListView<K> parent;
    private ServerUtils server;
    final private Recipes recipe;
    private RecipeOverviewCtrl ctrl;
    private EditButtonOptions option;
    private OverviewListCtrl overviewListCtrl;
    private ShoppingListCtrl shoppingListCtrl;

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
                      EditButtonOptions option) {
        super(s);
        this.object = object;
        this.index = index;
        this.parent = parent;
        this.server = server;
        this.recipe = null;
        this.overviewListCtrl=ctrl;
        this.option=option;
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
