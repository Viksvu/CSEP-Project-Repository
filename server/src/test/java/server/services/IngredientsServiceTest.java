package server.services;

import commons.Ingredients;
import commons.Unit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import server.database.IngredientsRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for IngredientsRepository to verify adding and deleting ingredients.
 * Basic unit tests using an in-memory database.
 */
@DataJpaTest
public class IngredientsServiceTest {
    @Autowired
    IngredientsRepository ingredientsRepository;

    @Test
    void testAddIngredient() {
        Ingredients testIngredient = new Ingredients("Sugar", 10, 0.0, 100.0, 387.0);
        Ingredients savedIngredient = ingredientsRepository.save(testIngredient);
        Optional<Ingredients> foundIngredient = ingredientsRepository.findById(savedIngredient.getId());

        if (foundIngredient.isEmpty()) {
            assert false;
        } else {
            Ingredients ingredient = foundIngredient.get();
            assertEquals("Sugar", ingredient.getName());
            assertEquals(10, ingredient.getKcalPer100g());
        }
    }

    @Test
    void testDeleteIngredient() {
        Ingredients testIngredient = new Ingredients("Salt", 0, 0.0, 0.0, 0.0);
        Ingredients savedIngredient = ingredientsRepository.save(testIngredient);
        Long ingredientId = savedIngredient.getId();
        ingredientsRepository.deleteById(ingredientId);
        Optional<Ingredients> foundIngredient = ingredientsRepository.findById(ingredientId);
        assert(foundIngredient.isEmpty());
    }

}
