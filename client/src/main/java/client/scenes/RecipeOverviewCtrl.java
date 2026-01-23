package client.scenes;

import client.EditButton;
import client.EditButtonOptions;
import client.commonsClient.*;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.OverrunStyle;
import javafx.scene.text.Font;


import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RecipeOverviewCtrl implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private  ShoppingList shoppingList;
    private ObservableList<Recipes> recipeData;
    private ObservableList<Recipes> data1;
    private final ConfigHolder configHolder=new ConfigHolder();
    private HashSet<Long> favorites;
    private ResourceBundle bundle;
    private HashSet<RecipeLanguage> langFilters;
    private ObservableList<IngredientInRecipe> ingredientsData;
    protected ObservableList<PreparationStep> preparationStepsData;
    private static final File STARREDFILE =
            new File("starred_recipes.txt");

    // Button someButton;
    public ArrayList<Button> ingredientButtons;


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
    private ToggleButton star;
    @FXML
    private ToggleButton favSort;
    @FXML
    private AnchorPane preparationStepsPane;
    @FXML
    private Button addPreparationStepButton;

    //IMPORTANT: Change String to Recipe
    // ObservableList<String> recipeObservableList;

    @FXML
    private Button addToShop;

    @FXML
    private TextField recipeNameTF;

    @FXML
    private Label recipeNameLabel;

    @FXML
    private Button cloneRecipeButton;

    @FXML
    private Button shoppingListButon;

    @FXML
    private Button cloneButton;

    @FXML
    private Button refreshButton;

    @FXML
    private Button renameRecipeButton;

    @FXML
    private Label mainTitle;

    @FXML
    private Button downloadRecipeButton;

    @FXML
    private ComboBox<LanguageObject> languageDropDown;

    @FXML
    private CheckMenuItem filterEnglish;

    @FXML
    private CheckMenuItem filterDutch;

    @FXML
    private CheckMenuItem filterGerman;

    @FXML
    private CheckMenuItem filterSpanish;

    @FXML
    private MenuButton languageFilterMenu;

    @FXML
    private AnchorPane topAnchorPane;
    private boolean isCloning;

    private FilteredList<Recipes> filteredRecipes;
    private SortedList<Recipes> sortedRecipes;
    private Recipes lastSelectedRecipe;
    private Predicate<Recipes> searchFilter=(Recipes r)->true;
    private Predicate<Recipes> favFilter=
            (Recipes r)->true;
    private Predicate<Recipes> langFilter = (Recipes r)->true;

    private boolean isRenaming;

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
        this.isRenaming = false;
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
        favorites=new HashSet<>();
        langFilters=new HashSet<>();
        this.bundle=resourceBundle;
        setLanguageDropDown(resourceBundle);
        searchField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                clearSearch();
            }
        });
        recipeData = FXCollections.observableArrayList();
        data1 = FXCollections.observableArrayList();
        this.filteredRecipes = new FilteredList<>(recipeData);
      //  filteredRecipes.setPredicate(searchFilter);
        this.sortedRecipes = new SortedList<>(filteredRecipes);
        recipeNameLabel.toBack();
        recipeNameTF.toBack();
        cloneRecipeButton.toBack();
        Scanner scanner= null;
        if (!STARREDFILE.exists()) {
            try {
                File parent = STARREDFILE.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                STARREDFILE.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Failed to create starred file", e);
            }
        }
        try {
            scanner = new Scanner(STARREDFILE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while(scanner.hasNextLong()){
            favorites.add(scanner.nextLong());
        }
        getLangs();

        addStarsToRecipeListView();
        ImageView iv = new ImageView(new Image(
                getClass().getResourceAsStream("/pictures/img.png")));
        iv.setFitHeight(20);
        iv.setFitWidth(20);
        renameRecipeButton.setGraphic(iv);
        doubleClickToFavorite();
        Font.loadFont(
                getClass().getResourceAsStream("/client/" +
                        "scenes/stylesheets/fonts/" +
                        "Poppins/Poppins-Regular.ttf"),
                12
        );
        ClientConfig cfg = configHolder.get();
        Locale locale = Locale.forLanguageTag(cfg.getLocale());

        ResourceBundle newBundle =
                ResourceBundle.getBundle("languageBundles.messages", locale);

        updateLanguage(newBundle);

    }

    /**
     * Selects the first recipe if nothing is selected
     */
    private void selectFirstRecipeIfNoneSelected() {
        ObservableList<Recipes> items = recipeListView.getItems();
        if (items == null || items.isEmpty()) {
            lastSelectedRecipe = null;
            return;
        }
        if (recipeListView.getSelectionModel().getSelectedItem() == null) {
            recipeListView.getSelectionModel().select(0);
        }

        lastSelectedRecipe = recipeListView.getSelectionModel().getSelectedItem();
    }
    /**
     * Gets language filters from the config file
     */
    public void getLangs(){
        ClientConfig config = configHolder.get();
        langFilters.clear();
        filterEnglish.setSelected(false);
        filterSpanish.setSelected(false);
        filterGerman.setSelected(false);
        filterDutch.setSelected(false);
        for (String code : config.getRecipeLanguageFilters()) {
            switch (code) {
                case "en" -> {
                    filterEnglish.setSelected(true);
                    langFilters.add(RecipeLanguage.ENGLISH);
                }
                case "de" -> {
                    filterGerman.setSelected(true);
                    langFilters.add(RecipeLanguage.GERMAN);
                }
                case "nl" -> {
                    filterDutch.setSelected(true);
                    langFilters.add(RecipeLanguage.DUTCH);
                }
                case "es" -> {
                    filterSpanish.setSelected(true);
                    langFilters.add(RecipeLanguage.SPANISH);
                }
            }
        }
        applyLang();
    }
    /**
     * Method to be called in initialise, allows for recipes to be
     * favorited by double-clicking on them
     */
    public void doubleClickToFavorite(){
        recipeListView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount()==2) {
                Recipes selected=recipeListView
                        .getSelectionModel()
                        .getSelectedItem();
                if (selected==null) return;
                Long id=selected.getId();
                if (favorites.contains(id)) removeFavRecipeId(id);
                else addFavRecipeId(id);
                star.setSelected(favorites.contains(id));
                star.setText(star.isSelected() ? "★" : "☆");
                recipeListView.refresh();
            }
        });
    }

    /**
     * Test class for now
     * @return ne hash set
     */
    private Set<String> getSelectedLanguages() {
        return new HashSet<>();
    }
    /**
     * This method is responsible for rendering the Language Drop Down Menu
     */
    public void setLanguageDropDown(ResourceBundle resourceBundle) {
        languageDropDown.getItems().addAll(
                new LanguageObject(
                        Locale.forLanguageTag("en"),
                        new Image(getClass().getResourceAsStream("/icons/en.png"))),
                new LanguageObject(
                        Locale.forLanguageTag("es"),
                        new Image(getClass().getResourceAsStream("/icons/es.png"))),
                new LanguageObject(
                        Locale.forLanguageTag("nl"),
                        new Image(getClass().getResourceAsStream("/icons/nl.png"))),
                new LanguageObject(
                        Locale.forLanguageTag("de"),
                        new Image(getClass().getResourceAsStream("/icons/de.png")))
        );
        languageDropDown.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(LanguageObject languageObject, boolean empty) {
                super.updateItem(languageObject, empty);
                if (empty || languageObject == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    ImageView imageView = new ImageView(languageObject.getFlag());
                    imageView.setFitHeight(16);
                    imageView.setFitWidth(24);
                    setGraphic(imageView);
                    setAlignment(Pos.CENTER);
                }
            }
        });
        languageDropDown.setButtonCell(languageDropDown.getCellFactory().call(null));
        String cfgLocale = configHolder.get().getLocale();
        Locale localeFromConfig = Locale.forLanguageTag(cfgLocale);

        for (LanguageObject languageObject : languageDropDown.getItems()) {
            if (languageObject.getLocale().equals(localeFromConfig)) {
                languageDropDown.getSelectionModel().select(languageObject);
                break;
            }
        }

        languageDropDown.valueProperty().addListener((obs, oldLang, newLang) -> {
            if (newLang == null) return;

            Locale locale = newLang.getLocale();

            configHolder.modify(cfg -> cfg.setLocale(locale.toLanguageTag()));

            ResourceBundle newBundle =
                    ResourceBundle.getBundle("languageBundles.messages", locale);
            mainCtrl.updateLanguage(newBundle);
        });

    }

    /**
     * Updates the UI elements to the new selected language.
     * @param resourceBundle the resource bundle corresponding to the new language.
     */
    public void updateLanguage(ResourceBundle resourceBundle) {
        this.bundle = resourceBundle;
        mainTitle.setText(resourceBundle.getString("title"));
        shoppingListButon.setText(resourceBundle.getString("shoppingList"));
        languageFilterMenu.setText(resourceBundle.getString("languages"));
        addToShop.setText(resourceBundle.getString("shop"));
        refreshButton.setText(resourceBundle.getString("refresh"));
        cloneButton.setText(resourceBundle.getString("clone"));
        cloneRecipeButton.setText(resourceBundle.getString("clone.ok"));
        recipeNameLabel.setText(resourceBundle.getString("clone.newRecipeName"));
        favSort.setText(resourceBundle.getString("allRecipes"));
        downloadRecipeButton.setText(resourceBundle.getString("download"));
    }

    /**
     * Refreshes the split panes and the content in the ListViews.
     */
    public void refresh() {
        splitPaneRefreshButton.setDividerPosition(0, 0.10090361445783134);
        splitNameDetails.setDividerPosition(0, 0.29797979797979796);
        refreshRecipes();
        selectFirstRecipeIfNoneSelected();
        if (getSelectedRecipe() != null) {
            lastSelectedRecipe = getSelectedRecipe();
            mainCtrl.sendToWSEndpoint(lastSelectedRecipe.getId());
        }
        if(lastSelectedRecipe!=null && favorites.contains(lastSelectedRecipe.getId())) {
            star.setSelected(true);
            star.setText("★");
        }else{
            star.setSelected(false);
            star.setText("☆");
        }
        if(!server.recipeExists(lastSelectedRecipe)) lastSelectedRecipe=null;

        refreshIngredients(lastSelectedRecipe);
        refreshPreparationSteps(lastSelectedRecipe);
        if (isCloning) {
            recipeNameTF.setText(lastSelectedRecipe.getName() + " copy");
        }
        recipeListView.refresh();
    }
    /**
     * Shows a star next to favorited recipes in the recipe list view
     */
    private void addStarsToRecipeListView() {
        recipeListView.setCellFactory(lv -> new ListCell<>() {
            private final Label starLabel=new Label("★");
            @Override
            protected void updateItem(Recipes recipe, boolean empty) {
                super.updateItem(recipe, empty);
                if (empty || recipe == null){
                    setText(null);
                    setGraphic(null);
                }else{
                    setText(recipe.getName());
                    if(favorites.contains(recipe.getId())) setGraphic(starLabel);
                }
            }
        });
    }

    /**
     * Apply the favorite filter
     */
    public void applyFav(){
        if(favSort.isSelected()) {
            favSort.setText(bundle.getString("favourites"));
            favFilter=(Recipes a)->favorites.contains(a.getId());
        }else{
            favSort.setText(bundle.getString("allRecipes"));
            favFilter=(Recipes a)->true;
        }

        applyPredicates();
    }

    /**
     * Check what is toggled
     */
    public void checkLangs(){
        if (filterEnglish.isSelected()) langFilters.add(RecipeLanguage.ENGLISH);
        if (filterDutch.isSelected())   langFilters.add(RecipeLanguage.DUTCH);
        if (filterGerman.isSelected())  langFilters.add(RecipeLanguage.GERMAN);
        if (filterSpanish.isSelected()) langFilters.add(RecipeLanguage.SPANISH);
    }
    /**
     * Apply the language filter
     */
    public void applyLang() {
        langFilters.clear();

        checkLangs();

        if (langFilters.isEmpty()) {
            langFilter = r -> true;
        } else {
            langFilter = r -> langFilters.contains(r.getLanguage());
        }

        configHolder.modify(cfg -> {
            Set<String> codes = new HashSet<>();
            if (filterEnglish.isSelected()) codes.add("en");
            if (filterDutch.isSelected())   codes.add("nl");
            if (filterGerman.isSelected())  codes.add("de");
            if (filterSpanish.isSelected()) codes.add("es");
            cfg.setRecipeLanguageFilters(codes);
        });

        applyPredicates();
    }

    /**
     * Refreshes the recipes on the client
     */
    public void refreshRecipes() {
        List<Recipes> updatedRecipes = server.getRecipes();

        handleMissingFavoriteRecipes(updatedRecipes);

        recipeData.setAll(server.getRecipes());
        recipeListView.setItems(sortedRecipes);
        applyPredicates();
    }

    /**
     * Handles the favorite recipe
     * logic
     * (I split into two methods because
     * I need this logic separate for APS)
     * @param updatedRecipes
     */
    public void handleMissingFavoriteRecipes(List<Recipes> updatedRecipes) {
        List<Recipes> currentRecipes = recipeData;

        Set<Long> updatedRecipeIds = updatedRecipes.stream()
                .map(Recipes::getId)
                .collect(Collectors.toSet());

        List<Recipes> missingRecipes = currentRecipes.stream()
                .filter(recipe -> !updatedRecipeIds.contains(recipe.getId()))
                .filter(recipe -> favorites.contains(recipe.getId()))
                .toList();

        for (int i = 0; i < missingRecipes.size(); i++) {
            removeFavRecipeId(missingRecipes.get(i).getId());
        }

        if (!missingRecipes.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Favorite removed");
            alert.setHeaderText(null);
            alert.setContentText(
                    "A favorited recipe was deleted and removed from your favorites."
            );
            alert.showAndWait();
        }
    }



    /**
     * Checks if a recipe is favorited
     * @param id the id of the recipe
     * @return if the recipe is favorited
     */
    public boolean isRecipeFavorited(Long id){
        return favorites.contains(id);
    }

    /**
     * when the favorite toggle button is clicked
     */
    public void starToggle(){
        if(lastSelectedRecipe==null) {
            star.setSelected(!star.isSelected());
            return;
        }
        if(star.isSelected()) {
            addFavRecipeId(lastSelectedRecipe.getId());
        }else removeFavRecipeId(lastSelectedRecipe.getId());
        star.setText(star.isSelected() ? "★" : "☆");
        recipeListView.refresh();
    }

    /**
     * Add a recipe to favorites
     * @param id the id of the recipe to be added
     */
    public void addFavRecipeId(Long id) {
        favorites.add(id);
        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(STARREDFILE, true))) {

            writer.println(id);

        } catch (IOException e) {
            System.out.println("6767");
        }
    }

    /**
     * Remove a recipe to favorites
     * @param idToRemove the id of the recipe to be removed
     */
    public void removeFavRecipeId(Long idToRemove) {
        favorites.remove(idToRemove);
        if (!STARREDFILE.exists()) {
            return;
        }
        List<String> remainingLines = new ArrayList<>();

        try (Scanner scanner = new Scanner(STARREDFILE)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.equals(idToRemove.toString())) {
                    remainingLines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("676767");
            return;
        }

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(STARREDFILE, false))) {

            for (String line : remainingLines) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.out.println("676");
        }
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
            currentRecipe.setPreparationSteps(steps);
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
        ingredientListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(IngredientInRecipe ingredient, boolean empty){
                super.updateItem(ingredient, empty);
                if (empty || ingredient == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    EditButton<IngredientInRecipe> deleteButton =
                            new EditButton<>(ingredient,"delete",getIndex(),
                                    ingredientListView,server,lastSelectedRecipe,
                                    RecipeOverviewCtrl.this,EditButtonOptions.REMOVE_INGREDIENT);
                    EditButton<IngredientInRecipe> editButton =
                            new EditButton<>(
                                    ingredient,"edit",getIndex(),ingredientListView,
                                    server,lastSelectedRecipe,RecipeOverviewCtrl.this,
                                    EditButtonOptions.EDIT_INGREDIENT);

                    HBox row=new HBox(8,deleteButton,editButton);
                    row.setPickOnBounds(false);
                    setText(ingredient.toString());
                    setGraphic(row);

                 //   setContentDisplay(ContentDisplay.RIGHT);
                    setGraphicTextGap(-80);
                    setTextOverrun(OverrunStyle.ELLIPSIS);
                }
            }
        });
    }


    /**
     * Adds an edit button next to the name of the ingredient
     */
    public void addDeleteButtonToPreparationStep() {
        preparationsListView.setCellFactory(lv -> new ListCell<>() {

            @Override
            protected void updateItem(PreparationStep step, boolean empty) {
                super.updateItem(step, empty);

                if (empty || step == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    EditButton<PreparationStep> deleteButton =
                            new EditButton<>(
                                    step,
                                    "delete",
                                    getIndex(),
                                    preparationsListView,
                                    server,
                                    lastSelectedRecipe,
                                    RecipeOverviewCtrl.this,
                                    EditButtonOptions.REMOVE_STEP
                            );

                    EditButton<PreparationStep> editButton =
                            new EditButton<>(
                                    step,
                                    "edit",
                                    getIndex(),
                                    preparationsListView,
                                    server,
                                    lastSelectedRecipe,
                                    RecipeOverviewCtrl.this,
                                    EditButtonOptions.EDIT_STEP
                            );

                    HBox row = new HBox(8, deleteButton, editButton);
                    row.setPickOnBounds(false);

                    setText(step.getDescription());
                    setGraphic(row);
                    setGraphicTextGap(-100);
                    setTextOverrun(OverrunStyle.ELLIPSIS);
                }
            }
        });
    }


    /**
     * Shows the scene of "add recipe"
     */
    public void addRecipe() {
        mainCtrl.showAdd(languageDropDown.getValue().getLocale());
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
     * edits a certain preparation step.
     * @param preparationStep
     */
    public void editPreparationStep(PreparationStep preparationStep){
        refresh();
        mainCtrl.showEditPreparationStep(lastSelectedRecipe, preparationStep);
    }

    /**
     * Shows the text field for entering the new recipe name (renamed)
     */
    public void renameRecipe() {
        if (lastSelectedRecipe == null) {
            // Show the error scene
        }
        else {
            isRenaming = true;
            recipeNameLabel.toFront();
            recipeNameTF.toFront();
            recipeNameTF.setText(lastSelectedRecipe.getName());
            recipeNameTF.setEditable(true);
            cloneRecipeButton.toFront();
        }
    }

    /**
     * Shows the text field for entering the new recipe name (cloned)
     */
    public void cloneRecipe() {
        if (lastSelectedRecipe == null) {
            // Show the error scene
        }
        else {
            isCloning = true;
            recipeNameLabel.toFront();
            recipeNameTF.toFront();
            recipeNameTF.setText(lastSelectedRecipe.getName() + " copy");
            recipeNameTF.setEditable(true);
            cloneRecipeButton.toFront();
        }
    }

    /**
     * Calls the clone recipe method of the server
     * and clones the last selected recipe
     */
    public void okRecipe() {
        if (recipeNameTF.getText().isEmpty()) {
            return;
        }
        if (isCloning){
            for (int i = 0; i < recipeData.size(); i++) {
                if (recipeData.get(i).getName().equals(recipeNameTF.getText())) {
                    return;
                }
            }
            server.cloneRecipe(lastSelectedRecipe, recipeNameTF.getText());
            refreshRecipes();
            refreshIngredients(lastSelectedRecipe);
            refreshPreparationSteps(lastSelectedRecipe);
            recipeNameTF.toBack();
            recipeNameLabel.toBack();
            cloneRecipeButton.toBack();
            isCloning = false;
        }
        else if (isRenaming){
            for (int i = 0; i < recipeData.size(); i++) {
                if (!lastSelectedRecipe.getName().equals(recipeNameTF.getText())
                        && (recipeData.get(i).getName().equals(recipeNameTF.getText()))) {
                    return;
                }
            }
                server.renameRecipe(lastSelectedRecipe, recipeNameTF.getText());
                refreshRecipes();
                refreshIngredients(lastSelectedRecipe);
                refreshPreparationSteps(lastSelectedRecipe);
                recipeNameTF.toBack();
                recipeNameLabel.toBack();
                cloneRecipeButton.toBack();
                isRenaming = false;
        }
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
     * Apply the predicates to the sorted list
     */
    public void applyPredicates(){
        filteredRecipes.setPredicate(searchFilter.and(favFilter.and(langFilter)));
        recipeListView.refresh();
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
            searchFilter =recipes -> true;
            applyPredicates();
            return;
        }
        text=text.toLowerCase();
        final String[] texts=text.split("\\s+");
        searchFilter = recipes -> {
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
        };
        applyPredicates();
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
            if (isCloning || isRenaming) {
                okRecipe();
            }
            else {
                searchInit();
            }
        } else if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ESCAPE){
            if (isCloning || isRenaming) {
                recipeNameTF.clear();
                recipeNameTF.toBack();
                recipeNameLabel.toBack();
                cloneRecipeButton.toBack();
                isCloning = false;
                isRenaming = false;
            }
        }
    }
    /**
     * Edit the selected ingredient
     *
     * @param ingredient the selected ingredient
     */
    public void editIngredient(IngredientInRecipe ingredient, Recipes recipe) {
        mainCtrl.showEditIngredient(ingredient, recipe);
    }

    /**
     * Refreshes current recipe content
     * @param id
     */
    public void refreshIfCurrent(long id){
        if(lastSelectedRecipe!=null && lastSelectedRecipe.getId()==id){
            refreshIngredients(lastSelectedRecipe);
            refreshPreparationSteps(lastSelectedRecipe);
        }
    }


    /**
     * add recipe to list view
     */
    public void addRecipeToListView(long id) {
        Recipes recipeNew = server.getRecipe(id);

        for (int i = 0; i < recipeData.size(); i++) {
            Recipes recipe = recipeData.get(i);

            if (recipe.getId() == id) {
                recipe.setName(recipeNew.getName());
                recipeData.set(i, recipe);
                return;
            }
        }
        recipeData.add(recipeNew);
    }


    /**
     * add recipe to list view
     */
    public void removeRecipeFromListView(long id){
        for(Recipes recipe:recipeData){
            if(recipe.getId()==id){
                ObservableList<Recipes> snapshot =
                        FXCollections.observableArrayList(recipeData);
                snapshot.remove(recipe);
                handleMissingFavoriteRecipes(snapshot);
                recipeData.remove(recipe);
                break;
            }
        }

        recipeListView.refresh();

    }

    /**
     * Downloads the lastSelectedRecipe
     */
    public void downloadRecipe() {
        if (lastSelectedRecipe != null) {
            mainCtrl.showSaveRecipe(lastSelectedRecipe, this);
        }
    }
}