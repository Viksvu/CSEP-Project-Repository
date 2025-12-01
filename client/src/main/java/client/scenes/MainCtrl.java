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

import commons.Recipes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;

    private Scene overview;
    private RecipeOverviewCtrl overviewCtrl;

    private AddRecipeCtrl addCtrl;
    private Scene add;

    private RemoveRecipeCtrl removeCtrl;
    private Scene remove;

    private AddIngredientCtrl addIngredientCtrl;
    private Scene addIngredient;

    // This observable list stores the names of all the recipes.
    // <String> might want to be replaced by <Recipe> in
    // the future while also then looking at all its usages.
    private ObservableList<String> recipeObservableList;

    /**
     * Initializes the application. Necessary when running
     * for the first time. Initializes the ObservableList.
     * @param primaryStage The main stage of the application
     * @param overview The Overview-Scene control
     * @param add The Add-Scene control
     * @param remove The remove scene control
     * @param addIngredient The add ingredient scene control
     */
    public void initialize(Stage primaryStage
            , Pair<RecipeOverviewCtrl, Parent> overview
            , Pair<AddRecipeCtrl, Parent> add
            , Pair<RemoveRecipeCtrl, Parent> remove
            , Pair<AddIngredientCtrl, Parent> addIngredient
    ) {
        this.primaryStage = primaryStage;
        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.addCtrl = add.getKey();
        this.add = new Scene(add.getValue());

        this.removeCtrl = remove.getKey();
        this.remove = new Scene(remove.getValue());

        this.addIngredientCtrl = addIngredient.getKey();
        this.addIngredient = new Scene(addIngredient.getValue());
        // MIGHT NEED TO BE MODIFIED AFTER CONNECTION TO SERVER
        this.recipeObservableList = FXCollections.observableArrayList();

        showOverview();
        primaryStage.show();
    }

    /**
     * Sets the recipe-overview scene as the primary scene
     */
    public void showOverview() {
        primaryStage.setTitle("Recipes: Overview");
        primaryStage.setScene(overview);
        overviewCtrl.refresh();
    }

    /**
     * Sets the add-recipe scene as the primary scene
     */
    public void showAdd() {
        primaryStage.setTitle("Recipes: Adding Recipe");
        primaryStage.setScene(add);
//        add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }

    /**
     * Sets the remove-recipe scene as the primary scene
     */
    public void showRemove() {
        removeCtrl.setup();
        primaryStage.setTitle("Recipes: Removing Recipe");
        primaryStage.setScene(remove);
    }

    /**
     * Sets the add ingredient scene as the primary scene
     * @param recipe to which the ingredient is being added
     */
    public void showAddIngredient(Recipes recipe) {
        primaryStage.setTitle("Adding Ingredient to: "+recipe.toString());
        primaryStage.setScene(addIngredient);
        addIngredientCtrl.provideRecipe(recipe);
    }

    // EVERYTHING BELOW NEEDS TO BE REPLACED WITH SERVER-LOGIC

    /**
     * NEEDS TO BE MODIFIED WITH SERVER-LOGIC.
     * Adds recipe to the ObservableList
     * @param recipeName to add
     */
    public void addRecipeToList(String recipeName) {
        recipeObservableList.add(recipeName);
    }

    /**
     * Gets the ObservableList
     * @return the list of recipes stored
     */
    public ObservableList<String> getRecipes() {
        return recipeObservableList;
    }

    /**
     * NEEDS TO BE MODIFIED WITH SERVER-LOGIC.
     * Removes recipe from ObservableList
     * @param recipeName - to remove
     */
    public void removeRecipeFromList(String recipeName) {
        recipeObservableList.remove(recipeName);
    }
}
