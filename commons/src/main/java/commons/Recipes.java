package commons;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Recipes {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    private long id;
    @ManyToMany
    private List<IngredientInRecipe> ingredients; // to change after ingredients class is implemented

    private List<PreparationStep> preparationSteps;
    private String name;
    public Recipes() {
        this.name="";
        this.ingredients=new ArrayList<>();
        this.preparationSteps=new ArrayList<>();
    }
    public Recipes(String name) {
        this.name = name;
        this.ingredients=new ArrayList<>();
        this.preparationSteps=new ArrayList<>();
    }
    public void addIngredient(IngredientInRecipe ingredient){
        ingredients.add(ingredient);
    }
    public void addPreparationStep(PreparationStep preparationStep){
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

    public void setIngredients(List<IngredientInRecipe> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recipes recipes = (Recipes) o;
        return Objects.equals(ingredients, recipes.ingredients) && Objects.equals(preparationSteps, recipes.preparationSteps) && Objects.equals(name, recipes.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredients, preparationSteps, name);
    }
}
