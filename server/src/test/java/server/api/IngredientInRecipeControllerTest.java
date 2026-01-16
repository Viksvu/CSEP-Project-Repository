package server.api;

import commons.IngredientInRecipe;
import commons.Ingredients;
import commons.Recipes;
import commons.Unit;
import commons.request.AddIngredientInRecipeRequest;
import commons.request.DeleteIngredientInRecipeRequest;
import commons.request.EditIngredientInRecipeRequest;
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
        AddIngredientInRecipeRequest request = new AddIngredientInRecipeRequest(r1.getId(), iR);
        ResponseEntity<IngredientInRecipe> response = ic.add(request);
        assertEquals(OK, response.getStatusCode());

        ResponseEntity<List<IngredientInRecipe>> response2 = ic.get(r1.getId());
        assertEquals(OK, response2.getStatusCode());
        assertNotNull(response2.getBody());

        assertTrue(response2.getBody().contains(iR));
    }

    @Test
    void addNull() {
        AddIngredientInRecipeRequest request = new AddIngredientInRecipeRequest(r1.getId(), null);
        ResponseEntity<IngredientInRecipe> response = ic.add(request);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void addInvalidRecipe() {
        AddIngredientInRecipeRequest request = new AddIngredientInRecipeRequest(-2L, iR);
        ResponseEntity<IngredientInRecipe> response = ic.add(request);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void delete() {
        AddIngredientInRecipeRequest request = new AddIngredientInRecipeRequest(r1.getId(), iR);
        ResponseEntity<IngredientInRecipe> response = ic.add(request);
        assertEquals(OK, response.getStatusCode());

        DeleteIngredientInRecipeRequest request2 = new DeleteIngredientInRecipeRequest(r1.getId(), iR);
        response = ic.delete(request2);
        assertEquals(OK, response.getStatusCode());

        ResponseEntity<List<IngredientInRecipe>> response2 = ic.get(r1.getId());
        assertEquals(OK, response2.getStatusCode());
        assertNotNull(response2.getBody());

        assertFalse(response2.getBody().contains(iR));
    }

    @Test
    void deleteNull() {
        DeleteIngredientInRecipeRequest request = new DeleteIngredientInRecipeRequest(r1.getId(), null);
        ResponseEntity<IngredientInRecipe> response = ic.delete(request);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void editIngredientInRecipe_success() {
        Ingredients newIngredient = new Ingredients("Onion", 0, 0.0, 0.0, 0.0);

        Ingredients i = new Ingredients("Garlic", 0, 0.0, 0.0, 0.0);
        IngredientInRecipe existing = new IngredientInRecipe(i);
        existing.setQuantity(1);
        existing.setUnit(Unit.GRAM);
        existing.setId(1L);

        r1.getIngredients().add(existing);

        IngredientInRecipe edited = new IngredientInRecipe(newIngredient);
        edited.setId(1L);
        edited.setQuantity(5);
        edited.setUnit(Unit.TABLE_SPOON);

        EditIngredientInRecipeRequest request = new EditIngredientInRecipeRequest(r1.getId(), edited);
        ResponseEntity<IngredientInRecipe> response = ic.edit(request);
        assertEquals(OK, response.getStatusCode());

        IngredientInRecipe updated = r1.getIngredients().get(0);
        assertEquals(newIngredient, updated.getIngredient());
        assertEquals(5, updated.getQuantity());
        assertEquals(Unit.TABLE_SPOON, updated.getUnit());
    }


    @Test
    void deleteInvalidRecipe() {
        DeleteIngredientInRecipeRequest request = new DeleteIngredientInRecipeRequest(-2L, iR);
        ResponseEntity<IngredientInRecipe> response = ic.delete(request);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

}