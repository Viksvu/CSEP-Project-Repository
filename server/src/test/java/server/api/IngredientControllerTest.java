package server.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import server.temp.Ingredient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class IngredientControllerTest {

    private IngredientController ic;
    private Ingredient i1;
    private Ingredient i2;

    @BeforeEach
    public void setup() {
//        ic = new IngredientController();
        i1 = new Ingredient(0, "Salt");
        i2 = new Ingredient(1, "Pepperoni");
    }

    @Test
    public void addNullIngredient() {
//        ResponseEntity<Ingredient> actual = ic.add(null);
//        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void checkAdd() {
//        ic.add(i1);
//        ic.add(i2);
//        assertTrue(ic.getAll().containsAll(Arrays.asList(i1, i2)));
    }

    @Test
    public void checkAddIngredientNameNull() {
//        ResponseEntity<Ingredient> response = ic.add(new Ingredient(-1, null));
//        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void checkNullIngredientName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        assertFalse(isValidName(null));
    }

    @Test
    public void checkEmptyIngredientName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        assertFalse(isValidName(""));
    }

    @Test
    public void checkGoodIngredientName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        assertTrue(isValidName("Spaghetti"));
    }

    @Test
    public void checkIngredientExists() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
//        assertFalse(ingredientExists(i1.getId()));
//        ic.add(i1);
//        assertTrue(ingredientExists(i1.getId()));
    }

    @Test
    public void checkAutoNewID() {
//        Ingredient newIngredient = new Ingredient(-1, "Wraps");
//
//        ResponseEntity<Ingredient> response = ic.add(newIngredient);
//        Ingredient ingredient = response.getBody();
//
//        assertEquals(OK, response.getStatusCode());
//        assertNotNull(ingredient);
//
//        assertNotEquals(newIngredient.getId(), ingredient.getId());
    }

    @Test
    public void checkAutoNewIDWithNonEmptyIngredientList() {
//        ic.add(i2);
//        ic.add(i1);
//
//        Ingredient newIngredient = new Ingredient(-1, "Milk");
//
//        ResponseEntity<Ingredient> response = ic.add(newIngredient);
//        Ingredient ingredient = response.getBody();
//
//        assertEquals(OK, response.getStatusCode());
//        assertNotNull(ingredient);
//
//        assertNotEquals(newIngredient.getId(), ingredient.getId());
    }

    @Test
    public void checkRemove() {
//        ic.add(i1);
//
//        ResponseEntity<Ingredient> response = ic.remove(i1);
//        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void checkRemoveWithMultipleEntries() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
//        ic.add(i1);
//        ic.add(i2);
//
//        ResponseEntity<Ingredient> response = ic.remove(i1);
//        assertEquals(OK, response.getStatusCode());
//
//        assertTrue(ingredientExists(i2.getId()));
    }

    @Test
    public void checkRemoveNull() {
//        ResponseEntity<Ingredient> response = ic.remove(null);
//        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void checkRemoveNonExisting() {
//        ResponseEntity<Ingredient> response = ic.remove(i1);
//        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Get the private method isValidName for testing
     * @param name input for isValidName method of IngredientController
     * @return true, if valid name
     */
    private boolean isValidName(String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = ic.getClass().getDeclaredMethod("isValidName", String.class);
        m.setAccessible(true);
        return (boolean) m.invoke(ic, name);
    }

    /**
     * Get the private method ingredientExists for testing
     * @param ingredientID input for ingredientExists method of IngredientController
     * @return true, if ingredient with id exists
     */
    private boolean ingredientExists(int ingredientID) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = ic.getClass().getDeclaredMethod("ingredientExists", int.class);
        m.setAccessible(true);
        return (boolean) m.invoke(ic, ingredientID);
    }
}
