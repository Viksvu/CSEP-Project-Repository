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

        Ingredients sugar = new Ingredients("Sugar");
        IngredientInRecipe ingr = new IngredientInRecipe(sugar);
        ingr.setQuantity(100);
        ingr.setUnit(Unit.GRAM);
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
        Ingredients flour = new Ingredients("Flour");
        IngredientInRecipe ingr = new IngredientInRecipe(flour);
        ingr.setQuantity(200);
        ingr.setUnit(Unit.GRAM);

        r.addIngredient(ingr);

        assertTrue(r.getIngredients().contains(ingr));
        assertEquals(1, r.getIngredients().size());
    }

    @Test
    void testRemoveIngredient() {
        Recipes r = new Recipes();
        Ingredients butter = new Ingredients("Butter");
        IngredientInRecipe ingr = new IngredientInRecipe(butter);
        ingr.setQuantity(50);
        ingr.setUnit(Unit.GRAM);

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

        Ingredients carrot = new Ingredients("Carrot");
        IngredientInRecipe ingr1 = new IngredientInRecipe(carrot);
        ingr1.setQuantity(100);
        ingr1.setUnit(Unit.GRAM);

        IngredientInRecipe ingr2 = new IngredientInRecipe(carrot);
        ingr2.setQuantity(100);
        ingr2.setUnit(Unit.GRAM);

        r1.addIngredient(ingr1);
        r2.addIngredient(ingr2);

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
