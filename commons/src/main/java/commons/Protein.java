package commons;

public class Protein {

    /**
     *
     * calculates protein for each ingredient in recipe
     *
     * @param ingredient
     * @return
     */
    public double calculate(IngredientInRecipe ingredient){
        double proteinPer100g = ingredient.getIngredient().getProteinPer100g();
        int quantity = ingredient.getQuantity();
        Unit unit = ingredient.getUnit();

        double grams = convertToGrams(quantity, unit);

        return (grams / 100.0) * proteinPer100g;
    }

    /**
     *
     * calculates total protein in recipe
     *
     * @param recipe
     * @return
     */
    public double calculateTotal(Recipes recipe){
        return recipe.getIngredients()
                .stream()
                .mapToDouble(this::calculate)
                .sum();
    }

    /**
     *
     * sets the units to grams
     *
     * @param amount
     * @param unit
     * @return
     */
    private double convertToGrams(double amount, Unit unit) {
        return switch (unit){
            case GRAM -> amount;
            case KILOGRAM -> amount * 1000.0;
            case LITER -> amount * 1000.0;
            case TABLE_SPOON -> amount * 5.0;
            case PINCH -> amount * 0.4;
            case CUP -> amount * 240.0;
            case MILLILITER -> amount;
            case PIECE -> amount * 50.0;
            case NULL -> amount;
            case TEASPOON ->  amount* 15.0;
        };
    }
}
