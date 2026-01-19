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
import client.utils.WebSocketUtils;
import com.google.inject.Inject;
import commons.PreparationStep;
import commons.Printable;
import commons.Recipes;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import commons.IngredientInRecipe;


import java.util.Map;


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

    private EditPreparationStepCtrl editPreparationStepCtrl;
    private Scene editPreparationStep;

    private SaveRecipeCtrl saveRecipeCtrl;
    private Scene saveRecipe;

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
    private WebSocketUtils webSocketUtils;

    /**
     * Injects the websocket utils
     * to the main ctrl and runs it
     *
     * @param webSocketUtils
     */
    @Inject
    public MainCtrl(WebSocketUtils webSocketUtils) {
        this.webSocketUtils = webSocketUtils;
        this.webSocketUtils.connect(this::handleWebSocketMessage);
    }


    /**
     * No arg constructor for test.
     */
    public MainCtrl() {

    }

    /**
     * Initializes the main control
     * and give access to all the other controllers
     *
     * @param primaryStage the main stage.
     * @param sceneMap     a map with all the
     *                     controller, parent pairs
     */
    public void initialize(
            Stage primaryStage,
            Map<String, Pair<?, Parent>> sceneMap
    ) {
        this.primaryStage = primaryStage;

        Pair<?, Parent> overviewPair = sceneMap.get("overview");
        this.overviewCtrl = (RecipeOverviewCtrl) overviewPair.getKey();
        this.overview = new Scene(overviewPair.getValue());
        this.overview.getRoot().setId("overview");
        this.overviewCtrl.setShoppingList(this.shoppingList);

        Pair<?, Parent> addPair = sceneMap.get("addRecipe");
        this.addCtrl = (AddRecipeCtrl) addPair.getKey();
        this.add = new Scene(addPair.getValue());
        this.add.getRoot().setId("addRecipe");


        Pair<?, Parent> removePair = sceneMap.get("removeRecipe");
        this.removeCtrl = (RemoveRecipeCtrl) removePair.getKey();
        this.remove = new Scene(removePair.getValue());
        this.remove.getRoot().setId("removeRecipe");


        Pair<?, Parent> addIngredientPair = sceneMap.get("addIngredient");
        this.addIngredientCtrl = (AddIngredientCtrl) addIngredientPair.getKey();
        this.addIngredient = new Scene(addIngredientPair.getValue());

        Pair<?, Parent> saveRecipePair = sceneMap.get("saveRecipe");
        this.saveRecipeCtrl = (SaveRecipeCtrl) saveRecipePair.getKey();
        this.saveRecipe = new Scene(saveRecipePair.getValue());


        Pair<?, Parent> shoppingListPair = sceneMap.get("shoppingList");
        this.shoppingListCtrl = (ShoppingListCtrl) shoppingListPair.getKey();
        this.shoppingListScene = new Scene(shoppingListPair.getValue());
        this.shoppingListScene.getRoot().setId("shoppingList");
        this.shoppingListCtrl.setShoppingList(this.shoppingList);

        Pair<?, Parent> overviewListPair = sceneMap.get("overviewList");
        this.overviewListCtrl = (OverviewListCtrl) overviewListPair.getKey();
        this.overviewList = new Scene(overviewListPair.getValue());
        this.overviewList.getRoot().setId("overviewList");
        this.overviewListCtrl.setShoppingList(this.shoppingList);

        Pair<?, Parent> addRecipeIngredientsPair =
                sceneMap.get("addRecipeIngredients");
        this.addRecipeIngredientsCtrl =
                (AddRecipeIngredientsCtrl) addRecipeIngredientsPair.getKey();
        this.addRecipeIngredients =
                new Scene(addRecipeIngredientsPair.getValue());
        this.addRecipeIngredients.getRoot().setId("addRepIngrs");
        this.addRecipeIngredientsCtrl.setShoppingList(this.shoppingList);

        Pair<?, Parent> addPreparationStepPair =
                sceneMap.get("addPreparationStep");
        this.addPreparationStepCtrl =
                (AddPreparationStepCtrl) addPreparationStepPair.getKey();
        this.addPreparationStep =
                new Scene(addPreparationStepPair.getValue());

        Pair<?, Parent> editIngredientPair =
                sceneMap.get("editIngredient");
        this.editIngredientCtrl =
                (EditIngredientCtrl) editIngredientPair.getKey();
        this.editIngredient =
                new Scene(editIngredientPair.getValue());

        Pair<?, Parent> editPreparationStepPair =
                sceneMap.get("editPreparationStep");
        this.editPreparationStepCtrl =
                (EditPreparationStepCtrl) editPreparationStepPair.getKey();
        this.editPreparationStep =
                new Scene(editPreparationStepPair.getValue());

        this.recipeObservableList = FXCollections.observableArrayList();
        this.addIngredientCtrl.provideShoppingList(this.shoppingList);

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
     * Sets the save recipe scene as the primary scene
     */
    public void showSaveRecipe(Printable thing) {
        saveRecipeCtrl.provideThing(thing);
        if (thing instanceof Recipes) {
            primaryStage.setTitle("Saving Recipe");
        } else if (thing instanceof ShoppingList) {
            primaryStage.setTitle("Saving Shopping List");
        } else {
            primaryStage.setTitle("Saving");
        }
        primaryStage.setScene(saveRecipe);
        saveRecipe.setOnKeyPressed(k -> saveRecipeCtrl.onKeyPressed(k));
    }


    /**
     * Sets the add preparation step scene as the primary scene
     *
     * @param recipe current recipe
     */
    public void showAddPreparationStep(Recipes recipe) {
        if (recipe == null) return;
        primaryStage.setTitle("Adding preparation to: " + recipe.toString());
        primaryStage.setScene(addPreparationStep);
        addPreparationStepCtrl.provideRecipe(recipe);
    }

    /**
     * Sets the edit preparation step scene as the primary scene
     *
     * @param recipe current recipe
     */
    public void showEditPreparationStep(Recipes recipe, PreparationStep preparationStep) {
        if (recipe == null) return;
        primaryStage.setTitle("Editing preparation from: " + recipe.toString());
        primaryStage.setScene(editPreparationStep);
        editPreparationStepCtrl.provideRecipe(recipe);
        editPreparationStepCtrl.providePrepStep(preparationStep);
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
     */
    public void showAddRecipeIngredientsOverview() {

        primaryStage.setTitle("Add recipe ingredients");
        primaryStage.setScene(addRecipeIngredients);
        addRecipeIngredientsCtrl.setChoiceBox(overviewCtrl.getRecipeData());

    }

    /**
     * Displays overview list from the recipe
     *
     * @param recipe the recipe.
     */
    public void showOverviewList(Recipes recipe) {
        overviewListCtrl.previousSceneSetter(primaryStage.getScene());
        primaryStage.setTitle("Adding from \"" + recipe.getName() + "\"" +
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
    public void showOverviewList() {
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
     *
     * @param ingredient the ingredient to edit
     */
    public void showEditIngredient(IngredientInShoppingList ingredient) {
        editIngredientCtrl.previousSceneSetter(primaryStage.getScene());
        editIngredientCtrl.setIngredient(ingredient);
        primaryStage.setScene(editIngredient);
    }

    /**
     * Displays the ingredient editing scene
     *
     * @param ingredient the ingredient to edit
     */
    public void showEditIngredient(IngredientInRecipe ingredient, Recipes recipe) {
        editIngredientCtrl.previousSceneSetter(primaryStage.getScene());
        editIngredientCtrl.setIngredient(ingredient);
        primaryStage.setScene(editIngredient);
        primaryStage.setTitle("Editing ingredient from: " + recipe.toString());
        editIngredientCtrl.provideRecipe(recipe);
    }

    /**
     * Handles incoming web socket message
     *
     * @param message
     */
    private void handleWebSocketMessage(String message) {
        long id = Long.parseLong(message.split(":")[1]);

        if (message.startsWith("RECIPE_UPDATED")) {
            if (primaryStage.getScene()
                    .getRoot().getId()!=null && primaryStage.getScene()
                    .getRoot().getId().equals("overview")) {
                refreshCurrentRecipeContent(id);
            }
        } else if (message.startsWith("RECIPE_DELETED")) {
            deleteRecipeFromListViews(id);
        } else if (message.startsWith("RECIPE_ADDED")) {
            addRecipeToListViews(id);
        }
    }

    /**
     * Refreshes current recipe content
     *
     * @param id
     */
    private void refreshCurrentRecipeContent(long id) {
        Platform.runLater(() -> {
            overviewCtrl.refreshIfCurrent(id);
            overviewCtrl.refreshRecipeName(id);
        });
    }

    /**
     * deletes the recipe
     * with the id
     * that is sent from the websocket
     * in the correct scene
     *
     * @param id
     */
    public void deleteRecipeFromListViews(long id) {
        Platform.runLater(() -> {
            if(primaryStage.getScene()
                    .getRoot().getId()!=null) {
                if (primaryStage.getScene()
                        .getRoot().getId().equals("overview")) {
                    overviewCtrl.removeRecipeFromListView(id);
                } else if (primaryStage.getScene()
                        .getRoot().getId().equals("deleteRecipe")) {
                }
            }
        });
    }

    /**
     * adds the recipe
     * with the id
     * that is sent from the websocket
     * in the correct scene
     *
     * @param id
     */
    public void addRecipeToListViews(long id) {
        Platform.runLater(() -> {
                    if(primaryStage.getScene()
                            .getRoot().getId()!=null) {
                        if (primaryStage.getScene()
                                .getRoot().getId().equals("overview")) {
                            overviewCtrl.addRecipeToListView(id);
                        } else if (primaryStage.getScene()
                                .getRoot().getId().equals("deleteRecipe")) {
                        }
                    }
        });
    }


    /**
     * Sends to ws client endpoint what to subscribe to right now
     *
     * @param id the id of the recipe to subscribe
     */
    public void sendToWSEndpoint(long id) {
        webSocketUtils.send("VIEW_RECIPE:" + id);
    }


}
