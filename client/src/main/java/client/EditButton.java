package client;

import client.scenes.RecipeOverviewCtrl;
import client.utils.ServerUtils;
import commons.IngredientInRecipe;
import commons.PreparationStep;
import commons.Recipes;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class EditButton<K> extends Button {
    private final K object;
    private final int index;
    private final ListView<K> parent;
    private ServerUtils server;
    private final Recipes recipe;
    private RecipeOverviewCtrl ctrl;
    private EditButtonOptions option;

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
        super.setTranslateY(containerHeight*index/7.0);
        super.setTranslateX(
                8 * parent.getItems().get(index).toString().length()
        );
        editIngredientInRecipe();
    }


    /**
     * Actually allows to edit by creating a new
     */
    public void editIngredientInRecipe() {
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
