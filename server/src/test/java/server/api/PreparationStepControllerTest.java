package server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.PreparationStep;
import commons.Recipes;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import server.Main;
import server.services.RecipeService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Main.class)
@TestPropertySource(
        locations = "classpath:application-junit.properties")
class PreparationStepControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Recipes recipe;

    @BeforeEach
    public void setup() {
        if (this.recipe == null) {
            this.recipe = new Recipes("Test Recipe");
        }
        this.recipe.getPreparationSteps().clear();
        this.recipe = this.recipeService.addRecipe(this.recipe);
    }

    @Test
    public void testList() throws Exception {
        String desc1 = "TestRecipeStep1";
        String desc2 = "TestRecipeStep2";

        PreparationStep step1 = new PreparationStep(desc1);
        PreparationStep step2 = new PreparationStep(desc2);

        this.recipe.addPreparationStep(step1);
        this.recipe.addPreparationStep(step2);

        this.recipeService.addRecipe(this.recipe);

        MvcResult result = mvc.perform(get("/api/prep-step/list")
                        .queryParam("recipeId", String.valueOf(this.recipe.getId())))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        PreparationStep[] list = this.objectMapper.readValue(result.getResponse().getContentAsString(), PreparationStep[].class);
        assertTrue(Arrays.stream(list).anyMatch(r -> r.getDescription().equals(desc1)));
        assertTrue(Arrays.stream(list).anyMatch(r -> r.getDescription().equals(desc2)));
    }

    @Test
    public void testListNullRecipeId() throws Exception {
        this.mvc.perform(get("/api/prep-step/list")
                .queryParam("recipeId", "-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void addPreparationStep() throws Exception {
        String name = "VeryCoolPreparationStep";

        PreparationStep ps = new PreparationStep(name);

        this.mvc.perform(post("/api/prep-step/add")
                        .queryParam("recipeId", String.valueOf(this.recipe.getId()))
                .content(this.objectMapper.writeValueAsString(ps))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        this.recipe = this.recipeService.getRecipeById(this.recipe.getId());
        List<PreparationStep> list = this.recipe.getPreparationSteps();
        assertEquals(name, list.getFirst().getDescription());
    }

    @Test
    public void addEmptyPreparationStep() throws Exception {
        String name = "";

        PreparationStep ps = new PreparationStep(name);

        this.mvc.perform(post("/api/prep-step/add")
                        .queryParam("recipeId", String.valueOf(this.recipe.getId()))
                        .content(this.objectMapper.writeValueAsString(ps))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void addNullRecipePreparationStep() throws Exception {
        String name = "VeryCoolPreparationStep";

        PreparationStep ps = new PreparationStep(name);

        this.mvc.perform(post("/api/prep-step/add")
                        .queryParam("recipeId", "-1")
                        .content(this.objectMapper.writeValueAsString(ps))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}