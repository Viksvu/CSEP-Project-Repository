package server.services;

import commons.IngredientInRecipe;
import commons.Ingredients;
import commons.Recipes;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;
/**
 * Test class for RecipeService to verify saving recipes along with their ingredients.
 */
@SpringBootTest
@Transactional
public class RecipeServiceTest {
    @Autowired
    RecipeService recipeService;

    @Autowired
    IngredientsService ingredientsService;

    @Test
    void testSavingRecipe() {
        Recipes recipe = new Recipes("Cake");
        IngredientInRecipe ingredient1 = new IngredientInRecipe();
        Ingredients ing1 = new Ingredients("Flour", 10);
        ingredient1.setIngredient(ing1);
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
        assertEquals(1, count);
    }
}
