package client.commonsClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;


public class ShoppingList {
    /// The list containing the full shopping list ingredients.
    private final List<Ingredients> shoppingList;

    /// A buffer list that holds
    /// the ingredients in the overview (to be added) shopping list.
    private final List<Ingredients> bufferList;

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
    public void addIngredientDirectly(Ingredients tempIngredient) {
        shoppingList.add(tempIngredient);
    }

    /**
     * Removes an ingredient directly
     * from shopping list.
     *
     * @param tempIngredient the ingredient to remove.
     */
    public void removeIngredientDirectly(Ingredients tempIngredient) {
        shoppingList.remove(tempIngredient);
    }

    /**
     * Adds all the ingredients in a recipe
     * to the overview(buffer list).
     *
     * @param recipe the recipe.
     */
    public void addRecipeIngredientsToOverview(Recipes recipe) {
        List<Ingredients> ingredientsTemp =
                new ArrayList<>(recipe.getIngredients());
        bufferList.addAll(ingredientsTemp);
    }

    /**
     * Adds an ingredient directly
     * to buffer list.
     *
     * @param tempIngredient the ingredient to add.
     */
    public void
    addIngredientDirectlyToOverview(Ingredients tempIngredient) {
        bufferList.add(tempIngredient);
    }

    public List<Ingredients> getShoppingList() {
        return shoppingList;
    }

    public List<Ingredients> getBufferList() {
        return bufferList;
    }

    /**
     * Removes an ingredient directly
     * from buffer list.
     *
     * @param tempIngredient the ingredient to remove.
     */
    public void
    removeIngredientDirectlyFromOverview(Ingredients tempIngredient) {
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

        for (Ingredients ingredient : shoppingList) {
            if (ingredient instanceof IngredientInRecipe iir) {
                stringBuilder.append(iir.getQuantity())
                        .append(iir.getUnit().toString())
                        .append(" ")
                        .append(iir.getName())
                        .append(" ")
                        .append(iir.getRecipes().getName());
            } else {
                stringBuilder.append(ingredient.getName());
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
        for (Ingredients ingredient : shoppingList) {
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
