package TestClientCommons;

import client.commonsClient.Ingredients;
import client.commonsClient.Recipes;
import client.commonsClient.IngredientInRecipe;
import client.commonsClient.Unit;
import client.commonsClient.ShoppingList;
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
        Ingredients t = new Ingredients();
        sl.addIngredientDirectly(t);
        assertEquals(1, sl.getShoppingList().size());
    }

    @Test
    void removeIngredientDirectly() {
        ShoppingList sl = new ShoppingList();
        Ingredients t = new Ingredients();
        sl.addIngredientDirectly(t);
        assertEquals(1, sl.getShoppingList().size());
        sl.removeIngredientDirectly(t);
        assertEquals(0, sl.getShoppingList().size());
    }

    @Test
    void addRecipeIngredientsToOverview() {
        Recipes recipe = new Recipes();
        IngredientInRecipe iir1 = new IngredientInRecipe();
        IngredientInRecipe iir2 = new IngredientInRecipe();
        recipe.addIngredient(iir1);
        recipe.addIngredient(iir2);
        ShoppingList sl = new ShoppingList();
        sl.addRecipeIngredientsToOverview(recipe);
        assertEquals(2, sl.getBufferList().size());
    }

    @Test
    void addIngredientDirectlyToOverview() {
        ShoppingList sl = new ShoppingList();
        Ingredients t = new Ingredients();
        sl.addIngredientDirectlyToOverview(t);
        assertEquals(1, sl.getBufferList().size());
    }

    @Test
    void removeIngredientDirectlyFromOverview() {
        ShoppingList sl = new ShoppingList();
        Ingredients t = new Ingredients();
        sl.addIngredientDirectlyToOverview(t);
        assertEquals(1, sl.getBufferList().size());
        sl.removeIngredientDirectlyFromOverview(t);
        assertEquals(0, sl.getBufferList().size());
    }

    @Test
    void addOverviewToShoppingList() {
        Recipes recipe = new Recipes();
        IngredientInRecipe iir1 = new IngredientInRecipe();
        IngredientInRecipe iir2 = new IngredientInRecipe();
        IngredientInRecipe iir3 = new IngredientInRecipe();
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
        IngredientInRecipe iir1 = new IngredientInRecipe();
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
        IngredientInRecipe iir1 = new IngredientInRecipe();
        iir1.setRecipes(recipe);
        iir1.setQuantity(2);
        iir1.setUnit(Unit.GRAM);
        iir1.setName("Matt");
        recipe.addIngredient(iir1);
        ShoppingList sl = new ShoppingList();
        sl.addRecipeIngredientsToOverview(recipe);
        sl.addOverviewToShoppingList();
        assertEquals("2gram/s Matt bologness\n", sl.printableShoppingList());

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