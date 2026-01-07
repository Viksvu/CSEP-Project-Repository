package server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Recipes;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Main.class)
@TestPropertySource(
        locations = "classpath:application-junit.properties")
public class RecipeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testList() throws Exception {
        String name1 = "TestRecipeList1";
        String name2 = "TestRecipeList2";

        Recipes t1 = new Recipes(name1);
        Recipes t2 = new Recipes(name2);

        this.recipeService.addRecipe(t1);
        this.recipeService.addRecipe(t2);

        MvcResult result = mvc.perform(get("/api/recipe/list"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Recipes[] list = this.objectMapper.readValue(result.getResponse().getContentAsString(), Recipes[].class);
        assertTrue(Arrays.stream(list).anyMatch(r -> r.getName().equals(name1)));
        assertTrue(Arrays.stream(list).anyMatch(r -> r.getName().equals(name2)));
    }

    @Test
    public void testAddAndDelete() throws Exception {
        Recipes t1 = new Recipes("TestRecipeAdd");

        // Check add
        MvcResult result = this.mvc.perform(post("/api/recipe/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(t1)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Recipes r1 = this.objectMapper.readValue(result.getResponse().getContentAsString(), Recipes.class);
        assertNotNull(r1);
        assertNotNull(r1.getId());

        Recipes f1 = this.recipeService.getRecipeById(r1.getId());
        assertNotNull(f1);

        // Then delete
        result = this.mvc.perform(post("/api/recipe/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(r1)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Recipes r2 = this.objectMapper.readValue(result.getResponse().getContentAsString(), Recipes.class);
        assertNotNull(r2);
        assertNotNull(r2.getId());

        assertThrows(RecipeService.RecipeNotFoundException.class, () -> this.recipeService.getRecipeById(r2.getId()));
    }

    @Test
    public void testAddNull() throws Exception {
        this.mvc.perform(post("/api/recipe/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddInvalidName() throws Exception {
        Recipes t1 = new Recipes("");

        this.mvc.perform(post("/api/recipe/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(t1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddNullName() throws Exception {
        Recipes t1 = new Recipes(null);

        this.mvc.perform(post("/api/recipe/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(t1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteNull() throws Exception {
        this.mvc.perform(post("/api/recipe/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteInvalidId() throws Exception {
        Recipes t1 = new Recipes("TestDeleteNull");

        this.mvc.perform(post("/api/recipe/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(t1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRename() throws Exception {
        Recipes r = new Recipes("TestRename");
        r = this.recipeService.addRecipe(r);

        Long id = r.getId();
        assertNotNull(id);

        String newName = "VeryCoolName";
        this.mvc.perform(post("/api/recipe/rename")
                        .queryParam("id", String.valueOf(id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newName))
                .andExpect(status().is2xxSuccessful());

        r = this.recipeService.getRecipeById(id);
        assertNotNull(r);
        assertEquals(newName, r.getName());
    }

    @Test
    public void testRenameInvalidName() throws Exception {
        Recipes r = new Recipes("TestRename");
        r = this.recipeService.addRecipe(r);

        Long id = r.getId();
        assertNotNull(id);

        String newName = "";
        this.mvc.perform(post("/api/recipe/rename")
                        .queryParam("id", String.valueOf(id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newName))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRenameInvalidID() throws Exception {
        Long id = -1L;
        String newName = "VeryCoolName";
        this.mvc.perform(post("/api/recipe/rename")
                        .queryParam("id", String.valueOf(id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newName))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testClone() throws Exception {
        Recipes r = new Recipes("TestClone");
        r = this.recipeService.addRecipe(r);

        String newName = "ClonedRecipe";

        MvcResult result = this.mvc.perform(post("/api/recipe/clone")
                .queryParam("newName", newName)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(r)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        assertNotNull(json);

        Recipes res = this.objectMapper.readValue(json, Recipes.class);
        assertNotNull(res);

        assertEquals(newName, res.getName());
    }

    @Test
    public void testCloneNonExisting() throws Exception {
        Recipes r = new Recipes("TestCloneNonExisting");

        String newName = "ClonedRecipe";

        this.mvc.perform(post("/api/recipe/clone")
                        .queryParam("newName", newName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(r)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}