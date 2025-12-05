package server.services;

import commons.IngredientInRecipe;
import commons.Ingredients;
import commons.Recipes;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import server.database.IngredientInRecipeRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for IngredientsInRecipeService.
 * Tests the functionality of finding recipe IDs by ingredient ID.
 */
@SpringBootTest
@Transactional
public class IngredientsInRecipeServiceTest {
    @Autowired
    IngredientInRecipeRepository ingredientInRecipeRepository;

    @Autowired
    RecipeService recipeService;

    @Autowired
    IngredientsService ingredientsService;

    private Recipes recipe1;
    private Recipes recipe2;

    @BeforeEach
    void setUp() {
        recipe1 = new Recipes("Pancakes");
        recipe2 = new Recipes("Omelette");
        Ingredients ingredient1 = new Ingredients("Eggs", 150, "Eggs", commons.Unit.PIECE);

        IngredientInRecipe iir1 = new IngredientInRecipe();
        iir1.setTempIngredient(ingredient1);
        iir1.setRecipes(recipe1);
        iir1.setQuantity(2);
        iir1.setUnit(commons.Unit.PIECE);

        IngredientInRecipe iir2 = new IngredientInRecipe();
        iir2.setTempIngredient(ingredient1);
        iir2.setRecipes(recipe2);
        iir2.setQuantity(3);
        iir2.setUnit(commons.Unit.PIECE);

        recipe1.addIngredient(iir1);
        recipe2.addIngredient(iir2);
    }

    @Test
    void testFindRecipeIdsByIngredientId() {
        Recipes r1 = recipeService.addRecipe(recipe1);
        Recipes r2 = recipeService.addRecipe(recipe2);
        int count = 0;
        for (Ingredients i: ingredientsService.getAllIngredients()) {
            count++;
        }
        assertEquals(1, count);
        Ingredients ingredient = ingredientsService.getAllIngredients().iterator().next();
        List<Long> recipeId = ingredientInRecipeRepository.findRecipeIdsByIngredientId(ingredient.getId());
        assertEquals(2, recipeId.size());
        assertEquals(r1.getId(), recipeId.get(0));
        assertEquals(r2.getId(), recipeId.get(1));
    }
}
