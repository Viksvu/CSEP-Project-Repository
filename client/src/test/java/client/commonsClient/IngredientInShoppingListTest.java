package client.commonsClient;

import commons.IngredientInRecipe;
import commons.Ingredients;
import commons.Recipes;
import commons.Unit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientInShoppingListTest {

    @Test
    void testToStringQuantityOne() {
        Ingredients i = new Ingredients("pepper", 50, 0.0,0.0,0.0);
        IngredientInShoppingList t = new IngredientInShoppingList(i, 34, Unit.GRAM);

        String result = t.toString();

        assertEquals("pepper (34 grams)", result);
    }
    @Test
    void testConstructor(){
        Ingredients i = new Ingredients("pepper", 50, 0.0,0.0,0.0);
        IngredientInShoppingList t = new IngredientInShoppingList(i, 34, Unit.TABLE_SPOON);
        assertEquals(i, t.getIngredient());
    }
    @Test
    void getIngredient() {

        IngredientInRecipe iir1 = new IngredientInRecipe();
        Ingredients i = new Ingredients("pepper", 50, 0.0,0.0,0.0);
        iir1.setIngredient(i);
        IngredientInShoppingList t = new IngredientInShoppingList(iir1);
        assertEquals(t.getIngredient(), i);
    }

    @Test
    void setIngredient() {
        IngredientInRecipe iir1 = new IngredientInRecipe();
        Ingredients i = new Ingredients("pepper", 50, 0.0,0.0,0.0);
        iir1.setIngredient(i);
        IngredientInShoppingList t = new IngredientInShoppingList(iir1);
        assertEquals(t.getIngredient(), i);
        Ingredients b = new Ingredients("salt", 50, 0.0,0.0,0.0);
        t.setIngredient(b);
        assertEquals(t.getIngredient(), b);

    }

    @Test
    void getQuantity() {
        IngredientInRecipe iir1 = new IngredientInRecipe();
        Ingredients i = new Ingredients("pepper", 50, 0.0,0.0,0.0);
        iir1.setIngredient(i);
        iir1.setQuantity(52);
        IngredientInShoppingList t = new IngredientInShoppingList(iir1);
        assertEquals(52, t.getQuantity());
    }

    @Test
    void setQuantity() {
        IngredientInRecipe iir1 = new IngredientInRecipe();
        Ingredients i = new Ingredients("pepper", 50, 0.0,0.0,0.0);
        iir1.setIngredient(i);
        IngredientInShoppingList t = new IngredientInShoppingList(iir1);
        t.setQuantity(52);

        assertEquals(52, t.getQuantity());
    }



    @Test
    void getRecipe() {
        IngredientInRecipe iir1 = new IngredientInRecipe();
        Ingredients i = new Ingredients("pepper", 50, 0.0,0.0,0.0);
        iir1.setIngredient(i);
        Recipes r = new Recipes();
        iir1.setRecipes(r);
        IngredientInShoppingList t = new IngredientInShoppingList(iir1);

        assertEquals(r, t.getRecipe());
    }

    @Test
    void setRecipe() {
        IngredientInRecipe iir1 = new IngredientInRecipe();
        Ingredients i = new Ingredients("pepper", 50, 0.0,0.0,0.0);
        iir1.setIngredient(i);
        Recipes r = new Recipes();
        IngredientInShoppingList t = new IngredientInShoppingList(iir1);
        t.setRecipe(r);
        assertEquals(r, t.getRecipe());
    }

    @Test
    void testEqualsSame() {
        IngredientInRecipe iir1 = new IngredientInRecipe();
        Ingredients i = new Ingredients("pepper", 50, 0.0,0.0,0.0);
        iir1.setIngredient(i);
        IngredientInShoppingList t1 = new IngredientInShoppingList(iir1);

        IngredientInShoppingList t2 = new IngredientInShoppingList(iir1);

        assertEquals(t1, t2);
    }

    @Test
    void testNotEqualsDifferent() {
        IngredientInRecipe iir1 = new IngredientInRecipe();
        IngredientInRecipe iir2 = new IngredientInRecipe();

        Ingredients i = new Ingredients("pepper", 50, 0.0,0.0,0.0);
        Ingredients i2 = new Ingredients("salt", 50, 0.0,0.0,0.0);

        iir1.setIngredient(i);
        iir2.setIngredient(i2);

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
        IngredientInRecipe iir1 = new IngredientInRecipe();
        Ingredients i = new Ingredients("pepper", 50, 0.0,0.0,0.0);
        iir1.setIngredient(i);
        IngredientInShoppingList t1 = new IngredientInShoppingList(iir1);

        IngredientInShoppingList t2 = new IngredientInShoppingList(iir1);

        assertEquals(t1.hashCode(), t2.hashCode());
    }
    @Test
    void testHashcodeDifferent() {
        IngredientInRecipe iir1 = new IngredientInRecipe();
        IngredientInRecipe iir2 = new IngredientInRecipe();

        Ingredients i = new Ingredients("pepper", 50, 0.0,0.0,0.0);
        Ingredients i2 = new Ingredients("salt", 50, 0.0,0.0,0.0);

        iir1.setIngredient(i);
        iir2.setIngredient(i2);

        IngredientInShoppingList t1 = new IngredientInShoppingList(iir1);

        IngredientInShoppingList t2 = new IngredientInShoppingList(iir2);

        assertNotEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void testToString() {

    }
}