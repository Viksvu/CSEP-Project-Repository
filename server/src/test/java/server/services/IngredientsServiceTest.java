package server.services;

import commons.Ingredients;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import server.database.IngredientsRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for IngredientsService.
 * Uses an in-memory database and verifies service-layer behavior.
 */
@DataJpaTest
@Import(IngredientsService.class)
class IngredientsServiceTest {

    @Autowired
    private IngredientsService ingredientsService;

    @Autowired
    private IngredientsRepository ingredientsRepository;

    @Test
    void testAddIngredient() {
        Ingredients ingredient =
                new Ingredients("Sugar", 10, 0.0, 100.0, 387.0);

        Ingredients saved = ingredientsService.addIngredient(ingredient);

        assertNotNull(saved.getId());

        Optional<Ingredients> found =
                ingredientsRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Sugar", found.get().getName());
        assertEquals(10, found.get().getKcalPer100g());
    }

    @Test
    void testGetIngredientById() {
        Ingredients ingredient =
                new Ingredients("Salt", 0, 0.0, 0.0, 0.0);

        Ingredients saved = ingredientsRepository.save(ingredient);

        Optional<Ingredients> found =
                ingredientsService.getIngredientById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Salt", found.get().getName());
    }

    @Test
    void testGetIngredientById_notFound() {
        Optional<Ingredients> found =
                ingredientsService.getIngredientById(999L);

        assertTrue(found.isEmpty());
    }

    @Test
    void testDeleteIngredient() {
        Ingredients ingredient =
                new Ingredients("Flour", 364, 1.0, 76.0, 10.0);

        Ingredients saved = ingredientsRepository.save(ingredient);
        Long id = saved.getId();

        ingredientsService.deleteIngredient(id);

        Optional<Ingredients> found =
                ingredientsRepository.findById(id);

        assertTrue(found.isEmpty());
    }

    @Test
    void testGetAllIngredients() {
        ingredientsRepository.save(
                new Ingredients("Milk", 42, 1.0, 5.0, 3.4)
        );
        ingredientsRepository.save(
                new Ingredients("Butter", 717, 81.0, 0.1, 0.9)
        );

        Iterable<Ingredients> all =
                ingredientsService.getAllIngredients();

        int count = 0;
        for (Ingredients i : all) {
            count++;
        }

        assertEquals(2, count);
    }
}
