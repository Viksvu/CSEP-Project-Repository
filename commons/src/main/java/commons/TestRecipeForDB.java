package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TestRecipeForDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int cookingTime;

    @ElementCollection
    private List<String> ingredients;

    /**
     * temp
     */
    public TestRecipeForDB() {
        // for object mapper
    }

    /**
     * temp
     *
     * @param name        temp
     * @param description temp
     * @param cookingTime temp.
     */
    public TestRecipeForDB(String name, String description, int cookingTime) {
        this.name = name;
        this.description = description;
        this.cookingTime = cookingTime;
        this.ingredients = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    /**
     * temp
     *
     * @param ingredients temp.
     */
    public void addIngredients(String ingredients) {
        this.ingredients.add(ingredients);
    }

    /**
     * temp
     *
     * @param ingredients temp.
     */
    public void addAllIngredients(ArrayList<String> ingredients) {
        this.ingredients.addAll(ingredients);
    }

    public Long getId() {
        return id;
    }
}
