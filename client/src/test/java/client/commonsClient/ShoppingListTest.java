package client.commonsClient;

import commons.*;
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
        Ingredients i1 = new Ingredients("Flour");
        Ingredients i2 = new Ingredients("Sugar");

        IngredientInRecipe ing1 = new IngredientInRecipe(i1);
        ing1.setQuantity(100);
        ing1.setUnit(Unit.GRAM);

        IngredientInRecipe ing2 = new IngredientInRecipe(i2);
        ing2.setQuantity(50);
        ing2.setUnit(Unit.GRAM);

        recipe.addIngredient(ing1);
        recipe.addIngredient(ing2);

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
        Ingredients i1 = new Ingredients("Flour");
        Ingredients i2 = new Ingredients("Sugar");
        Ingredients i3 = new Ingredients("Milk");

        IngredientInRecipe ing1 = new IngredientInRecipe(i1);
        ing1.setQuantity(100);
        ing1.setUnit(Unit.GRAM);

        IngredientInRecipe ing2 = new IngredientInRecipe(i2);
        ing2.setQuantity(50);
        ing2.setUnit(Unit.GRAM);

        IngredientInRecipe ing3 = new IngredientInRecipe(i3);
        ing3.setQuantity(200);
        ing3.setUnit(Unit.MILLILITER);

        recipe.addIngredient(ing1);
        recipe.addIngredient(ing2);
        recipe.addIngredient(ing3);

        ShoppingList sl = new ShoppingList();
        sl.addRecipeIngredientsToOverview(recipe);
        sl.addOverviewToShoppingList();
        assertEquals(3, sl.getShoppingList().size());
    }

    @Test
    void resetShoppingList() {
        Recipes recipe = new Recipes();
        Ingredients i1 = new Ingredients("Flour");

        IngredientInRecipe ing1 = new IngredientInRecipe(i1);
        ing1.setQuantity(100);
        ing1.setUnit(Unit.GRAM);
        recipe.addIngredient(ing1);

        ShoppingList sl = new ShoppingList();
        sl.addRecipeIngredientsToOverview(recipe);
        sl.addOverviewToShoppingList();
        assertEquals(1, sl.getShoppingList().size());

        sl.resetShoppingList();
        assertEquals(0, sl.getShoppingList().size());
    }

    @Test
    void printableShoppingList() {
        Recipes recipe = new Recipes("Bolognese");
        Ingredients i = new Ingredients("pepper", 50, 5.0, 10.0, 200.0);

        IngredientInRecipe iir1 = new IngredientInRecipe(i);
        iir1.setRecipes(recipe);
        iir1.setQuantity(2);
        iir1.setUnit(Unit.TABLE_SPOON);

        recipe.addIngredient(iir1);

        ShoppingList sl = new ShoppingList();
        sl.addRecipeIngredientsToOverview(recipe);
        sl.addOverviewToShoppingList();

        assertEquals("2table spoon/s pepper Bolognese\n", sl.printableShoppingList());
    }

    @Test
    void testEqualsSameList() {
        ShoppingList sl = new ShoppingList();
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
        String s2 = "yayaa";
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
