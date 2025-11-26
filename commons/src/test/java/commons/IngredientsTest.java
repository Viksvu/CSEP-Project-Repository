package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientsTest {

    @Test
    void constructorShouldSetAllFields() {
        Ingredients i = new Ingredients("salt", 2.0, "Salt", "gram");

        assertEquals("salt", i.getName());
        assertEquals(2.0, i.getQuantity(), 0.00001);
        assertEquals("Salt", i.getIngredient());
        assertEquals("gram", i.getUnit());
    }

    @Test
    void settersShouldUpdateFields() {
        Ingredients i = new Ingredients("pepper", 1.0, "Pepper", "tbsp");

        i.setName("pepper-new");
        i.setQuantity(3.0);
        i.setIngredient("Black Pepper");
        i.setUnit("cup");

        assertEquals("pepper-new", i.getName());
        assertEquals(3.0, i.getQuantity(), 0.00001);
        assertEquals("Black Pepper", i.getIngredient());
        assertEquals("cup", i.getUnit());
    }

    @Test
    void equalsShouldReturnTrueForSameValues() {
        Ingredients i1 = new Ingredients("milk", 1.0, "Milk", "liter");
        Ingredients i2 = new Ingredients("milk", 1.0, "Milk", "liter");

        assertEquals(i1, i2);
        assertEquals(i1.hashCode(), i2.hashCode());
    }

    @Test
    void equalsShouldReturnFalseForDifferentValues() {
        Ingredients i1 = new Ingredients("water", 1.0, "Water", "ml");
        Ingredients i2 = new Ingredients("water", 2.0, "Water", "ml");

        assertNotEquals(i1, i2);
    }

    @Test
    void equalsShouldReturnFalseWhenComparingWithNullOrDifferentClass() {
        Ingredients i = new Ingredients("sugar", 1.0, "Sugar", "gram");

        assertFalse(i.equals(null));
        assertFalse(i.equals("not-an-ingredient"));
    }

    @Test
    void hashCodeShouldBeConsistent() {
        Ingredients i = new Ingredients("oil", 1.0, "Olive Oil", "ml");

        int h1 = i.hashCode();
        int h2 = i.hashCode();

        assertEquals(h1, h2);
    }
}

