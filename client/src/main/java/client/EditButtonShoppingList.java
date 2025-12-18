package client;

import client.commonsClient.IngredientInShoppingList;
import client.commonsClient.ShoppingList;
import client.scenes.OverviewListCtrl;
import client.scenes.ShoppingListCtrl;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class EditButtonShoppingList extends Button {
    private final IngredientInShoppingList ingredient;
    private final int index;
    private final ListView<IngredientInShoppingList> parent;
    private EditButtonOptions option;
    private Initializable ctrl;
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
                                  Initializable ctrl,
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
        if (ctrl instanceof ShoppingListCtrl) {
            editIngredientInShoppingList();
        } else if (ctrl instanceof OverviewListCtrl) {
            editIngredientInOverviewList();
        }
    }


    /**
     * currently just has a delete edit button
     */
    public void editIngredientInShoppingList() {
        this.setOnAction(event -> {
            if (this.option == EditButtonOptions.REMOVE_INGREDIENT) {
                shoppingList.getShoppingList().remove(ingredient);
                ((ShoppingListCtrl) ctrl).refresh();
            }
        });
    }

    /**
     * Currently has an edit and delete button
     */
    public void editIngredientInOverviewList() {
        this.setOnAction(event -> {
            if (this.option == EditButtonOptions.REMOVE_INGREDIENT) {
                shoppingList.getBufferList().remove(ingredient);
                ((OverviewListCtrl) ctrl).refresh();
            }
            else if(this.option == EditButtonOptions.EDIT_INGREDIENT){
                ((OverviewListCtrl) ctrl).editIngredient(ingredient);
                ((OverviewListCtrl) ctrl).refresh();
            }
        });
    }

}

