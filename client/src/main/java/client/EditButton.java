package client;

import client.scenes.RecipeOverviewCtrl;
import client.utils.ServerUtils;
import commons.IngredientInRecipe;
import commons.Recipes;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class EditButton extends Button {
    private final IngredientInRecipe ingredient;
    private final int index;
    private final ListView<IngredientInRecipe> parent;
    private ServerUtils server;
    private final Recipes recipe;
    private RecipeOverviewCtrl ctrl;
    private EditButtonOptions option;
    /**
     * Constructor for edit ingredient button
     * @param ingredient to which this button is associated
     * @param s for button text
     */
    public EditButton(IngredientInRecipe ingredient,
                      String s, int index, ListView<IngredientInRecipe> parent,
                      ServerUtils server, Recipes recipe, RecipeOverviewCtrl ctrl,
                      EditButtonOptions option) {
        super(s);
        this.ingredient = ingredient;
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
        editIngredient();
    }

    /**
     * Actually allows to edit by creating a new
     */
    public void editIngredient() {
        this.setOnAction(event -> {
            if (this.option==EditButtonOptions.REMOVE_INGREDIENT) {
                server.removeIngredientFromRecipe(ingredient, recipe);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {

                }
                ctrl.refresh();
            }
        });
    }
}
