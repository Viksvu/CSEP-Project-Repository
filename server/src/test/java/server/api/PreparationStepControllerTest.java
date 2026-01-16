package server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.PreparationStep;
import commons.Recipes;
import commons.request.AddPreparationStepRequest;
import commons.request.DeletePreparationStepRequest;
import commons.request.EditPreparationStepRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
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
        classes = Main.class
)
@ActiveProfiles("test")
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
                        .queryParam("recipeId", this.recipe.getId().toString()))
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

        AddPreparationStepRequest request = new AddPreparationStepRequest(this.recipe.getId(), ps);
        this.mvc.perform(post("/api/prep-step/add")
                    .content(this.objectMapper.writeValueAsString(request))
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

        AddPreparationStepRequest request = new AddPreparationStepRequest(this.recipe.getId(), ps);
        this.mvc.perform(post("/api/prep-step/add")
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addNullRecipePreparationStep() throws Exception {
        String name = "VeryCoolPreparationStep";

        PreparationStep ps = new PreparationStep(name);

        AddPreparationStepRequest request = new AddPreparationStepRequest(-1L, ps);
        this.mvc.perform(post("/api/prep-step/add")
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deletePreparationStep() throws Exception {
        String name = "VeryCoolPreparationStep";

        PreparationStep ps = new PreparationStep(name);

        this.recipe.addPreparationStep(ps);
        this.recipe = this.recipeService.addRecipe(this.recipe);

        DeletePreparationStepRequest request = new DeletePreparationStepRequest(this.recipe.getId(), ps);
        this.mvc.perform(post("/api/prep-step/delete")
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        this.recipe = this.recipeService.getRecipeById(this.recipe.getId());
        List<PreparationStep> list = this.recipe.getPreparationSteps();
        assertTrue(list.isEmpty());
    }

    @Test
    public void deleteEmptyPreparationStep() throws Exception {
        String name = "";

        PreparationStep ps = new PreparationStep(name);

        DeletePreparationStepRequest request = new DeletePreparationStepRequest(this.recipe.getId(), ps);
        this.mvc.perform(post("/api/prep-step/delete")
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteNullRecipePreparationStep() throws Exception {
        String name = "VeryCoolPreparationStep";

        PreparationStep ps = new PreparationStep(name);

        DeletePreparationStepRequest request = new DeletePreparationStepRequest(-1L, ps);
        this.mvc.perform(post("/api/prep-step/delete")
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void editPreparationStep() throws Exception {
        String name = "InitName";
        String newName = "NewName";

        PreparationStep ps = new PreparationStep(name);
        this.recipe.addPreparationStep(ps);
        this.recipe = this.recipeService.addRecipe(this.recipe);

        ps.setDescription(newName);

        EditPreparationStepRequest request = new EditPreparationStepRequest(this.recipe.getId(), 0, ps);
        this.mvc.perform(post("/api/prep-step/edit")
                .content(this.objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        this.recipe = this.recipeService.getRecipeById(this.recipe.getId());
        List<PreparationStep> list = this.recipe.getPreparationSteps();
        assertEquals(newName, list.getFirst().getDescription());
    }

    @Test
    public void editEmptyPreparationStep() throws Exception {
        String name = "InitName";
        String newName = "";

        PreparationStep ps = new PreparationStep(name);
        this.recipe.addPreparationStep(ps);
        this.recipe = this.recipeService.addRecipe(this.recipe);

        ps.setDescription(newName);

        EditPreparationStepRequest request = new EditPreparationStepRequest(this.recipe.getId(), 0, ps);
        this.mvc.perform(post("/api/prep-step/edit")
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void editNullRecipePreparationStep() throws Exception {
        String name = "InitName";
        String newName = "NewName";

        PreparationStep ps = new PreparationStep(name);
        this.recipe.addPreparationStep(ps);
        this.recipe = this.recipeService.addRecipe(this.recipe);

        ps.setDescription(newName);

        EditPreparationStepRequest request = new EditPreparationStepRequest(-1L, 0, ps);
        this.mvc.perform(post("/api/prep-step/edit")
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void editPreparationStepNegativeIndex() throws Exception {
        String name = "InitName";
        String newName = "NewName";

        PreparationStep ps = new PreparationStep(name);
        this.recipe.addPreparationStep(ps);
        this.recipe = this.recipeService.addRecipe(this.recipe);

        ps.setDescription(newName);

        EditPreparationStepRequest request = new EditPreparationStepRequest(this.recipe.getId(), -1, ps);
        this.mvc.perform(post("/api/prep-step/edit")
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void editPreparationStepNotExistingIndex() throws Exception {
        String name = "InitName";
        String newName = "NewName";

        PreparationStep ps = new PreparationStep(name);
        this.recipe.addPreparationStep(ps);
        this.recipe = this.recipeService.addRecipe(this.recipe);

        ps.setDescription(newName);

        EditPreparationStepRequest request = new EditPreparationStepRequest(this.recipe.getId(), 1, ps);
        this.mvc.perform(post("/api/prep-step/edit")
                        .content(this.objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}