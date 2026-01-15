package server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Ingredients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import server.services.IngredientsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(IngredientController.class)
@Import(IngredientControllerTest.MockConfig.class)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IngredientsService ingredientsService;

    // used the help of ChatGPT to debug and set up the configuration for the tests.
    @TestConfiguration
    static class MockConfig {

        @Bean
        IngredientsService ingredientService() {
            return Mockito.mock(IngredientsService.class);
        }
    }
    private IngredientController ic;
    private Ingredients i1;
    private Ingredients i2;

    @BeforeEach
    public void setup() {
        ic = new IngredientController(ingredientsService);
        i1 = new Ingredients("Salt", 10, 0.0, 0.0, 0.0);
        i2 = new Ingredients("Pepperoni", 100, 10.0, 0.0, 5.0);
    }

    @Test
    public void addNullIngredient() throws Exception {
        List<Ingredients> ingredients = new ArrayList<>();
        when(ingredientsService.getAllIngredients()).thenReturn(ingredients);
        MvcResult result = mockMvc.perform(get("/api/ingredient/list")).andReturn();
        String json = result.getResponse().getContentAsString();
        List<Ingredients> responseList = Arrays.asList(objectMapper.readValue(json, Ingredients[].class));
        assertEquals(0, responseList.size());
    }

    @Test
    public void checkAdd() throws Exception {
        when(ingredientsService.addIngredient(any())).thenReturn(i1);
        MvcResult result = mockMvc.perform(post("/api/ingredient/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(i1))).andReturn();
        String json = result.getResponse().getContentAsString();
        Ingredients response = objectMapper.readValue(json, Ingredients.class);
        assertEquals(response.getId(), i1.getId());
        assertEquals(response.getName(), i1.getName());
        verify(ingredientsService).addIngredient(any());
    }

    @Test
    public void checkAddIngredientNameNull() throws Exception {
        i1.setName("");
        when(ingredientsService.addIngredient(any())).thenReturn(i1);
        MvcResult result = mockMvc.perform(post("/api/ingredient/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(i1))).andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }
}
