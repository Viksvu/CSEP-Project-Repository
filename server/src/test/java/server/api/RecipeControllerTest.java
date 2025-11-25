package server.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.temp.Recipe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

public class RecipeControllerTest {

    private RecipeController rc;
    private Recipe r1;
    private Recipe r2;

    @BeforeEach
    public void setup() {
        rc = new RecipeController();
        r1 = new Recipe(0, "Spaghetti");
        r2 = new Recipe(1, "Pasta");
    }

    @Test
    public void addNullRecipe() {
        ResponseEntity<Recipe> actual = rc.add(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void checkAdd() {
        rc.add(r1);
        rc.add(r2);
        assertTrue(rc.getAll().containsAll(Arrays.asList(r1, r2)));
    }

    @Test
    public void checkAddRecipeNameNull() {
        ResponseEntity<Recipe> response = rc.add(new Recipe(-1, null));
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void checkNullRecipeName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        assertFalse(isValidName(null));
    }

    @Test
    public void checkEmptyRecipeName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        assertFalse(isValidName(""));
    }

    @Test
    public void checkGoodRecipeName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        assertTrue(isValidName("Spaghetti"));
    }

    @Test
    public void checkRecipeExists() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        assertFalse(recipeExists(r1.getId()));
        rc.add(r1);
        assertTrue(recipeExists(r1.getId()));
    }

    @Test
    public void checkAutoNewID() {
        Recipe newRecipe = new Recipe(-1, "Wraps");

        ResponseEntity<Recipe> response = rc.add(newRecipe);
        Recipe recipe = response.getBody();

        assertEquals(OK, response.getStatusCode());
        assertNotNull(recipe);

        assertNotEquals(newRecipe.getId(), recipe.getId());
    }

    @Test
    public void checkAutoNewIDWithNonEmptyRecipeList() {
        rc.add(r2);
        rc.add(r1);

        Recipe newRecipe = new Recipe(-1, "Wraps");

        ResponseEntity<Recipe> response = rc.add(newRecipe);
        Recipe recipe = response.getBody();

        assertEquals(OK, response.getStatusCode());
        assertNotNull(recipe);

        assertNotEquals(newRecipe.getId(), recipe.getId());
    }

    @Test
    public void checkRemove() {
        rc.add(r1);

        ResponseEntity<Recipe> response = rc.remove(r1);
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void checkRemoveWithMultipleEntries() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        rc.add(r1);
        rc.add(r2);

        ResponseEntity<Recipe> response = rc.remove(r1);
        assertEquals(OK, response.getStatusCode());

        assertTrue(recipeExists(r2.getId()));
    }

    @Test
    public void checkRemoveNull() {
        ResponseEntity<Recipe> response = rc.remove(null);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void checkRemoveNonExisting() {
        ResponseEntity<Recipe> response = rc.remove(r1);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Get the private method isValidName for testing
     * @param name input for isValidName method of RecipeController
     * @return true, if valid name
     */
    private boolean isValidName(String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = rc.getClass().getDeclaredMethod("isValidName", String.class);
        m.setAccessible(true);
        return (boolean) m.invoke(rc, name);
    }

    /**
     * Get the private method recipeExists for testing
     * @param recipeID input for recipeExists method of RecipeController
     * @return true, if recipe with id exists
     */
    private boolean recipeExists(int recipeID) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = rc.getClass().getDeclaredMethod("recipeExists", int.class);
        m.setAccessible(true);
        return (boolean) m.invoke(rc, recipeID);
    }
}