package server.api;

import commons.IngredientInRecipe;
import commons.Ingredients;
import commons.Recipes;
import commons.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.services.TempRecipeService;

import java.util.ArrayList;
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
        ResponseEntity<List<IngredientInRecipe>> response = ic.get(-2);
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
        ResponseEntity<IngredientInRecipe> response = ic.add(-2, iR);
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
    void deleteInvalidRecipe() {
        ResponseEntity<IngredientInRecipe> response = ic.delete(-2, iR);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }
}