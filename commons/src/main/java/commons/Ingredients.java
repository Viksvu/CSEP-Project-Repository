package commons;

import jakarta.persistence.*;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL
            , orphanRemoval = true)
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
    public Ingredients(String name, int kcalPer100g) {
        this.name = name;
        this.kcalPer100g = kcalPer100g;
        this.ingredientInRecipes = new ArrayList<>();
        this.ingredient=name;
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
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ingredients that = (Ingredients) o;
        return kcalPer100g == that.kcalPer100g &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, kcalPer100g);
    }
}
