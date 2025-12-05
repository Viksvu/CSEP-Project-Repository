package client.commonsClient;

import commons.IngredientInRecipe;
import commons.Recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;


public class ShoppingList {
    /// The list containing the full shopping list ingredients.
    private final List<IngredientInShoppingList> shoppingList;

    /// A buffer list that holds
    /// the ingredients in the overview (to be added) shopping list.
    private final List<IngredientInShoppingList> bufferList;

    /**
     * A zero-argument constructor
     * that initializes the shoppingList list and
     * the bufferList list.
     */
    public ShoppingList() {
        shoppingList = new ArrayList<>();
        bufferList = new ArrayList<>();
    }

    /**
     * Adds an ingredient directly
     * to shopping list.
     *
     * @param tempIngredient the ingredient to add.
     */
    public void addIngredientDirectly(IngredientInShoppingList tempIngredient) {
        shoppingList.add(tempIngredient);
    }

    /**
     * Removes an ingredient directly
     * from shopping list.
     *
     * @param tempIngredient the ingredient to remove.
     */
    public void removeIngredientDirectly(IngredientInShoppingList
                                                 tempIngredient) {
        shoppingList.remove(tempIngredient);
    }

    /**
     * Adds all the ingredients in a recipe
     * to the overview(buffer list).
     *
     * @param recipe the recipe.
     */
    public void addRecipeIngredientsToOverview(Recipes recipe) {
        List<IngredientInShoppingList> ingredientsTemp =
                new ArrayList<>();
        for (IngredientInRecipe iir : recipe.getIngredients()) {
            ingredientsTemp.add(new IngredientInShoppingList(iir));
        }
        bufferList.addAll(ingredientsTemp);
    }

    /**
     * Adds an ingredient directly
     * to buffer list.
     *
     * @param tempIngredient the ingredient to add.
     */
    public void
    addIngredientDirectlyToOverview(IngredientInShoppingList tempIngredient) {
        bufferList.add(tempIngredient);
    }

    public List<IngredientInShoppingList> getShoppingList() {
        return shoppingList;
    }

    public List<IngredientInShoppingList> getBufferList() {
        return bufferList;
    }

    /**
     * Removes an ingredient directly
     * from buffer list.
     *
     * @param tempIngredient the ingredient to remove.
     */
    public void
    removeIngredientDirectlyFromOverview(IngredientInShoppingList
                                                 tempIngredient) {
        bufferList.remove(tempIngredient);
    }

    /**
     * Add the overview
     * to the shopping list and clear
     * the buffer list (overview).
     */
    public void addOverviewToShoppingList() {
        shoppingList.addAll(bufferList);
        bufferList.clear();
    }

    /**
     * Reset the shopping list,
     * clear its contents.
     */
    public void resetShoppingList() {
        shoppingList.clear();
    }

    /**
     * Provides a readable format for the shopping list.
     * This will be refactored after every other class
     * implementation of toString is provided.
     * (The name of ingredient is not private)
     *
     * @return the readable string.
     */
    public String printableShoppingList() {
        StringBuilder stringBuilder = new StringBuilder();

        for (IngredientInShoppingList ingredient : shoppingList) {
            if (ingredient.getRecipe() != null) {
                stringBuilder.append(ingredient.getQuantity())
                        .append(ingredient.getIngredient().getUnit().toString())
                        .append(" ")
                        .append(ingredient.getIngredient().getName())
                        .append(" ")
                        .append(ingredient.getRecipe().getName());
            } else {
                stringBuilder.append(ingredient.getIngredient().getName());
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Provides a readable format for the overview list.
     *
     * @return the string of the said format.
     */
    public String printableOverviewList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (IngredientInShoppingList ingredient : shoppingList) {
            /// stringBuilder.append(((IngredientInRecipe)
            ///ingredient).toString)

        }
        return stringBuilder.toString();
    }


    /**
     * Saves to file the current
     * shopping list.
     *
     * @param filePath the file path, where to save.
     */
    public void saveToFile(String filePath) {
        String content =
                printableShoppingList();
        try {
            Files.writeString(Paths.get(filePath), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null
                || getClass() != o.getClass()) return false;
        ShoppingList that = (ShoppingList) o;
        return Objects.equals(shoppingList, that.shoppingList);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(shoppingList);
        result *= 21;
        return result;
    }


}
