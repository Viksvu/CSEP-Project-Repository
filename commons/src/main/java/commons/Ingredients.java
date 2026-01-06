package commons;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Ingredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int kcalPer100g;
    private String ingredient;

    private double fatPer100g;
    private double carbsPer100g;
    private double proteinPer100g;


    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL
            , orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<IngredientInRecipe> ingredientInRecipes;


    /**
     * No-args constructor for JPA
     */
    public Ingredients() {
    }

    /**
     *
     * this is the constructor
     *
     * @param name
     * @param kcalPer100g
     */
    public Ingredients(String name, int kcalPer100g,
                       double fatPer100g, double carbsPer100g,
                       double proteinPer100g) {
        this.name = name;
        this.kcalPer100g = kcalPer100g;
        this.ingredient=name;

        this.fatPer100g = fatPer100g;
        this.carbsPer100g = carbsPer100g;
        this.proteinPer100g = proteinPer100g;
    }

    /**
     * this is a constructor
     *
     * @param name       the name
     */
    public Ingredients(String name) {
        this.name = name;
        this.ingredient=name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKcalPer100g() {
        return kcalPer100g;
    }

    public void setKcalPer100g(int kcalPer100g) {
        this.kcalPer100g = kcalPer100g;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public double getFatPer100g() {
        return fatPer100g;
    }

    public void setFatPer100g(double fatPer100g) {
        this.fatPer100g = fatPer100g;
    }

    public double getCarbsPer100g() {
        return carbsPer100g;
    }

    public void setCarbsPer100g(double carbsPer100g) {
        this.carbsPer100g = carbsPer100g;
    }

    public double getProteinPer100g() {
        return proteinPer100g;
    }

    public void setProteinPer100g(double proteinPer100g) {
        this.proteinPer100g = proteinPer100g;
    }

    public List<IngredientInRecipe> getIngredientInRecipes() {
        return ingredientInRecipes;
    }

    public void setIngredientInRecipes(List<IngredientInRecipe> ingredientInRecipes) {
        this.ingredientInRecipes = ingredientInRecipes;
    }

    /**
     * Clones "this" by cloning all the attributes
     * @return the clone
     */
    public Ingredients cloneIngredients() {
        return new Ingredients(this.name, this.kcalPer100g,
        this.fatPer100g, this.carbsPer100g,
                this.proteinPer100g);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ingredients that = (Ingredients) o;
        return kcalPer100g == that.kcalPer100g &&
                Double.compare(fatPer100g, that.fatPer100g) == 0 &&
                Double.compare(carbsPer100g, that.carbsPer100g) == 0 &&
                Double.compare(proteinPer100g, that.proteinPer100g) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(ingredient, that.ingredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, kcalPer100g, ingredient, fatPer100g,
                carbsPer100g, proteinPer100g);
    }
}
