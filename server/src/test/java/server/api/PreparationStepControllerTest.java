package server.api;

import commons.PreparationStep;
import commons.Recipes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.services.TempRecipeService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled // TODO: Needs to be implemented with new recipe service
class PreparationStepControllerTest {

    private PreparationStepController preparationStepController;
    private Recipes r1;
    private Recipes r2;
    private Recipes r3;
    private PreparationStep ps1;
    private PreparationStep ps2;
    private PreparationStep ps3;
    private TempRecipeService tc;

    @BeforeEach
    void setUp() {
//        tc = TempRecipeService.get();
//        preparationStepController = new PreparationStepController();
//        r1 = new Recipes("Pizza");
//        r2 = new Recipes("Pasta");
//        r3 = new Recipes("Lasagne");
//        ps1 = new PreparationStep("First step.");
//        ps2 = new PreparationStep("Second step.");
//        ps3 = new PreparationStep("Third step.");
//        r1.addPreparationStep(ps1);
//        r1.addPreparationStep(ps2);
//        tc.addRecipe(r1);
//        tc.addRecipe(r2);
//        tc.addRecipe(r3);
    }

    @Test
    void getPreparationSteps() {
        List<PreparationStep> steps = preparationStepController.getPreparationSteps(r1.getId());
        List<PreparationStep> expected = List.of(ps1, ps2);

        assertEquals(expected, steps);
    }

    @Test
    void addPreparationStepToNullRecipe() {
        ResponseEntity<PreparationStep> response =
                preparationStepController.addPreparationStep(-1, ps1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void addNullPreparationStep() {
        ResponseEntity<PreparationStep> response =
                preparationStepController.addPreparationStep(r3.getId(), null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void addPreparationStep() {
        List<PreparationStep> steps = preparationStepController.getPreparationSteps(r1.getId());
        ResponseEntity<PreparationStep>  response =
                preparationStepController.addPreparationStep(r1.getId(), ps3);
        r1.addPreparationStep(ps3);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(steps, preparationStepController.getPreparationSteps(r1.getId()));
    }
    @Test
    void deletePreparationStepFromNullRecipe() {
        ResponseEntity<PreparationStep> response
                = preparationStepController.deletePreparationStep(-1, ps1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}