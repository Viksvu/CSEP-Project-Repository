package commons;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;


public class ShoppingList {
    /// The list containing the full shopping list ingredients.
    private final List<TempIngredient> shoppingList;

    /// A buffer list that holds
    /// the ingredients in the overview (to be added) shopping list.
    private final List<TempIngredient> bufferList;

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
    public void addIngredientDirectly(TempIngredient tempIngredient) {
        shoppingList.add(tempIngredient);
    }

    /**
     * Removes an ingredient directly
     * from shopping list.
     *
     * @param tempIngredient the ingredient to remove.
     */
    public void removeIngredientDirectly(TempIngredient tempIngredient) {
        shoppingList.remove(tempIngredient);
    }

    /**
     * Adds all the ingredients in a recipe
     * to the overview(buffer list).
     *
     * @param recipe the recipe.
     */
    public void addRecipeIngredientsToOverview(Recipes recipe) {
        List<TempIngredient> ingredientsTemp =
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
    addIngredientDirectlyFromOverview(TempIngredient tempIngredient) {
        bufferList.add(tempIngredient);
    }

    /**
     * Removes an ingredient directly
     * from buffer list.
     *
     * @param tempIngredient the ingredient to remove.
     */
    public void
    removeIngredientDirectlyFromOverview(TempIngredient tempIngredient) {
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

        for (TempIngredient ingredient : shoppingList) {
            if (ingredient instanceof IngredientInRecipe iir) {
                stringBuilder.append(iir.getQuantity())
                        .append(iir.getUnit().toString())
                        .append(" ")
                        .append(iir.name)
                        .append(" ")
                        .append(iir.getRecipes().getName());
            } else {
                stringBuilder.append(ingredient.name);
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
        for (TempIngredient ingredient : shoppingList) {
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
