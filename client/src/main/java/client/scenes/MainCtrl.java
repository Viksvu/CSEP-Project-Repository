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

import client.commonsClient.IngredientInShoppingList;
import client.commonsClient.ShoppingList;
import commons.Recipes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import commons.IngredientInRecipe;



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

    private ShoppingListCtrl shoppingListCtrl;
    private Scene shoppingListScene;

    private OverviewListCtrl overviewListCtrl;
    private Scene overviewList;

    private AddRecipeIngredientsCtrl addRecipeIngredientsCtrl;
    private Scene addRecipeIngredients;

    private EditIngredientCtrl editIngredientCtrl;
    private Scene editIngredient;
    private AddPreparationStepCtrl addPreparationStepCtrl;
    private Scene addPreparationStep;

    // This observable list stores the names of all the recipes.
    // <String> might want to be replaced by <Recipe> in
    // the future while also then looking at all its usages.
    private ObservableList<String> recipeObservableList;

    // temporary, to be replaced with recipeObservableList
    private ObservableList<Recipes> tempRecipeList;

    // to use after refactoring
    private FilteredList<Recipes> filteredRecipes;
    private SortedList<Recipes> sortedRecipes;
    private ShoppingList shoppingList = new ShoppingList();

    /**
     * Initializes the application. Necessary when running
     * for the first time. Initializes the ObservableList.
     *
     * @param primaryStage  The main stage of the application
     * @param overview      The Overview-Scene control
     * @param add           The Add-Scene control
     * @param remove        The remove scene control
     * @param addIngredient The add ingredient scene control
     */
    public void initialize(Stage primaryStage
            , Pair<RecipeOverviewCtrl, Parent> overview
            , Pair<AddRecipeCtrl, Parent> add
            , Pair<RemoveRecipeCtrl, Parent> remove
            , Pair<AddIngredientCtrl, Parent> addIngredient
            , Pair<ShoppingListCtrl, Parent> shoppingList
            , Pair<AddRecipeIngredientsCtrl, Parent> addRecipeIngredientsP,
                           Pair<OverviewListCtrl, Parent> overviewListPair
            , Pair<AddPreparationStepCtrl, Parent> addPreparationStep
    ) {
        this.primaryStage = primaryStage;
        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());
        this.overview.getRoot().setId("overview");
        this.overviewCtrl.setShoppingList(this.shoppingList);

        this.addCtrl = add.getKey();
        this.add = new Scene(add.getValue());

        this.removeCtrl = remove.getKey();
        this.remove = new Scene(remove.getValue());

        this.addIngredientCtrl = addIngredient.getKey();
        this.addIngredient = new Scene(addIngredient.getValue());
        // MIGHT NEED TO BE MODIFIED AFTER CONNECTION TO SERVER
        this.recipeObservableList = FXCollections.observableArrayList();

        this.shoppingListCtrl = shoppingList.getKey();
        this.shoppingListScene = new Scene(shoppingList.getValue());
        this.shoppingListScene.getRoot().setId("shoppingList");
        this.shoppingListCtrl.setShoppingList(this.shoppingList);

        this.overviewListCtrl = overviewListPair.getKey();
        this.overviewList = new Scene(overviewListPair.getValue());
        this.overviewListCtrl.setShoppingList(this.shoppingList);
        this.overviewList.getRoot().setId("overviewList");


        this.addRecipeIngredientsCtrl = addRecipeIngredientsP.getKey();
        this.addRecipeIngredients = new Scene(addRecipeIngredientsP.getValue());
        addRecipeIngredientsCtrl.setShoppingList(this.shoppingList);
        this.addRecipeIngredients.getRoot().setId("addRepIngrs");

        //this.editIngredientCtrl=editIngredientCtrlParentPair.getKey();
        //this.editIngredient=
        // new Scene(editIngredientCtrlParentPair.getValue());
        addIngredientCtrl.provideShoppingList(this.shoppingList);


        this.addPreparationStepCtrl = addPreparationStep.getKey();
        this.addPreparationStep = new Scene(addPreparationStep.getValue());

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
        overview.setOnKeyPressed(e -> overviewCtrl.keyPressed(e));
    }

    /**
     * Sets the add-recipe scene as the primary scene
     */
    public void showAdd() {
        primaryStage.setTitle("Recipes: Adding Recipe");
        primaryStage.setScene(add);
        add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }

    /**
     * Sets the remove-recipe scene as the primary scene
     */
    public void showRemove() {
        removeCtrl.setup();
        primaryStage.setTitle("Recipes: Removing Recipe");
        primaryStage.setScene(remove);
        remove.setOnKeyPressed(e -> removeCtrl.keyPressed(e));
    }

    /**
     * Sets the add ingredient scene as the primary scene
     *
     * @param recipe to which the ingredient is being added
     */
    public void showAddIngredient(Recipes recipe) {
        primaryStage.setTitle("Adding Ingredient to: " + recipe.toString());
        primaryStage.setScene(addIngredient);
        addIngredientCtrl.provideRecipe(recipe);
        addIngredientCtrl.previousSceneSetter(this.overview);

    }

    /**
     * Sets the add ingredient scene as the primary scene
     */
    public void showAddIngredient() {
        addIngredientCtrl.previousSceneSetter(primaryStage.getScene());
        primaryStage.setTitle("Adding ingredient");
        primaryStage.setScene(addIngredient);
    }



    /**
     * Sets the add preparation step scene as the primary scene
     * @param recipe current recipe
     */
    public void showAddPreparationStep(Recipes recipe) {
        if (recipe == null) return;
        primaryStage.setTitle("Adding preparation to: " + recipe.toString());
        primaryStage.setScene(addPreparationStep);
        addPreparationStepCtrl.provideRecipe(recipe);
    }

    // EVERYTHING BELOW HAS BEEN REPLACED WITH SERVER-LOGIC
    // IT IS ONLY THERE IN CASE EVER NEEDED FOR DEBUGGING

    /**
     * NEEDS TO BE MODIFIED WITH SERVER-LOGIC.
     * Adds recipe to the ObservableList
     *
     * @param recipeName to add
     */
    @Deprecated
    public void addRecipeToList(String recipeName) {
        recipeObservableList.add(recipeName);
    }

    /**
     * Gets the ObservableList
     *
     * @return the list of recipes stored
     */
    @Deprecated
    public ObservableList<String> getRecipes() {
        return recipeObservableList;
    }

    /**
     * NEEDS TO BE MODIFIED WITH SERVER-LOGIC.
     * Removes recipe from ObservableList
     *
     * @param recipeName - to remove
     */
    @Deprecated
    public void removeRecipeFromList(String recipeName) {
        recipeObservableList.remove(recipeName);
    }



    /**
     * Show shopping list scene.
     */
    public void showShoppingList() {
        primaryStage.setTitle("Shopping list");
        primaryStage.setScene(shoppingListScene);
        shoppingListCtrl.refresh();

    }

    /**
     * Shows the add recipe ingredients to
     * shopping list overview.
     *
     */
    public void showAddRecipeIngredientsOverview() {

        primaryStage.setTitle("Add recipe ingredients");
        primaryStage.setScene(addRecipeIngredients);
        addRecipeIngredientsCtrl.setChoiceBox(overviewCtrl.getRecipeData());

    }

    /**
     * Displays overview list from the recipe
     * @param recipe the recipe.
     */
    public void showOverviewList(Recipes recipe) {
        overviewListCtrl.previousSceneSetter(primaryStage.getScene());
        primaryStage.setTitle("Adding from \""+recipe.getName()+"\"" +
                " recipe");
        primaryStage.setScene(overviewList);
        overviewListCtrl.clear();

        overviewListCtrl.addIngredients(recipe.getIngredients()
        , recipe);
        overviewListCtrl.refresh();
    }

    /**
     * Displays overview without overwrite
     */
    public void showOverviewList(){
        overviewListCtrl.refresh();
        primaryStage.setScene(overviewList);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Scene getAddScene() {
        return add;
    }

    public Scene getShoppingListScene() {
        return shoppingListScene;
    }

    /**
     * Displays the ingredient editing scene
     * @param ingredient the ingredient to edit
     */
    public void showEditIngredient(IngredientInShoppingList ingredient) {
        editIngredientCtrl.previousSceneSetter(primaryStage.getScene());
        editIngredientCtrl.setIngredient(ingredient);
        primaryStage.setScene(editIngredient);
    }
    /**
     * Displays the ingredient editing scene
     * @param ingredient the ingredient to edit
     */
    public void showEditIngredient(IngredientInRecipe ingredient, Recipes recipe){
        editIngredientCtrl.previousSceneSetter(primaryStage.getScene());
        editIngredientCtrl.setIngredient(ingredient);
        primaryStage.setScene(editIngredient);
        primaryStage.setTitle("Editing ingredient from: " + recipe.toString());
        editIngredientCtrl.provideRecipe(recipe);
    }


}
