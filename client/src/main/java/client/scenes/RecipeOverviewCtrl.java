package client.scenes;

import client.EditButton;
import client.EditButtonOptions;
import client.commonsClient.ShoppingList;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.IngredientInRecipe;
import commons.Ingredients;
import commons.PreparationStep;
import commons.Recipes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.*;

public class RecipeOverviewCtrl implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private  ShoppingList shoppingList;
    ObservableList<Recipes> recipeData;
    ObservableList<Recipes> data1;
    ObservableList<IngredientInRecipe> ingredientsData;
    ObservableList<PreparationStep> preparationStepsData;
    // Button someButton;
    ArrayList<Button> ingredientButtons;

    private static final int NO_MATCH = 0;
    private static final int PARTIAL_MATCH = 1;
    private static final int STARTS_WITH_MATCH = 2;

    @FXML
    public TextField searchField;

    @FXML
    private SplitPane splitPaneRefreshButton;
    @FXML
    private SplitPane splitNameDetails;
    //IMPORTANT: Change String type to Recipe.
    @FXML
    private ListView<Recipes> recipeListView;
    //IMPORTANT: Potential change String type to Ingredient.
    @FXML
    private ListView<IngredientInRecipe> ingredientListView;
    //IMPORTANT: Type change likely
    // not needed in future but usage will have to be modified
    @FXML
    private ListView<PreparationStep> preparationsListView;
    @FXML
    private AnchorPane ingredientsPane;
    @FXML
    private Button addIngredientButton;

    @FXML
    private AnchorPane preparationStepsPane;
    @FXML
    private Button addPreparationStepButton;

    //IMPORTANT: Change String to Recipe
    // ObservableList<String> recipeObservableList;

    @FXML
    private Button addToShop;

    @FXML
    private TextField cloneRecipeNameTF;

    @FXML
    private Label cloneRecipeNameLabel;

    @FXML
    private Button cloneRecipeButton;

    @FXML
    private Button shoppingListButon;

    @FXML
    private Button cloneButton;

    @FXML
    private Button refreshButton;

    @FXML
    private Label mainTitle;

    private boolean isCloning;

    private FilteredList<Recipes> filteredRecipes;
    private SortedList<Recipes> sortedRecipes;
    private Recipes lastSelectedRecipe;

    /**
     * Recipe overview controller constructor.
     *
     * @param mainCtrl main controller.
     */
    @Inject
    public RecipeOverviewCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        //this.recipeObservableList = FXCollections.observableArrayList();
        //splitPaneRefreshButton = new SplitPane();
        this.server = server;
        this.ingredientsData = FXCollections.observableArrayList();
        this.isCloning = false;
        ingredientButtons = new ArrayList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //ObservableList<String> recipeObservableList = mainCtrl.getRecipes();
        //ObservableList<Recipes> recipeList =
        // FXCollections.observableArrayList(server.getRecipes());
        //recipeObservableList.add("Test String 1");
        // Adding anything to the recipeObservableList
        // will also add to the ListView of Recipes
        //recipeListView.setItems(recipeObservableList);
        //recipeListView.setEditable(true);
        mainTitle.setText(resourceBundle.getString("title"));
        shoppingListButon.setText(resourceBundle.getString("shoppingList"));
        addToShop.setText(resourceBundle.getString("shop"));
        refreshButton.setText(resourceBundle.getString("refresh"));
        cloneButton.setText(resourceBundle.getString("clone"));
        cloneRecipeButton.setText(resourceBundle.getString("clone.ok"));
        cloneRecipeNameLabel.setText(resourceBundle.getString("clone.newRecipeName"));

        searchField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                clearSearch();
            }
        });
        recipeData = FXCollections.observableArrayList();
        data1 = FXCollections.observableArrayList();
        this.filteredRecipes = new FilteredList<>(recipeData);
        this.sortedRecipes = new SortedList<>(filteredRecipes);
        cloneRecipeNameLabel.toBack();
        cloneRecipeNameTF.toBack();
        cloneRecipeButton.toBack();
    }

    /**
     * Refreshes the split panes and the content in the ListViews.
     */
    public void refresh() {
        splitPaneRefreshButton.setDividerPosition(0, 0.10090361445783134);
        splitNameDetails.setDividerPosition(0, 0.29797979797979796);
        refreshRecipes();
        if (getSelectedRecipe() != null) {
            lastSelectedRecipe = getSelectedRecipe();
        }
        if(!server.recipeExists(lastSelectedRecipe)) lastSelectedRecipe=null;
        refreshIngredients(lastSelectedRecipe);
        refreshPreparationSteps(lastSelectedRecipe);
        if (isCloning) {
            cloneRecipeNameTF.setText(lastSelectedRecipe.getName() + " copy");
        }
    }



    /**
     * Refreshes the recipes on the client
     */
    private void refreshRecipes() {

        recipeData.setAll(server.getRecipes());

        recipeListView.setItems(sortedRecipes);

    }

    /**
     * Refreshes the ingredients from the current selected currentRecipe
     * @param currentRecipe current selected currentRecipe
     */
    private void refreshIngredients(Recipes currentRecipe) {
        List<IngredientInRecipe> ingredients = Collections.emptyList();
        if (currentRecipe != null) {
            ingredients = server.getIngredientsInRecipe(currentRecipe);
            currentRecipe.setIngredients(ingredients);
        }
        if (ingredients == null) ingredients = Collections.emptyList();
        ingredientsData = FXCollections.observableArrayList(ingredients);
        ingredientListView.setItems(ingredientsData);

        addEditButtonToIngredient();
    }

    /**
     * Refreshes the preparation steps of the current selected currentRecipe
     * @param currentRecipe current selected currentRecipe
     */
    private void refreshPreparationSteps(Recipes currentRecipe) {
        List<PreparationStep> steps = Collections.emptyList();
        if (currentRecipe != null) {
            steps = server.getPreparationSteps(currentRecipe);
        }
        if (steps == null) steps = Collections.emptyList();
        preparationStepsData = FXCollections.observableArrayList(steps);
        preparationsListView.setItems(preparationStepsData);
        addDeleteButtonToPreparationStep();
    }

    /**
     * Adds an edit button next to the name of the ingredient
     */
    public void addEditButtonToIngredient() {
        ingredientsPane.getChildren().clear();
        ingredientsPane.getChildren().addAll(ingredientListView);
        ingredientsPane.getChildren().add(addIngredientButton);
        ingredientsPane.getChildren().add(addToShop);
        if (!ingredientsData.isEmpty()) {
            int numIngredients = ingredientsData.size();
            for (int i = 0; i < numIngredients; i++) {
                EditButton<IngredientInRecipe> editButton =
                        new EditButton<>(
                                ingredientsData.get(i),
                                "delete",
                                i,
                                ingredientListView,
                                server,
                                lastSelectedRecipe,
                                this,
                                EditButtonOptions.REMOVE_INGREDIENT
                        );
                // TO DO: REPLACE EDIT TEXT WITH PENCIL ICON

                ingredientsPane.getChildren().add(editButton);
            }
        }
    }

    /**
     * Adds an edit button next to the name of the ingredient
     */
    public void addDeleteButtonToPreparationStep() {
        preparationStepsPane.getChildren().clear();
        preparationStepsPane.getChildren().addAll(preparationsListView);
        preparationStepsPane.getChildren().add(addPreparationStepButton);
        if (!preparationStepsData.isEmpty()) {
            int numIngredients = preparationStepsData.size();
            for (int i = 0; i < numIngredients; i++) {
                EditButton<PreparationStep> editButton =
                        new EditButton<>(
                                preparationStepsData.get(i),
                                "delete",
                                i,
                                preparationsListView,
                                server,
                                lastSelectedRecipe,
                                this,
                                EditButtonOptions.REMOVE_STEP
                        );
                // TO DO: REPLACE EDIT TEXT WITH PENCIL ICON

                preparationStepsPane.getChildren().add(editButton);
            }
        }
    }

    /**
     * Shows the scene of "add recipe"
     */
    public void addRecipe() {
        mainCtrl.showAdd();
    }

    /**
     * Shows the scene of remove recipe
     */
    public void removeRecipe() {
        mainCtrl.showRemove();
    }

    public void setRecipeData(ObservableList<Recipes> recipeData) {
        this.recipeData = recipeData;
    }

    public SortedList<Recipes> getSortedRecipes() {
        return sortedRecipes;
    }

    public void setSortedRecipes(SortedList<Recipes> sortedRecipes) {
        this.sortedRecipes = sortedRecipes;
    }

    public void setFilteredRecipes(FilteredList<Recipes> filteredRecipes) {
        this.filteredRecipes = filteredRecipes;
    }

    /**
     * Resets the search field and refreshes the view to show all recipes
     */
    private void clearSearch() {
        searchField.clear();
        searchInit();
    }
    /**
     * Adds ab ingredient to the recipe
     */
    public void addIngredient() {
        if(lastSelectedRecipe==null) return;
        mainCtrl.showAddIngredient(lastSelectedRecipe);
    }

    /**
     * Adds a preparationStep to the recipe
     */
    public void addPreparationStep() {
        mainCtrl.showAddPreparationStep(lastSelectedRecipe);
    }

    /**
     * Shows the text field for entering the new recipe name
     */
    public void cloneRecipe() {
        if (lastSelectedRecipe == null) {
            // Show the error scene
        }
        else {
            isCloning = true;
            cloneRecipeNameLabel.toFront();
            cloneRecipeNameTF.toFront();
            cloneRecipeNameTF.setText(lastSelectedRecipe.getName() + " copy");
            cloneRecipeNameTF.setEditable(true);
            cloneRecipeButton.toFront();
        }
    }

    /**
     * Calls the clone recipe method of the server
     * and clones the last selected recipe
     */
    public void okClone() {
        if (cloneRecipeNameTF.getText().isEmpty()) {
            // Show the error scene here
        }
        server.cloneRecipe(lastSelectedRecipe, cloneRecipeNameTF.getText());
        refreshRecipes();
        refreshIngredients(lastSelectedRecipe);
        refreshPreparationSteps(lastSelectedRecipe);
        cloneRecipeNameTF.toBack();
        cloneRecipeNameLabel.toBack();
        cloneRecipeButton.toBack();
        isCloning = false;
    }

    /**
     * Checks which Recipe from the ListView has been
     * selected.
     *
     * @return a recipe object
     */
    public Recipes getSelectedRecipe() {
        Recipes ret = recipeListView
                .getSelectionModel()
                .getSelectedItem();
        if (ret != null) {
            this.lastSelectedRecipe = ret;
        }
        return ret;
    }

    /**
     * Handles recipes being clicked showing corresponding ingredients and
     * preparations steps
     *
     * @param actionEvent the event.
     */

    public void recipeClicked(MouseEvent actionEvent) {
        refresh();
    }

    /**
     * Updates the ingredients section
     *
     * @param recipes the selected recipe will be
     *                passed on as a parameter
     */
    public void updateIngredients(Recipes recipes) {

    }

    /**
     * Updates the preparation steps section
     *
     * @param recipes the selected recipe will e
     *                passed on as a parameter
     */
    public void updatePreparations(Recipes recipes) {
        return;
    }

    /**
     *
     * The button search has been clicked
     */
    public void searchInit() {
        String text = searchField.getText();
        refresh();

        applySearchFilter(text);
        applySorting(text);
        refresh();
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
        final String[] texts=text.split("\\s+");
        filteredRecipes.setPredicate(recipes -> {
            boolean finalcheck=true;
            for(int i1=0;i1<texts.length;i1++) {
                boolean checkIfContains=false;
                String query=texts[i1];
                if (recipes.getName().toLowerCase().contains(query)) checkIfContains=true;
                List<PreparationStep> preparationSteps = recipes.getPreparationSteps();
                for (int i = 0; i < preparationSteps.size(); i++) {
                    if (preparationSteps.get(i).getDescription().toLowerCase().contains(query)) {
                        checkIfContains=true;
                    }
                }
                List<IngredientInRecipe> ings = recipes.getIngredients();
                for (int i = 0; i < ings.size(); i++) {
                    Ingredients tempIngredient = ings.get(i).getIngredient();
                    if (tempIngredient.getName().toLowerCase().contains(query)) {
                        checkIfContains=true;
                    }
                }
                System.out.println(checkIfContains);
                finalcheck=finalcheck&&checkIfContains;
            }
            return finalcheck;
        });
    }

    /**
     * New method to sort through ingredients for applySorting
     *
     * @param ings the ingredients list for the checked recipe
     * @param texts the query text
     * @return int value showing if it is in there
     */
    public int checkIngs(List<IngredientInRecipe> ings, String[] texts) {
        int mx = NO_MATCH;
        for(int i0=0;i0<texts.length;i0++) {
            String text=texts[i0];
            for (int i = 0; i < ings.size(); i++) {
                Ingredients tempIngredient = ings.
                        get(i).getIngredient();
                if (tempIngredient.getName().toLowerCase().contains(text)) {
                    if (tempIngredient.
                            getName().toLowerCase().startsWith(text)) {
                        return STARTS_WITH_MATCH;
                    }
                    mx = PARTIAL_MATCH;
                }
            }
        }
        return mx;
    }

    /**
     * New method to sort through preparation steps for applySorting
     *
     * @param prepSteps the preparation steps for the current recipe
     * @param texts      the text query
     * @return int value showing if it is there
     */
    public int checkPrepSteps(List<PreparationStep> prepSteps, String[] texts) {
        int mx = NO_MATCH;
        for(int i0=0;i0<texts.length;i0++) {
            String text=texts[i0];
            for (int i = 0; i < prepSteps.size(); i++) {
                PreparationStep tempPrepStep = prepSteps.get(i);
                if (tempPrepStep.getDescription().toLowerCase().contains(text)) {
                    if (tempPrepStep.
                            getDescription().toLowerCase().startsWith(text)) {
                        return STARTS_WITH_MATCH;
                    }
                    mx = PARTIAL_MATCH;
                }
            }
        }
        return mx;
    }

    /**
     * New method to sort names for apply sorting
     *
     * @param name the name of the current recipe
     * @param texts the query text
     * @return int value showing if it is there
     */
    public int checkName(String name, String[] texts) {
        for(int i=0;i<texts.length;i++) {
            String text=texts[i];
            if (name.contains(text)) {
                if (name.startsWith(text)) {
                    return STARTS_WITH_MATCH;
                }
                return PARTIAL_MATCH;
            }
        }
        return NO_MATCH;
    }

    /**
     * Do the sorting
     * @param text the text query
     */
    public void applySorting(String text){
        if(text.isEmpty()){
            sortedRecipes.setComparator(
                    Comparator.comparing(Recipes::getName,
                            String.CASE_INSENSITIVE_ORDER)
            );
            return;
        }
        text=text.toLowerCase();
        final String[] texts=text.split("\\s+");
        sortedRecipes.setComparator((r1,r2)->{
            String t1=r1.getName().toLowerCase();
            String t2=r2.getName().toLowerCase();
            if(checkName(t1,texts)>checkName(t2,texts)) return -1;
            else if(checkName(t1,texts)<checkName(t2,texts)) return 1;

            int chckVal1=checkIngs(r1.getIngredients(), texts);
            int chckVal2=checkIngs(r2.getIngredients(), texts);
            if(chckVal1>chckVal2){
                return -1;
            }else if(chckVal1<chckVal2) return 1;

            chckVal1=checkPrepSteps(r1.getPreparationSteps(), texts);
            chckVal2=checkPrepSteps(r2.getPreparationSteps(), texts);
            if(chckVal1>chckVal2){
                return -1;
            }else if(chckVal1<chckVal2) return 1;
            return 0;

        });
    }

    /**
     * Adds selected recipes
     * ingredients to shopping list overview
     */
    public void addToOverViewIngredients() {
        refresh();
        mainCtrl.showOverviewList(lastSelectedRecipe);
    }


    /**
     * Shows the current shopping list
     */
    public void openShoppingList() {
        mainCtrl.showShoppingList();
    }


    public ObservableList<Recipes> getRecipeData () {
        return recipeData;
    }


    public void setShoppingList (ShoppingList shoppingList){
        this.shoppingList = shoppingList;
    }


    /**
     * Decided the behaviour if a key press is detected
     * @param keyEvent (the key pressed)
     */
    public void keyPressed(KeyEvent keyEvent) {
        if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
            if (isCloning) {
                okClone();
            }
        }
    }
}