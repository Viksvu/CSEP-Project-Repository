package server.services;

import commons.TestRecipeForDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import server.database.RecipeRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RecipeServiceTest {
    @Autowired
    RecipeRepository recipeRepository;

    @Test
    void testSavingRecipe() {
        TestRecipeForDB testRecipe1 = new TestRecipeForDB("Pancakes", "Making the best pancakes", 60);

        ArrayList<String> ingredients1 = new ArrayList<>(Arrays.asList("Flour", "Eggs", "Milk"));
        testRecipe1.addAllIngredients(ingredients1);

        TestRecipeForDB saved = recipeRepository.save(testRecipe1);
        Optional<TestRecipeForDB> result = recipeRepository.findById(saved.getId());
        TestRecipeForDB found = result.get();

        assertEquals(saved.getId(), found.getId());
        assertEquals("Pancakes", found.getName());
        assertEquals(3, found.getIngredients().size());
        assertTrue(found.getIngredients().contains("Flour"));
        assertTrue(found.getIngredients().contains("Eggs"));
        assertTrue(found.getIngredients().contains("Milk"));
    }

    @Test
    void testFindingAllRecipes() {
        TestRecipeForDB testRecipe1 = new TestRecipeForDB("Pancakes", "Making the best pancakes", 60);
        TestRecipeForDB testRecipe2 = new TestRecipeForDB("Omelette", "Delicious cheese omelette", 15);
        TestRecipeForDB testRecipe3 = new TestRecipeForDB("Dal Makhni", "Mom's recipe for Dal", 60);
        TestRecipeForDB result1 = recipeRepository.save(testRecipe1);
        TestRecipeForDB result2 = recipeRepository.save(testRecipe2);
        TestRecipeForDB result3 = recipeRepository.save(testRecipe3);

        Iterable<TestRecipeForDB> allRecipes = recipeRepository.findAll();
        ArrayList<TestRecipeForDB> recipeList = new ArrayList<>();
        for(TestRecipeForDB recipe : allRecipes) {
            recipeList.add(recipe);
        }
        assertEquals(3, recipeList.size());
        assertEquals(result1.getId(), recipeList.get(0).getId());
        assertEquals(result2.getId(), recipeList.get(1).getId());
        assertEquals(result3.getId(), recipeList.get(2).getId());
    }

    @Test
    void testDeletingRecipe() {
        TestRecipeForDB testRecipe1 = new TestRecipeForDB("Pancakes", "Making the best pancakes", 60);
        TestRecipeForDB savedRecipe = recipeRepository.save(testRecipe1);
        Long recipeId = savedRecipe.getId();

        recipeRepository.deleteById(recipeId);
        Optional<TestRecipeForDB> result = recipeRepository.findById(recipeId);
        assertFalse(result.isPresent());
    }

    @Test
    void testIngredients() {
        TestRecipeForDB testRecipe1 = new TestRecipeForDB("Pancakes", "Making the best pancakes", 60);

        ArrayList<String> ingredients1 = new ArrayList<>(Arrays.asList("Flour", "Eggs", "Milk"));
        testRecipe1.addAllIngredients(ingredients1);

        TestRecipeForDB saved = recipeRepository.save(testRecipe1);
        Optional<TestRecipeForDB> result = recipeRepository.findById(saved.getId());
        TestRecipeForDB found = result.get();

        assertEquals(3, found.getIngredients().size());
        assertTrue(found.getIngredients().contains("Flour"));
        assertTrue(found.getIngredients().contains("Eggs"));
        assertTrue(found.getIngredients().contains("Milk"));
    }
}
