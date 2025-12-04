package commons;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Ingredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int kcalPer100g;
    private String ingredient;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    /**
     * No-args constructor for JPA
     */
    protected Ingredients() {
    }

    /**
     *
     * this is the constructor
     * @param name
     * @param kcalPer100g
     * @param ingredient
     * @param unit
     */
    public Ingredients(String name, int kcalPer100g, String ingredient, Unit unit) {
        this.name = name;
        this.kcalPer100g = kcalPer100g;
        this.ingredient = ingredient;
        this.unit = unit;
    }

    /**
     * this is a constructor
     * @param name the name
     * @param ingredient the ingredient
     */
    public Ingredients(String name, String ingredient) {
        this.name = name;
        this.ingredient = ingredient;
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

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ingredients that = (Ingredients) o;
        return kcalPer100g == that.kcalPer100g &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(ingredient, that.ingredient) &&
                unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, kcalPer100g, ingredient, unit);
    }
}
