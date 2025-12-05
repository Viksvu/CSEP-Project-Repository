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
import commons.PreparationStep;
import commons.IngredientInRecipe;
import commons.Ingredients;


import java.util.Comparator;
import java.util.List;


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
    private Scene shoppingList;

    // This observable list stores the names of all the recipes.
    // <String> might want to be replaced by <Recipe> in
    // the future while also then looking at all its usages.
    private ObservableList<String> recipeObservableList;

    // temporary, to be replaced with recipeObservableList
    private ObservableList<Recipes> tempRecipeList;

    // to use after refactoring
    private FilteredList<Recipes> filteredRecipes;
    private SortedList<Recipes> sortedRecipes;
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
            , Pair<ShoppingListCtrl, Parent> shoppingList
    ) {
        this.primaryStage = primaryStage;
        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());
        // TO CHANGE AFTER REFACTORING
        this.tempRecipeList = FXCollections.observableArrayList();
        this.filteredRecipes=new FilteredList<>(tempRecipeList);
        this.sortedRecipes=new SortedList<>(filteredRecipes);

        this.addCtrl = add.getKey();
        this.add = new Scene(add.getValue());

        this.removeCtrl = remove.getKey();
        this.remove = new Scene(remove.getValue());

        this.addIngredientCtrl = addIngredient.getKey();
        this.addIngredient = new Scene(addIngredient.getValue());
        // MIGHT NEED TO BE MODIFIED AFTER CONNECTION TO SERVER
        this.recipeObservableList = FXCollections.observableArrayList();
        //
        this.shoppingListCtrl=shoppingList.getKey();
        this.shoppingList = new Scene(shoppingList.getValue());
        showOverview();
        primaryStage.show();
    }

    public FilteredList<Recipes> getFilteredRecipes() {
        return filteredRecipes;
    }


    public SortedList<Recipes> getSortedRecipes() {
        return sortedRecipes;
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
     * @param recipe to which the ingredient is being added
     */
    public void showAddIngredient(Recipes recipe) {
        primaryStage.setTitle("Adding Ingredient to: "+recipe.toString());
        primaryStage.setScene(addIngredient);
        addIngredientCtrl.provideRecipe(recipe);
    }

    /**
     * Sets the add ingredient scene as the primary scene
     */
    public void showAddIngredient(ShoppingList shoppingList) {
        primaryStage.setTitle("Adding ingredient");
        primaryStage.setScene(addIngredient);
        addIngredientCtrl.provideShoppingList(shoppingList);
    }


    // EVERYTHING BELOW HAS BEEN REPLACED WITH SERVER-LOGIC
    // IT IS ONLY THERE IN CASE EVER NEEDED FOR DEBUGGING

    /**
     * NEEDS TO BE MODIFIED WITH SERVER-LOGIC.
     * Adds recipe to the ObservableList
     * @param recipeName to add
     */
    @Deprecated
    public void addRecipeToList(String recipeName) {
        recipeObservableList.add(recipeName);
    }

    /**
     * Gets the ObservableList
     * @return the list of recipes stored
     */
    @Deprecated
    public ObservableList<String> getRecipes() {
        return recipeObservableList;
    }

    /**
     * NEEDS TO BE MODIFIED WITH SERVER-LOGIC.
     * Removes recipe from ObservableList
     * @param recipeName - to remove
     */
    @Deprecated
    public void removeRecipeFromList(String recipeName) {
        recipeObservableList.remove(recipeName);
    }

    /**
     *  Applying search filters
     * @param text the text query
     */
    public void applySearchFilter(String text){
        if(text.isEmpty()){
            filteredRecipes.setPredicate(recipes -> true);
            return;
        }
        text=text.toLowerCase();
        final String query=text;
        filteredRecipes.setPredicate(recipes -> {
            if(recipes.getName().toLowerCase().contains(query)) return true;
            List<PreparationStep>
                    preparationSteps=recipes.getPreparationSteps();
            for(int i=0;i<preparationSteps.size();i++){
                if(preparationSteps.
                        get(i).getDescription().toLowerCase().contains(query)) {
                    return true;
                }
            }
            List<IngredientInRecipe> ings=recipes.getIngredients();
            for(int i=0;i<ings.size();i++){
                Ingredients tempIngredient=ings.get(i).getIngredient();
                if(tempIngredient.getName()
                        .toLowerCase().contains(query)
                        || tempIngredient.
                        getName().toLowerCase().contains(query)) {
                    return true;
                }
            }
            return false;
        });
    }

    /**
     * New method to sort through ingredients for applySorting
     * @param ings  the ingredients list for the checked recipe
     * @param text  the query text
     * @return int value showing if it is in there
     */
    public int checkIngs(List<IngredientInRecipe> ings, String text){
        int mx=0;
        for(int i=0;i<ings.size();i++){
            Ingredients tempIngredient =ings.
                    get(i).getIngredient();
            if(tempIngredient.getName().toLowerCase().contains(text)){
                if(tempIngredient.
                        getName().toLowerCase().startsWith(text)){
                    return 2;
                }
                mx=1;
            }
        }
        return mx;
    }

    /**
     * New method to sort through preparation steps for applySorting
     * @param prepSteps the preparation steps for the current recipe
     * @param text the text query
     * @return int value showing if it is there
     */
    public int checkPrepSteps(List<PreparationStep> prepSteps, String text){
        int mx=0;
        for(int i=0;i<prepSteps.size();i++){
            PreparationStep tempPrepStep=prepSteps.get(i);
            if(tempPrepStep.getDescription().toLowerCase().contains(text)){
                if(tempPrepStep.
                        getDescription().toLowerCase().startsWith(text)){
                    return 2;
                }
                mx=1;
            }
        }
        return mx;
    }

    /**
     * New method to sort names for apply sorting
     * @param name the name of the current recipe
     * @param text the query text
     * @return int value showing if it is there
     */
    public int checkName(String name,String text){
        if(name.contains(text)){
            if(name.startsWith(text)){
                return 2;
            }
            return 1;
        }
        return 0;
    }
    /**
     * Do the sorting
     * @param text the text query
     */
    public void applySorting(String text){
        if(text.isEmpty()){
            sortedRecipes.setComparator(
                    Comparator.
                            comparing(Recipes::getName,
                                    String.CASE_INSENSITIVE_ORDER)
            );
            return;
        }
        text=text.toLowerCase();
        final String query=text;
        sortedRecipes.setComparator((r1,r2)->{
            String t1=r1.getName().toLowerCase();
            String t2=r2.getName().toLowerCase();
            if(checkName(t1,query)>checkName(t2,query)) return -1;
            else if(checkName(t1,query)<checkName(t2,query)) return 1;

            int chckVal1=checkIngs(r1.getIngredients(), query);
            int chckVal2=checkIngs(r2.getIngredients(), query);
            if(chckVal1>chckVal2){
                return -1;
            }else if(chckVal1<chckVal2) return 1;

            chckVal1=checkPrepSteps(r1.getPreparationSteps(), query);
            chckVal2=checkPrepSteps(r2.getPreparationSteps(), query);
            if(chckVal1>chckVal2){
                return -1;
            }else if(chckVal1<chckVal2) return 1;

            return 0;

        });
    }

    public ObservableList<Recipes> getRecipeObservableList() {
        return tempRecipeList;
    }

    /**
     * setter for temp recipe list
     * @param recipes list of recipes to set to
     */
    public void setRecipeObservableList(List<Recipes> recipes) {
        // Convert plain list to ObservableList
        this.tempRecipeList = FXCollections.observableArrayList(recipes);

        // Wrap in FilteredList
        this.filteredRecipes = new FilteredList<>(tempRecipeList, r -> true);

        // Wrap filtered list in SortedList
        this.sortedRecipes = new SortedList<>(filteredRecipes);

        // If you have a ListView/TableView in the actual UI:
        // recipeListView.setItems(sortedRecipes);
    }

    /**
     * Show shopping list scene.
     */
    public void showShoppingList() {
        primaryStage.setTitle("Shopping list");
        primaryStage.setScene(shoppingList);

    }

}
