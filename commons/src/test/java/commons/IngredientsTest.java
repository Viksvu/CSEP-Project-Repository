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

    @Test
    void noArgsConstructorShouldCreateObject() {
        Ingredients i = new Ingredients();
        assertNotNull(i);
    }

    @Test
    void quantityCanBeZeroOrNegative() {
        Ingredients zero = new Ingredients("water", 0.0, "Water", "ml");
        Ingredients negative = new Ingredients("salt", -5.0, "Salt", "gram");

        assertEquals(0.0, zero.getQuantity(), 0.00001);
        assertEquals(-5.0, negative.getQuantity(), 0.00001);
    }

    @Test
    void nameAndIngredientCanBeEmptyString() {
        Ingredients i = new Ingredients("", 1.0, "", "unit");

        assertEquals("", i.getName());
        assertEquals("", i.getIngredient());
    }

    @Test
    void unitCanBeNull() {
        Ingredients i = new Ingredients("milk", 1.0, "Milk", null);
        assertNull(i.getUnit());
    }

    @Test
    void equalsShouldReturnFalseForDifferentIds() {
        Ingredients i1 = new Ingredients("sugar", 1.0, "Sugar", "gram");
        Ingredients i2 = new Ingredients("sugar", 1.0, "Sugar", "gram");

        i1.setName("sugar1"); // slight change to differentiate
        assertNotEquals(i1, i2);
    }

    @Test
    void hashCodeShouldChangeIfFieldChanges() {
        Ingredients i = new Ingredients("oil", 1.0, "Olive Oil", "ml");
        int originalHash = i.hashCode();
        i.setUnit("liter");
        int newHash = i.hashCode();

        assertNotEquals(originalHash, newHash);
    }
}
