package client;

import client.commonsClient.IngredientInShoppingList;
import client.commonsClient.ShoppingList;
import client.scenes.ShoppingListCtrl;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class EditButtonShoppingList extends Button {
    private final IngredientInShoppingList ingredient;
    private final int index;
    private final ListView<IngredientInShoppingList> parent;
    private EditButtonOptions option;
    private ShoppingListCtrl ctrl;
    private ShoppingList shoppingList;

    /**
     * Constructor for edit ingredient button
     *
     * @param ingredient to which this button is associated
     * @param s          for button text
     */
    public EditButtonShoppingList(IngredientInShoppingList ingredient,
                                  String s,
                                  int index,
                                  ListView<IngredientInShoppingList> parent,
                                   ShoppingListCtrl ctrl,
                                  ShoppingList shoppingList,
                                  EditButtonOptions option) {
        super(s);
        this.ingredient = ingredient;
        this.index = index;
        this.parent = parent;
        this.ctrl = ctrl;
        this.option = option;
        this.shoppingList = shoppingList;
        double containerHeight = parent.getLayoutBounds().getHeight();
        super.setTranslateY(containerHeight * index / 12);
        super.setTranslateX(
                8 * parent.getItems().get(index).toString().length()
        );
        editIngredientInShoppingList();
    }


    /**
     * currently just has an delete edit button
     */
    public void editIngredientInShoppingList() {
        this.setOnAction(event -> {
            if (this.option == EditButtonOptions.REMOVE_INGREDIENT) {
                shoppingList.getShoppingList().remove(ingredient);
                ctrl.refresh();
            }
        });
    }

}

