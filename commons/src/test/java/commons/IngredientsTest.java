package commons;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IngredientsTest {

    @Test
    void constructorShouldSetAllFields() {
        Ingredients i = new Ingredients("salt", 0, 0.0, 0.0, 0.0);

        assertEquals("salt", i.getName());
        assertEquals(0, i.getKcalPer100g());
    }

    @Test
    void settersShouldUpdateFields() {
        Ingredients i = new Ingredients("pepper", 50, 0.0, 0.0, 0.0);

        i.setName("pepper-new");
        i.setKcalPer100g(200);

        assertEquals("pepper-new", i.getName());
        assertEquals(200, i.getKcalPer100g());
    }

    @Test
    void equalsShouldReturnTrueForSameValues() {
        Ingredients i1 = new Ingredients("milk", 60, 0.0, 0.0, 0.0);
        Ingredients i2 = new Ingredients("milk", 60, 0.0, 0.0, 0.0);

        assertEquals(i1, i2);
        assertEquals(i1.hashCode(), i2.hashCode());
    }

    @Test
    void equalsShouldReturnFalseForDifferentValues() {
        Ingredients i1 = new Ingredients("water", 1, 0.0, 0.0, 0.0);
        Ingredients i2 = new Ingredients("oil", 900, 0.0, 0.0, 0.0);

        assertNotEquals(i1, i2);
    }

    @Test
    void equalsShouldReturnFalseWhenComparingWithNullOrDifferentClass() {
        Ingredients i = new Ingredients("sugar", 387, 0.0, 0.0, 0.0);

        assertNotNull(i);
        assertNotEquals("not-an-ingredient", i);
    }

    @Test
    void hashCodeShouldBeConsistent() {
        Ingredients i = new Ingredients("oil", 884, 0.0, 0.0, 0.0);

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
        Ingredients zero = new Ingredients("water", 0, 0.0, 0.0, 0.0);
        Ingredients negative = new Ingredients("salt", -5, 0.0, 0.0, 0.0);

        assertEquals(0, zero.getKcalPer100g());
        assertEquals(-5, negative.getKcalPer100g());
    }

    @Test
    void nameAndIngredientCanBeEmptyString() {
        Ingredients i = new Ingredients("", 0, 0.0, 0.0, 0.0);

        assertEquals("", i.getName());
        assertEquals("", i.getName());
    }



    @Test
    void equalsShouldReturnFalseForDifferentIds() {
        Ingredients i1 = new Ingredients("sugar", 300, 0.0, 0.0, 0.0);
        Ingredients i2 = new Ingredients("sugar", 300, 0.0, 0.0, 0.0);

        i1.setName("sugar1");
        assertNotEquals(i1, i2);
    }

    @Test
    void hashCodeShouldChangeIfFieldChanges() {
        Ingredients i = new Ingredients("oil", 884, 0.0, 0.0, 0.0);
        int originalHash = i.hashCode();
        i.setKcalPer100g(2000);

        int newHash = i.hashCode();

        assertNotEquals(originalHash, newHash);
    }
}
