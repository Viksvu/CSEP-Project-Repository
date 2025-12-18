/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import commons.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class    SearchMethodsTest {

    private MainCtrl controller;
    private Recipes r1, r2, r3;

    @BeforeEach
    void setup() {
        controller = new MainCtrl();

        r1 = new Recipes();
        r1.setName("Chicken Soup");
        r1.setPreparationSteps(List.of(
                new PreparationStep("Chop onions"),
                new PreparationStep("Boil chicken")
        ));
        r1.setIngredients(List.of(
                new IngredientInRecipe(new Ingredients("Chicken")),
                new IngredientInRecipe(new Ingredients("Onion"))
        ));

        r2 = new Recipes();
        r2.setName("Chocolate Cake");
        r2.setPreparationSteps(List.of(
                new PreparationStep("Mix chocolate"),
                new PreparationStep("Bake cake")
        ));
        r2.setIngredients(List.of(
                new IngredientInRecipe(new Ingredients("Chocolate")),
                new IngredientInRecipe(new Ingredients("Flour"))
        ));

        r3 = new Recipes();
        r3.setName("Tomato Pasta");
        r3.setPreparationSteps(List.of(
                new PreparationStep("Add tomatoes"),
                new PreparationStep("Boil pasta")
        ));
        r3.setIngredients(List.of(
                new IngredientInRecipe(new Ingredients("Tomato")),
                new IngredientInRecipe(new Ingredients("Pasta"))
        ));

        List<Recipes> baseList = List.of(r1, r2, r3);

        controller.setRecipeObservableList(baseList);
    }


    @Test
    void testCheckNameStartsWith() {
        assertEquals(2, controller.checkName("chicken soup", new String[]{"chi"}));
    }

    @Test
    void testCheckNameContains() {
        assertEquals(1, controller.checkName("chicken soup", new String[]{"ken"}));
    }

    @Test
    void testCheckNameNoMatch() {
        assertEquals(0, controller.checkName("chicken soup", new String[]{"xyz"}));
    }


    @Test
    void testCheckIngsStartsWith() {
        assertEquals(2, controller.checkIngs(r1.getIngredients(), new String[]{"chi"}));
    }

    @Test
    void testCheckIngsContains() {
        assertEquals(1, controller.checkIngs(r1.getIngredients(), new String[]{"nio"}));
    }

    @Test
    void testCheckIngsNoMatch() {
        assertEquals(0, controller.checkIngs(r1.getIngredients(), new String[]{"zzz"}));
    }


    @Test
    void testCheckPrepStepsStartsWith() {
        assertEquals(2, controller.checkPrepSteps(r1.getPreparationSteps(), new String[]{"chop"}));
    }

    @Test
    void testCheckPrepStepsContains() {
        assertEquals(1, controller.checkPrepSteps(r1.getPreparationSteps(), new String[]{"oil"})); // matches "boil"
    }

    @Test
    void testCheckPrepStepsNoMatch() {
        assertEquals(0, controller.checkPrepSteps(r1.getPreparationSteps(), new String[]{"xyz"}));
    }




    @Test
    void testApplySortingStartsWithFirst() {
        controller.applySorting("ch");

        Comparator<? super Recipes> comp = controller.getSortedRecipes().getComparator();

        List<Recipes> sorted =
                List.of(r2, r1).stream()
                        .sorted(comp)
                        .toList();

        assertEquals("Chicken Soup", sorted.get(0).getName());
        assertEquals("Chocolate Cake", sorted.get(1).getName());
    }
    @Test
    void testApplySortingIngredientMatchBeatsPrepMatch() {
        r1.setPreparationSteps(List.of(
                new PreparationStep("Cut vegetables"),
                new PreparationStep("Add tom broth")
        ));

        controller.applySorting("tom");
        Comparator<? super Recipes> comp = controller.getSortedRecipes().getComparator();

        List<Recipes> sorted = List.of(r1, r3).stream()
                .sorted(comp)
                .toList();

        assertEquals("Tomato Pasta", sorted.get(0).getName());
        assertEquals("Chicken Soup", sorted.get(1).getName());
    }

    @Test
    void testApplySortingEmptyQuery() {
        controller.applySorting("");

        Comparator<? super Recipes> comp = controller.getSortedRecipes().getComparator();

        List<Recipes> sorted = List.of(r1, r2, r3).stream()
                .sorted(comp)
                .toList();

        assertEquals("Chicken Soup", sorted.get(0).getName());
        assertEquals("Chocolate Cake", sorted.get(1).getName());
        assertEquals("Tomato Pasta", sorted.get(2).getName());
    }
}