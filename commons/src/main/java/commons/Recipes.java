package commons;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Recipes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;
    @OneToMany(mappedBy = "recipes", cascade = CascadeType.ALL,
            orphanRemoval = true)
    // to change after ingredients class is implemented
    private List<IngredientInRecipe> ingredients;
    @ElementCollection
    private List<PreparationStep> preparationSteps;
    private String name;

    /**
     * Class recipes for the recipes
     *
     */
    public Recipes() {
        this.name = "";
        this.ingredients = new ArrayList<>();
        this.preparationSteps = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Constructor for recipes (For testing)
     *
     * @param name - the name of the recipe
     */
    public Recipes(String name) {
        this.name = name;
        this.ingredients = new ArrayList<>();
        this.preparationSteps = new ArrayList<>();
    }

    /**
     * A constructor of recipe for in use code.
     *
     * @param id               the ID of a recipe.
     * @param ingredients      the ingredients in a recipe.
     * @param preparationSteps the preparation steps in a recipe.
     * @param name             the name of the recipe.
     */
    public Recipes(long id, List<IngredientInRecipe> ingredients,
                   List<PreparationStep> preparationSteps, String name) {
        this.id = id;
        this.ingredients = ingredients;
        this.preparationSteps = preparationSteps;
        this.name = name;
    }

    /**
     * adds an ingredient into recipes
     *
     * @param ingredient - the ingredient
     */
    public void addIngredient(IngredientInRecipe ingredient) {
        ingredients.add(ingredient);
    }

    /**
     * removes ingredient from recipes
     *
     * @param ingredient - the ingredient
     */
    public void removeIngredient(IngredientInRecipe ingredient) {
        ingredients.remove(ingredient);
    }

    /**
     * Adds preparationStep into the recipe
     *
     * @param preparationStep the preparation step
     */
    public void addPreparationStep(PreparationStep preparationStep) {
        preparationSteps.add(preparationStep);
    }

    public List<IngredientInRecipe> getIngredients() {
        return ingredients;
    }

    public List<PreparationStep> getPreparationSteps() {
        return preparationSteps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPreparationSteps(List<PreparationStep> preparationSteps) {
        this.preparationSteps = preparationSteps;
    }

    /**
     * Removes a specific preparation step from the
     * list of all preparation steps
     * @param preparationStep to remove from the list
     */
    public void removePreparationStep(PreparationStep preparationStep) {
        preparationSteps.remove(preparationStep);
    }

    public void setIngredients(List<IngredientInRecipe> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recipes recipes = (Recipes) o;
        return Objects.equals(ingredients, recipes.ingredients)
                && Objects.equals(preparationSteps, recipes.preparationSteps) &&
                Objects.equals(name, recipes.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredients, preparationSteps, name);
    }

    @Override
    public String toString() {
        return name;
    }
}
