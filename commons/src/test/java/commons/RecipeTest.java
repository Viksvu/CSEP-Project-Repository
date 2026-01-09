package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Test
    void testDefaultConstructor() {
        Recipes r = new Recipes();
        assertEquals("", r.getName());
        assertNotNull(r.getIngredients());
        assertNotNull(r.getPreparationSteps());
        assertTrue(r.getIngredients().isEmpty());
        assertTrue(r.getPreparationSteps().isEmpty());
    }

    @Test
    void testConstructorWithName() {
        Recipes r = new Recipes("Pancakes");
        assertEquals("Pancakes", r.getName());
        assertNotNull(r.getIngredients());
        assertNotNull(r.getPreparationSteps());
    }

    @Test
    void testSettersAndGetters() {
        Recipes r = new Recipes();
        r.setName("Cake");

        List<IngredientInRecipe> ingredients = new ArrayList<>();
        List<PreparationStep> steps = new ArrayList<>();

        IngredientInRecipe ingr = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());
        ingredients.add(ingr);

        PreparationStep step = new PreparationStep("Mix ingredients");
        steps.add(step);

        r.setIngredients(ingredients);
        r.setPreparationSteps(steps);

        assertEquals("Cake", r.getName());
        assertEquals(ingredients, r.getIngredients());
        assertEquals(steps, r.getPreparationSteps());
    }

    @Test
    void testAddIngredient() {
        Recipes r = new Recipes();
        IngredientInRecipe ingr = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());

        r.addIngredient(ingr);

        assertTrue(r.getIngredients().contains(ingr));
        assertEquals(1, r.getIngredients().size());
    }

    @Test
    void testRemoveIngredient() {
        Recipes r = new Recipes();
        IngredientInRecipe ingr = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());

        r.addIngredient(ingr);
        r.removeIngredient(ingr);

        assertFalse(r.getIngredients().contains(ingr));
        assertEquals(0, r.getIngredients().size());
    }

    @Test
    void testAddPreparationStep() {
        Recipes r = new Recipes();
        PreparationStep step = new PreparationStep("Boil water");

        r.addPreparationStep(step);

        assertTrue(r.getPreparationSteps().contains(step));
        assertEquals(1, r.getPreparationSteps().size());
    }

    @Test
    void testCloneRecipe() {
        Recipes r = new Recipes("My Recipe");
        r.addIngredient(new IngredientInRecipe(new Ingredients("Cheese")));
        Recipes r2 = r.cloneRecipes("My Recipe");
        assertEquals(r, r2);
    }

    @Test
    void testModifyOneIngredientClone() {
        Recipes r = new Recipes("My Recipe");
        r.addIngredient(new IngredientInRecipe(new Ingredients("Cheese")));
        Recipes r2 = r.cloneRecipes("My Recipe");
        r2.getIngredients().getFirst().getIngredient().setName("Not Cheese");
        assertNotEquals(r, r2);
    }

    @Test
    void testEquals() {
        Recipes r1 = new Recipes("Soup");
        Recipes r2 = new Recipes("Soup");

        assertEquals(r1, r2);

        // Add ingredients to both
        IngredientInRecipe ingr = new IngredientInRecipe(ingredient.getIngredient(), newQuantity, ingredient.getUnit());
        r1.addIngredient(ingr);
        r2.addIngredient(ingr);

        assertEquals(r1, r2);

        Recipes r3 = new Recipes("Salad");
        assertNotEquals(r1, r3);
    }

    @Test
    void testHashCode() {
        Recipes r1 = new Recipes("Soup");
        Recipes r2 = new Recipes("Soup");
        assertEquals(r1.hashCode(), r2.hashCode());
    }
}
