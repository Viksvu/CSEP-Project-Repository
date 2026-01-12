package commons;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Recipes implements Printable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    @OneToMany(mappedBy = "recipes", cascade = CascadeType.ALL,
            orphanRemoval = true)
    // to change after ingredients class is implemented
    @JsonManagedReference
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    /**
     * Checks whether a given preparation step is a part of the recipe
     * @param preparationStep to check for
     * @return a boolean for contains or not contains
     */
    @Deprecated
    public boolean containsPreparationStep(PreparationStep preparationStep) {
        return preparationSteps.contains(preparationStep);
    }

    public void setIngredients(List<IngredientInRecipe> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Clones "this" by cloning all the attributes
     * Important: IngredientInRecipe stored in this recipe DO NOT have
     * the recipe as it should be. So, after cloning the recipe, please call
     * setRecipeOnIngredients();
     * @return the clone
     */
    public Recipes cloneRecipes(String newName) {
        Recipes recipes = new Recipes(newName);
        for (IngredientInRecipe ingredient : this.ingredients) {
            recipes.addIngredient(ingredient.cloneIngredientInRecipe());
        }
        for (PreparationStep preparationStep : this.preparationSteps) {
            recipes.addPreparationStep(preparationStep.clonePreparationStep());
        }
        return recipes;
    }

    /**
     * Sets "this" as the recipe on all ingredients of the recipe
     */
    public void setRecipeOnIngredients() {
        for (IngredientInRecipe ingredient : this.ingredients) {
            ingredient.setRecipes(this);
        }
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

    /**
     * Creates a list of all things to be printed so that
     * Printer.print() can interpret it and create a .md
     * file based on that content.
     * @return the list of file content
     */
    @Override
    public List<Object> indexing(){
        List<Object> list = new ArrayList<>();
        list.add(ReadmeOptions.H1);
        list.add(name);
        if (ingredients != null && !ingredients.isEmpty()) {
            list.add(ReadmeOptions.BULLET);
            for (IngredientInRecipe ingredient : this.ingredients) {
                list.add(ingredient.toString());
            }
            list.add(ReadmeOptions.END_BULLET);
        }
        else {
            list.add(ReadmeOptions.TEXT);
            list.add("No Ingredients provided");
        }
        list.add(ReadmeOptions.TEXT);
        list.add("Preparation Steps:");
        if (preparationSteps != null && !preparationSteps.isEmpty()) {
            list.add(ReadmeOptions.NUMBERING);
            for (PreparationStep preparationStep : this.preparationSteps) {
                list.add(preparationStep.toString());
            }
            list.add(ReadmeOptions.END_NUMBERING);
        }
        else {
            list.add(ReadmeOptions.TEXT);
            list.add("No Preparation Steps provided");
        }
        return list;

    }
}
