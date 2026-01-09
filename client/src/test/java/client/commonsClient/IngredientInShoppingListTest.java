package client.commonsClient;

import commons.*;
import commons.Unit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientInShoppingListTest {

    @Test
    void testToStringQuantityOne() {
        Ingredients i = new Ingredients("pepper", 50, 0.0, 0.0, 0.0);
        IngredientInShoppingList t = new IngredientInShoppingList(i, 34, Unit.GRAM);

        String result = t.toString();
        assertEquals("pepper (34 grams)", result);
    }

    @Test
    void testConstructor() {
        Ingredients i = new Ingredients("pepper", 50, 0.0, 0.0, 0.0);
        IngredientInShoppingList t = new IngredientInShoppingList(i, 34, Unit.TABLE_SPOON);
        assertEquals(i, t.getIngredient());
    }

    @Test
    void getIngredient() {
        Ingredients i = new Ingredients("pepper", 50, 0.0, 0.0, 0.0);
        IngredientInRecipe iir1 = new IngredientInRecipe(i);
        iir1.setQuantity(52);
        iir1.setUnit(Unit.GRAM);

        IngredientInShoppingList t = new IngredientInShoppingList(iir1);
        assertEquals(i, t.getIngredient());
    }

    @Test
    void setIngredient() {
        Ingredients i = new Ingredients("pepper", 50, 0.0, 0.0, 0.0);
        IngredientInRecipe iir1 = new IngredientInRecipe(i);
        IngredientInShoppingList t = new IngredientInShoppingList(iir1);

        Ingredients b = new Ingredients("salt", 50, 0.0, 0.0, 0.0);
        t.setIngredient(b);
        assertEquals(b, t.getIngredient());
    }

    @Test
    void getQuantity() {
        Ingredients i = new Ingredients("pepper", 50, 0.0, 0.0, 0.0);
        IngredientInRecipe iir1 = new IngredientInRecipe(i);
        iir1.setQuantity(52);
        iir1.setUnit(Unit.GRAM);

        IngredientInShoppingList t = new IngredientInShoppingList(iir1);
        assertEquals(52, t.getQuantity());
    }

    @Test
    void setQuantity() {
        Ingredients i = new Ingredients("pepper", 50, 0.0, 0.0, 0.0);
        IngredientInRecipe iir1 = new IngredientInRecipe(i);
        IngredientInShoppingList t = new IngredientInShoppingList(iir1);

        t.setQuantity(52);
        assertEquals(52, t.getQuantity());
    }

    @Test
    void getRecipe() {
        Ingredients i = new Ingredients("pepper", 50, 0.0, 0.0, 0.0);
        IngredientInRecipe iir1 = new IngredientInRecipe(i);
        Recipes r = new Recipes();
        iir1.setRecipes(r);

        IngredientInShoppingList t = new IngredientInShoppingList(iir1);
        assertEquals(r, t.getRecipe());
    }

    @Test
    void setRecipe() {
        Ingredients i = new Ingredients("pepper", 50, 0.0, 0.0, 0.0);
        IngredientInRecipe iir1 = new IngredientInRecipe(i);
        IngredientInShoppingList t = new IngredientInShoppingList(iir1);

        Recipes r = new Recipes();
        t.setRecipe(r);
        assertEquals(r, t.getRecipe());
    }

    @Test
    void testEqualsSame() {
        Ingredients i = new Ingredients("pepper", 50, 0.0, 0.0, 0.0);
        IngredientInRecipe iir1 = new IngredientInRecipe(i);

        IngredientInShoppingList t1 = new IngredientInShoppingList(iir1);
        IngredientInShoppingList t2 = new IngredientInShoppingList(iir1);

        assertEquals(t1, t2);
    }

    @Test
    void testNotEqualsDifferent() {
        Ingredients i = new Ingredients("pepper", 50, 0.0, 0.0, 0.0);
        Ingredients i2 = new Ingredients("salt", 50, 0.0, 0.0, 0.0);

        IngredientInRecipe iir1 = new IngredientInRecipe(i);
        IngredientInRecipe iir2 = new IngredientInRecipe(i2);

        IngredientInShoppingList t1 = new IngredientInShoppingList(iir1);
        IngredientInShoppingList t2 = new IngredientInShoppingList(iir2);

        assertNotEquals(t1, t2);
    }

    @Test
    void testNotEqualsNull() {
        IngredientInShoppingList t1 = new IngredientInShoppingList();
        assertNotEquals(null, t1);
    }

    @Test
    void testHashCode() {
        Ingredients i = new Ingredients("pepper", 50, 0.0, 0.0, 0.0);
        IngredientInRecipe iir1 = new IngredientInRecipe(i);

        IngredientInShoppingList t1 = new IngredientInShoppingList(iir1);
        IngredientInShoppingList t2 = new IngredientInShoppingList(iir1);

        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void testHashcodeDifferent() {
        Ingredients i = new Ingredients("pepper", 50, 0.0, 0.0, 0.0);
        Ingredients i2 = new Ingredients("salt", 50, 0.0, 0.0, 0.0);

        IngredientInRecipe iir1 = new IngredientInRecipe(i);
        IngredientInRecipe iir2 = new IngredientInRecipe(i2);

        IngredientInShoppingList t1 = new IngredientInShoppingList(iir1);
        IngredientInShoppingList t2 = new IngredientInShoppingList(iir2);

        assertNotEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void testToString() {
        Ingredients i = new Ingredients("pepper", 50, 0.0, 0.0, 0.0);
        IngredientInShoppingList t = new IngredientInShoppingList(i, 1, Unit.GRAM);

        String result = t.toString();
        assertEquals("pepper (1 gram)", result);
    }
}
