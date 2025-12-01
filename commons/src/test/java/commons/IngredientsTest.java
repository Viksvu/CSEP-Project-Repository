package commons;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IngredientsTest {

    @Test
    void constructorShouldSetAllFields() {
        Ingredients i = new Ingredients("salt", 0, "Salt", Unit.GRAM);

        assertEquals("salt", i.getName());
        assertEquals(0, i.getKcalPer100g());
        assertEquals("Salt", i.getIngredient());
        assertEquals(Unit.GRAM, i.getUnit());
    }

    @Test
    void settersShouldUpdateFields() {
        Ingredients i = new Ingredients("pepper", 50, "Pepper", Unit.TABLE_SPOON);

        i.setName("pepper-new");
        i.setKcalPer100g(200);
        i.setIngredient("Black Pepper");
        i.setUnit(Unit.CUP);

        assertEquals("pepper-new", i.getName());
        assertEquals(200, i.getKcalPer100g());
        assertEquals("Black Pepper", i.getIngredient());
        assertEquals(Unit.CUP, i.getUnit());
    }

    @Test
    void equalsShouldReturnTrueForSameValues() {
        Ingredients i1 = new Ingredients("milk", 60, "Milk", Unit.LITER);
        Ingredients i2 = new Ingredients("milk", 60, "Milk", Unit.LITER);

        assertEquals(i1, i2);
        assertEquals(i1.hashCode(), i2.hashCode());
    }

    @Test
    void equalsShouldReturnFalseForDifferentValues() {
        Ingredients i1 = new Ingredients("water", 1, "Water", Unit.MILLILITER);
        Ingredients i2 = new Ingredients("oil", 900, "Oil", Unit.MILLILITER);

        assertNotEquals(i1, i2);
    }

    @Test
    void equalsShouldReturnFalseWhenComparingWithNullOrDifferentClass() {
        Ingredients i = new Ingredients("sugar", 387, "Sugar", Unit.GRAM);

        assertNotNull(i);
        assertNotEquals("not-an-ingredient", i);
    }

    @Test
    void hashCodeShouldBeConsistent() {
        Ingredients i = new Ingredients("oil", 884, "Olive Oil", Unit.GRAM);

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
        Ingredients zero = new Ingredients("water", 0, "Water", Unit.MILLILITER);
        Ingredients negative = new Ingredients("salt", -5, "Salt", Unit.GRAM);

        assertEquals(0, zero.getKcalPer100g());
        assertEquals(-5, negative.getKcalPer100g());
    }

    @Test
    void nameAndIngredientCanBeEmptyString() {
        Ingredients i = new Ingredients("", 0, "", Unit.NULL);

        assertEquals("", i.getName());
        assertEquals("", i.getIngredient());
    }

    @Test
    void unitCanBeNull() {
        Ingredients i = new Ingredients("milk", 50, "Milk", null);
        assertNull(i.getUnit());
    }

    @Test
    void equalsShouldReturnFalseForDifferentIds() {
        Ingredients i1 = new Ingredients("sugar", 300, "Sugar", Unit.GRAM);
        Ingredients i2 = new Ingredients("sugar", 300, "Sugar", Unit.GRAM);

        i1.setName("sugar1");
        assertNotEquals(i1, i2);
    }

    @Test
    void hashCodeShouldChangeIfFieldChanges() {
        Ingredients i = new Ingredients("oil", 884, "Olive Oil", Unit.MILLILITER);
        int originalHash = i.hashCode();
        i.setUnit(Unit.LITER);
        int newHash = i.hashCode();

        assertNotEquals(originalHash, newHash);
    }
}
