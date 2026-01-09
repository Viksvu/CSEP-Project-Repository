package server.api;

import commons.IngredientInRecipe;
import commons.Ingredients;
import commons.Recipes;
import commons.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Disabled
class IngredientInRecipeControllerTest {

    private IngredientInRecipeController ic;
    private Recipes r1;
    private Ingredients i;
    private IngredientInRecipe iR;

    @BeforeEach
    void setUp() {
//        TempRecipeService tc = TempRecipeService.get();
//        r1 = new Recipes(1000000, new ArrayList<>(), new ArrayList<>(), "Spaghetti");
//        if (tc.getRecipeById(r1.getId()) == null) tc.addRecipe(r1);
//
//        ic = new IngredientInRecipeController();
//
//        i = new Ingredients("Tomato", 1);
//
//        iR = new IngredientInRecipe();
//        iR.setRecipes(r1);
//        iR.setQuantity(1);
//        iR.setIngredient(i);
//        iR.setUnit(Unit.GRAM);
    }

    @Test
    void getInvalid() {
        ResponseEntity<List<IngredientInRecipe>> response = ic.get(-2L);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void add() {
        ResponseEntity<IngredientInRecipe> response = ic.add(r1.getId(), iR);
        assertEquals(OK, response.getStatusCode());

        ResponseEntity<List<IngredientInRecipe>> response2 = ic.get(r1.getId());
        assertEquals(OK, response2.getStatusCode());
        assertNotNull(response2.getBody());

        assertTrue(response2.getBody().contains(iR));
    }

    @Test
    void addNull() {
        ResponseEntity<IngredientInRecipe> response = ic.add(r1.getId(), null);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void addInvalidRecipe() {
        ResponseEntity<IngredientInRecipe> response = ic.add(-2L, iR);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void delete() {
        ResponseEntity<IngredientInRecipe> response = ic.add(r1.getId(), iR);
        assertEquals(OK, response.getStatusCode());

        response = ic.delete(r1.getId(), iR);
        assertEquals(OK, response.getStatusCode());

        ResponseEntity<List<IngredientInRecipe>> response2 = ic.get(r1.getId());
        assertEquals(OK, response2.getStatusCode());
        assertNotNull(response2.getBody());

        assertFalse(response2.getBody().contains(iR));
    }

    @Test
    void deleteNull() {
        ResponseEntity<IngredientInRecipe> response = ic.delete(r1.getId(), null);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void editIngredientInRecipe_success() {
        Ingredients newIngredient = new Ingredients("Onion", 0,
                0.0, 0.0,0.0);

        IngredientInRecipe existing = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());
        existing.setIngredient(i);
        existing.setQuantity(1);
        existing.setUnit(Unit.GRAM);
        r1.getIngredients().add(existing);
        existing.setId(1L);
        IngredientInRecipe edited = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());
        edited.setId(1L);
        edited.setIngredient(newIngredient);
        edited.setQuantity(5);
        edited.setUnit(Unit.TABLE_SPOON);
        ResponseEntity<IngredientInRecipe> response =
                ic.edit(r1.getId(), edited);
        assertEquals(OK, response.getStatusCode());
        IngredientInRecipe updated =
                r1.getIngredients().get(0);

        assertEquals(newIngredient, updated.getIngredient());
        assertEquals(5, updated.getQuantity());
        assertEquals(Unit.TABLE_SPOON, updated.getUnit());
    }

    @Test
    void deleteInvalidRecipe() {
        ResponseEntity<IngredientInRecipe> response = ic.delete( -2L, iR);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

}