package client.commonsClient;

import commons.IngredientInRecipe;
import commons.Ingredients;
import commons.Recipes;
import commons.Unit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingListTest {


    @Test
    void testConstructor() {
        ShoppingList sl = new ShoppingList();
        assertNotNull(sl);
        assertNotNull(sl.getShoppingList());
        assertNotNull(sl.getBufferList());
    }

    @Test
    void addIngredientDirectly() {
        ShoppingList sl = new ShoppingList();
        IngredientInShoppingList t = new IngredientInShoppingList();
        sl.addIngredientDirectly(t);
        assertEquals(1, sl.getShoppingList().size());
    }

    @Test
    void removeIngredientDirectly() {
        ShoppingList sl = new ShoppingList();
        IngredientInShoppingList t = new IngredientInShoppingList();
        sl.addIngredientDirectly(t);
        assertEquals(1, sl.getShoppingList().size());
        sl.removeIngredientDirectly(t);
        assertEquals(0, sl.getShoppingList().size());
    }

    @Test
    void addRecipeIngredientInShoppingListToOverview() {
        Recipes recipe = new Recipes();
        IngredientInRecipe iir1 = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());
        IngredientInRecipe iir2 = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());
        recipe.addIngredient(iir1);
        recipe.addIngredient(iir2);
        ShoppingList sl = new ShoppingList();
        sl.addRecipeIngredientsToOverview(recipe);
        assertEquals(2, sl.getBufferList().size());
    }

    @Test
    void addIngredientDirectlyToOverview() {
        ShoppingList sl = new ShoppingList();
        IngredientInShoppingList t = new IngredientInShoppingList();
        sl.addIngredientDirectlyToOverview(t);
        assertEquals(1, sl.getBufferList().size());
    }

    @Test
    void removeIngredientDirectlyFromOverview() {
        ShoppingList sl = new ShoppingList();
        IngredientInShoppingList t = new IngredientInShoppingList();
        sl.addIngredientDirectlyToOverview(t);
        assertEquals(1, sl.getBufferList().size());
        sl.removeIngredientDirectlyFromOverview(t);
        assertEquals(0, sl.getBufferList().size());
    }

    @Test
    void addOverviewToShoppingList() {
        Recipes recipe = new Recipes();
        IngredientInRecipe iir1 = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());
        IngredientInRecipe iir2 = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());
        IngredientInRecipe iir3 = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());
        recipe.addIngredient(iir1);
        recipe.addIngredient(iir2);
        recipe.addIngredient(iir3);
        ShoppingList sl = new ShoppingList();
        sl.addRecipeIngredientsToOverview(recipe);
        sl.addOverviewToShoppingList();
        assertEquals(3,sl.getShoppingList().size());
    }

    @Test
    void resetShoppingList() {
        Recipes recipe = new Recipes();
        IngredientInRecipe iir1 = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());
        recipe.addIngredient(iir1);
        ShoppingList sl = new ShoppingList();
        sl.addRecipeIngredientsToOverview(recipe);
        sl.addOverviewToShoppingList();
        assertEquals(1,sl.getShoppingList().size());
        sl.resetShoppingList();
        assertEquals(0,sl.getShoppingList().size());

    }

    @Test
    void printableShoppingList() {
        Recipes recipe = new Recipes("bologness");
        IngredientInRecipe iir1 = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());
        Ingredients i = new Ingredients("pepper", 50, 5.0, 10.0, 200.0);
        iir1.setIngredient(i);
        iir1.setRecipes(recipe);
        iir1.setQuantity(2);
        iir1.setUnit(Unit.TABLE_SPOON);
        iir1.getIngredient().setName("Matt");
        recipe.addIngredient(iir1);
        ShoppingList sl = new ShoppingList();
        sl.addRecipeIngredientsToOverview(recipe);
        sl.addOverviewToShoppingList();
        assertEquals("2table spoon/s Matt bologness\n", sl.printableShoppingList());

    }

    @Test
    void printableOverviewList() {
    }

    @Test
    void testEqualsSameList() {
        ShoppingList sl = new ShoppingList();
        assertNotNull(sl);
        assertEquals(sl, sl);
    }

    @Test
    void testEqualsIdenticalList() {
        ShoppingList sl1 = new ShoppingList();
        ShoppingList sl2 = new ShoppingList();
        assertEquals(sl1, sl2);
    }

    @Test
    void testNotEqualsDifferentClass() {
        ShoppingList sl1 = new ShoppingList();
        String s2= "yayaa";
        assertNotEquals(sl1, s2);
    }

    @Test
    void testHashCode() {
        ShoppingList sl1 = new ShoppingList();
        ShoppingList sl2 = new ShoppingList();
        assertEquals(sl1.hashCode(), sl2.hashCode());
        assertEquals(sl1.hashCode(), sl1.hashCode());
    }
}