package commons;

public class KCAL {

    /**
     *
     * calculates kcal for each ingredient in recipe
     *
     * @param ingredient
     * @return
     */
    public int calculator(IngredientInRecipe ingredient) {
        int kcalPer100g = ingredient.getTempIngredient().getKcalPer100g();
        int quantity = ingredient.getQuantity();
        Unit unit = ingredient.getUnit();

        double grams = convertToGrams(quantity, unit);

        return (int) Math.round((grams / 100.0) * kcalPer100g);

    }

    /**
     *
     * calculates total kcal in recipe
     *
     * @param recipe
     * @return
     */
    public int calculateTotal(Recipes recipe) {
        return  recipe.getIngredients()
                .stream()
                .mapToInt(this::calculator)
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
