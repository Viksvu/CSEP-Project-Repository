package server.api;

import commons.Recipes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Disabled
public class RecipeControllerTest {

    private RecipeController rc;
    private Recipes r1;
    private Recipes r2;

    @BeforeEach
    public void setup() {
//        rc = new RecipeController();
//        r1 = new Recipes(0, new ArrayList<>(), new ArrayList<>(), "Spaghetti");
//        r2 = new Recipes(1, new ArrayList<>(), new ArrayList<>(), "Pasta");
    }

    @Test
    public void addNullRecipe() {
        ResponseEntity<Recipes> actual = rc.add(null);
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
//        ResponseEntity<Recipes> response = rc.add(new Recipes(-1, null, null, null));
//        assertEquals(BAD_REQUEST, response.getStatusCode());
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
        rc.remove(r1);
    }

    @Test
    public void checkAutoNewID() {
//        Recipes newRecipe = new Recipes(-1, new ArrayList<>(), new ArrayList<>(), "Wraps");
//
//        ResponseEntity<Recipes> response = rc.add(newRecipe);
//        Recipes recipe = response.getBody();
//
//        assertEquals(OK, response.getStatusCode());
//        assertNotNull(recipe);
//
//        assertNotEquals(newRecipe.getId(), recipe.getId());
//
//        rc.remove(newRecipe);
    }

    @Test
    public void checkAutoNewIDWithNonEmptyRecipeList() {
//        rc.add(r2);
//        rc.add(r1);
//
//        Recipes newRecipe = new Recipes(-1, new ArrayList<>(), new ArrayList<>(), "Wraps");
//
//        ResponseEntity<Recipes> response = rc.add(newRecipe);
//        Recipes recipe = response.getBody();
//
//        assertEquals(OK, response.getStatusCode());
//        assertNotNull(recipe);
//
//        assertNotEquals(newRecipe.getId(), recipe.getId());
//
//        rc.remove(r1);
//        rc.remove(r2);
    }

    @Test
    public void checkRemove() {
        rc.add(r1);

        ResponseEntity<Recipes> response = rc.remove(r1);
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void checkRemoveWithMultipleEntries() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        rc.add(r1);
        rc.add(r2);

        ResponseEntity<Recipes> response = rc.remove(r1);
        assertEquals(OK, response.getStatusCode());

        assertTrue(recipeExists(r2.getId()));

        rc.remove(r2);
    }

    @Test
    public void checkRemoveWithMultipleEntries2() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        rc.add(r1);
        rc.add(r2);

        ResponseEntity<Recipes> response = rc.remove(r1);
        assertEquals(OK, response.getStatusCode());

        assertTrue(recipeExists(r2.getId()));
        assertFalse(recipeExists(r1.getId()));

        rc.remove(r2);
    }

    @Test
    public void checkRemoveNull() {
        ResponseEntity<Recipes> response = rc.remove(null);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void checkRemoveNonExisting() {
        ResponseEntity<Recipes> response = rc.remove(r1);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void checkRename() {
//        rc.add(r1);
//        ResponseEntity<String> response = rc.rename(0, "Hello World");
//        assertEquals(OK, response.getStatusCode());
//        assertEquals("Hello World", response.getBody());
//        rc.remove(r1);
    }

    @Test
    public void checkRenameNull() {
//        rc.add(r1);
//        ResponseEntity<String> response = rc.rename(0, null);
//        assertEquals(BAD_REQUEST, response.getStatusCode());
//        rc.remove(r1);
    }

    @Test
    public void checkRenameEmpty() {
//        rc.add(r1);
//        ResponseEntity<String> response = rc.rename(0, "");
//        assertEquals(BAD_REQUEST, response.getStatusCode());
//        rc.remove(r1);
    }

    @Test
    public void checkRenameNonExistingRecipe() {
//        ResponseEntity<String> response = rc.rename(0, "Hello World");
//        assertEquals(BAD_REQUEST, response.getStatusCode());
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
    private boolean recipeExists(long recipeID) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = rc.getClass().getDeclaredMethod("recipeExists", long.class);
        m.setAccessible(true);
        return (boolean) m.invoke(rc, recipeID);
    }
}