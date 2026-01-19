package server.services;

import commons.IngredientInRecipe;
import commons.Ingredients;
import commons.Recipes;
import commons.Unit;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import server.Main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * Test class for RecipeService to verify saving recipes along with their ingredients.
 */
@Transactional
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Main.class)
@ActiveProfiles("test")
@TestPropertySource(
        locations = "classpath:application-junit.properties")
public class RecipeServiceTest {
    @Autowired
    RecipeService recipeService;

    @Autowired
    IngredientsService ingredientsService;

    @Test
    void testSavingRecipe() {
        Recipes recipe = new Recipes("Cake");

        Ingredients ing1 = new Ingredients("Flour", 10, 0.0, 76.0, 364.0);
        IngredientInRecipe ingredient1 = new IngredientInRecipe(ing1);
        ingredient1.setQuantity(10);
        ingredient1.setUnit(Unit.GRAM);

        recipe.addIngredient(ingredient1);

        Recipes savedRecipe = recipeService.addRecipe(recipe);
        Recipes getRecipe = recipeService.getRecipeById(savedRecipe.getId());

        assertEquals("Cake", getRecipe.getName());
        assertEquals(1, getRecipe.getIngredients().size());
        assertEquals("Flour", getRecipe.getIngredients().getFirst().getIngredient().getName());

        int count = 0;
        for (Ingredients ing : ingredientsService.getAllIngredients()) {
            count++;
        }
        assertTrue(count >= 1);
    }
}
