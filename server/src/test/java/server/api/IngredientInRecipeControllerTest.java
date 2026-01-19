package server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.IngredientInRecipe;
import commons.Ingredients;
import commons.Recipes;
import commons.Unit;
import commons.request.AddIngredientInRecipeRequest;
import commons.request.DeleteIngredientInRecipeRequest;
import commons.request.EditIngredientInRecipeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import server.services.RecipeService;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(IngredientInRecipeController.class)
@Import(IngredientInRecipeControllerTest.MockConfig.class)
public class IngredientInRecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RecipeService recipeService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        RecipeService recipeService() {
            return Mockito.mock(RecipeService.class);
        }
    }

    private IngredientInRecipeController controller;
    private Recipes recipe;
    private Ingredients ingredient;
    private IngredientInRecipe ingredientInRecipe;
    private Unit unit;

    @BeforeEach
    public void setup() {
        Mockito.reset(recipeService);
        controller = new IngredientInRecipeController(recipeService);
        ingredient = new Ingredients("Salt", 10, 0.0, 0.0, 0.0);
        unit = Unit.GRAM;
        ingredientInRecipe = new IngredientInRecipe(ingredient, 5, unit);
        recipe = new Recipes();
        recipe.setId(1L);
        recipe.addIngredient(ingredientInRecipe);
    }

    @Test
    public void getWithIdZeroReturnsBadRequest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/recipeingredient/get")
                        .param("id", "0"))
                .andReturn();

        assertEquals(400, result.getResponse().getStatus());
        verify(recipeService, never()).getRecipeById(anyLong());
    }
    @Test
    public void getWhenServiceThrowsReturnsBadRequest() throws Exception {
        when(recipeService.getRecipeById(1L)).thenThrow(new RuntimeException("not found"));

        MvcResult result = mockMvc.perform(get("/api/recipeingredient/get")
                        .param("id", "1"))
                .andReturn();

        assertEquals(400, result.getResponse().getStatus());
        verify(recipeService).getRecipeById(1L);
    }
    @Test
    public void getReturnsIngredientList() throws Exception {
        when(recipeService.getRecipeById(2L)).thenReturn(recipe);

        MvcResult result=mockMvc.perform(get("/api/recipeingredient/get")
                        .param("id", "2"))
                .andReturn();

        assertEquals(200,result.getResponse().getStatus());

        String json=result.getResponse().getContentAsString();
        IngredientInRecipe[] response=objectMapper.readValue(json,IngredientInRecipe[].class);
        assertEquals(1,response.length);
        verify(recipeService).getRecipeById(2L);
    }
    @Test
    public void addWhenRecipeNullReturnsBadRequest() throws Exception {
        Long recipeId=10L;
        when(recipeService.getRecipeByIdSafe(recipeId)).thenReturn(null);

        AddIngredientInRecipeRequest request=
                new AddIngredientInRecipeRequest(recipeId, ingredientInRecipe);

        MvcResult result=mockMvc.perform(post("/api/recipeingredient/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        assertEquals(400,result.getResponse().getStatus());

        verify(recipeService).getRecipeByIdSafe(recipeId);
        verify(recipeService, never()).addRecipe(any());
    }
    @Test
    public void editWhenRecipeNullReturnsBadRequest() throws Exception {
        Long recipeId=20L;
        when(recipeService.getRecipeByIdSafe(recipeId)).thenReturn(null);
        EditIngredientInRecipeRequest request=
                new EditIngredientInRecipeRequest(recipeId, ingredientInRecipe);

        MvcResult result=mockMvc.perform(post("/api/recipeingredient/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        assertEquals(400,result.getResponse().getStatus());

        verify(recipeService).getRecipeByIdSafe(recipeId);
        verify(recipeService,never()).addRecipe(any());
    }
    @Test
    public void editSuccessUpdatesMatchingIngredientAndNotifies() throws Exception {
        Long recipeId=21L;
        recipe.setId(recipeId);

        IngredientInRecipe nonMatching=new IngredientInRecipe();
        nonMatching.setId(1L);
        nonMatching.setIngredient(new Ingredients("Pepper",1,0.0,0.0,0.0));
        nonMatching.setQuantity(1);
        nonMatching.setUnit(unit);

        IngredientInRecipe matching=new IngredientInRecipe();
        matching.setId(676L);
        matching.setIngredient(new Ingredients("Old",1,0.0,0.0,0.0));
        matching.setQuantity(1);
        matching.setUnit(unit);

        recipe.getIngredients().clear();
        recipe.addIngredient(nonMatching);
        recipe.addIngredient(matching);

        Ingredients newIngredient=new Ingredients("NewIngredient",2,0.0,0.0,0.0);
        IngredientInRecipe edited=new IngredientInRecipe();
        edited.setId(676L);
        edited.setIngredient(newIngredient);
        edited.setQuantity(123);
        edited.setUnit(unit);

        when(recipeService.getRecipeByIdSafe(recipeId)).thenReturn(recipe);

        EditIngredientInRecipeRequest req=
                new EditIngredientInRecipeRequest(recipeId,edited);

    }
    @Test
    public void deleteWhenRecipeNullReturnsBadRequest() throws Exception {
        Long recipeId=30L;

        when(recipeService.getRecipeByIdSafe(recipeId)).thenReturn(null);

        DeleteIngredientInRecipeRequest request=
                new DeleteIngredientInRecipeRequest(recipeId,ingredientInRecipe);

        MvcResult result = mockMvc.perform(post("/api/recipeingredient/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        assertEquals(400,result.getResponse().getStatus());

        verify(recipeService).getRecipeByIdSafe(recipeId);
        verify(recipeService, never()).addRecipe(any());
    }
    @Test
    public void deleteSuccessRemovesIngredient() throws Exception {
        Long recipeId=31L;
        recipe.setId(recipeId);

        recipe.getIngredients().clear();
        recipe.addIngredient(ingredientInRecipe);
        assertEquals(1,recipe.getIngredients().size());

        when(recipeService.getRecipeByIdSafe(recipeId)).thenReturn(recipe);

        DeleteIngredientInRecipeRequest req=
                new DeleteIngredientInRecipeRequest(recipeId,ingredientInRecipe);

        MvcResult result=mockMvc.perform(post("/api/recipeingredient/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andReturn();

        assertEquals(200,result.getResponse().getStatus());
        assertEquals(0,recipe.getIngredients().size());
        verify(recipeService).addRecipe(eq(recipe));
    }



}
