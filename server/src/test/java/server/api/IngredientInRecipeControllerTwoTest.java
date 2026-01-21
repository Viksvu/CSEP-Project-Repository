package server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.IngredientInRecipe;
import commons.Ingredients;
import commons.Recipes;
import commons.Unit;
import commons.request.AddIngredientInRecipeRequest;
import commons.request.EditIngredientInRecipeRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import server.Main;
import server.services.RecipeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Main.class)
@ActiveProfiles("test")
@TestPropertySource(
        locations = "classpath:application-junit.properties")
@Transactional
public class IngredientInRecipeControllerTwoTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testEdit() throws Exception {
        Ingredients i1 =  new Ingredients("Test 1", 100, 100, 100, 100);

        IngredientInRecipe ir1 = new IngredientInRecipe(i1, 10, Unit.TABLE_SPOON);
        Recipes r = new Recipes("Test Edit recipe");
        r.addIngredient(ir1);

        IngredientInRecipe ir2 = new IngredientInRecipe(i1, 5, Unit.PIECE);
        r.addIngredient(ir2);

        r = this.recipeService.addRecipe(r);

        ir1 = r.getIngredients().getFirst();
        Ingredients i2 =  new Ingredients("Test 1", 100, 100, 100, 100);
        ir1.setIngredient(i2);
        ir1.setQuantity(2);
        ir1.setUnit(Unit.CUP);

        EditIngredientInRecipeRequest request = new EditIngredientInRecipeRequest(
                r.getId(),
                ir1
        );

        mvc.perform(post("/api/recipeingredient/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());

        r = this.recipeService.getRecipeById(r.getId());
        IngredientInRecipe iRes = r.getIngredients().getFirst();
        assertNotNull(iRes);
        assertEquals(2, iRes.getQuantity());
        assertEquals(Unit.CUP, iRes.getUnit());
    }

    @Test
    public void testEditNullRecipe() throws Exception {
        Ingredients i1 = new Ingredients("Test 1", 100, 100, 100, 100);

        IngredientInRecipe ir1 = new IngredientInRecipe(i1, 10, Unit.TABLE_SPOON);
        Recipes r = new Recipes("Test Edit recipe");
        r.addIngredient(ir1);

        r = this.recipeService.addRecipe(r);
        ir1 = r.getIngredients().getFirst();

        EditIngredientInRecipeRequest request = new EditIngredientInRecipeRequest(
                -1L,
                ir1
        );

        mvc.perform(post("/api/recipeingredient/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addIngredient() throws Exception {
        Ingredients i = new Ingredients("Salt", 10, 10, 10, 10);
        IngredientInRecipe ir1 = new IngredientInRecipe(i, 10, Unit.PIECE);

        Recipes r = this.recipeService.addRecipe(
                new Recipes("Test add ingredient"));

        AddIngredientInRecipeRequest request = new AddIngredientInRecipeRequest(
                r.getId(),
                ir1
        );

        MvcResult result = this.mvc.perform(post("/api/recipeingredient/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(request)))
            .andExpect(status().is2xxSuccessful())
            .andReturn();

        IngredientInRecipe irRes = this.objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IngredientInRecipe.class);
        assertNotNull(irRes);
        assertEquals("Salt", irRes.getIngredient().getName());
        assertEquals(10, irRes.getQuantity());
        assertEquals(Unit.PIECE, irRes.getUnit());
    }
}
