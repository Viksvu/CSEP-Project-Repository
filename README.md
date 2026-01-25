# CSEP Project Team 10
## How to run the project
### 1. Setup environment
* Setup intellij with maven installed
* Clone this repository to your local system
* Download a (and unzip!) an [OpenJFX SDK](https://openjfx.io).
### 2. Start the server
* First start the server with the following maven command
* ```mvn -pl server -am spring-boot:run```
### 3. Start the client
* Then start the client with the following java VM options
* ```--module-path=C:\path\to\your\downloaded\javafx-sdk-25.0.1\lib --add-modules=javafx.controls,javafx.fxml,javafx.web```
* And the following maven command
* ```mvn -pl client -am javafx:run```
* With option -cfg you can specify the configuration file to be used (For example -cfg new-config-file-path.json). Default: config.json.

## Basic feature overview
### Simple instructions for all basic features:
* After the client loads, the left side is for all the recipes. 
* The green plus can be used to add recipe and the red minus can be used to delete recipe. 
* To add ingredients to a recipe, first select the recipe on the left and then in the **top** right section, click on the green plus button. 
* To add preparation steps, repeat the same thing but on the **bottom** right of the application. 
* To edit/delete ingredients or preparation steps just click on the button next to the respective ingredient or preparation step.
* To clone a recipe, first click on a recipe and then simply click on the clone button. You will then be asked for the name of the new recipe.
* Similarily, to edit a recipe, first click on the recipe and then click on the pencil icon between the add recipe and remove recipe buttons.
* To download a recipe, choose the recipe and then click on download in the top menu. You will then be prompted to choose the location of the file where you want to save it.
* Our application implements websocket. The refresh button in the top right only serves the purpose of a backup in case there are any issues with the automated change synchronization.

## Project extensions done
TODO: In readme: put all extensions that you did

## Things done extra (outside basic requirements and extensions)
### Live Language Switch
* To switch the language of the entire UI, the user can navigate to the top left of the application, where they will find a drop-down menu with the available languages presented by their respective flags.
* The users last choice is saved by the application, such that if they re-open the application after shutting it down, the UI will be loaded in the language according to their last choice.
* Whilst creating a new recipe, the user will have a drop-down menu where they can choose what language they want their recipe to be in.
* The user can then navigate to the top center of the application where they will be met with the same drop-down menu, where they can select one or more languages to filter their recipes by.
* The application will persist the users choice of language filters.
* This application provides the basic English Dutch languages, but also is available in German and Spanish.
* To further improve user experience, we have added to the application the additional feature, that the UI very subtly responds to this change in language choice by also slightly changing the certain UI elements' colours. This change is not drastic, as to not distract from the current task, yet subtle enough to register as a smoothing change.
* We believe that we have gone above the requirements listed in the product backlog to make this a smooth and inclusive user experience, and would thus like to get full points for this feature. 

### Search Bar (Full-Text, Multi-Term, and Relevance Sorting)
* The recipe overview supports **full-text search** without opening a new window: the list view is filtered directly.
* The search checks multiple parts of each recipe:
  * Recipe name
  * Ingredient names
  * Preparation step descriptions
* Multi-term queries are supported (split on whitespace) and treated as an **AND** search:
  * Example: `"t1 t2 t3"` matches recipes containing `t1` AND `t2` AND `t3` across any of the supported fields.
* The search integrates cleanly with other filtering (e.g., favorites-only filtering still works while searching).
* Bonus: **Relevance-based sorting while searching**
  * When a search query is active, results are **sorted by match quality**, prioritizing:
    1. Matches in recipe name (especially if the name starts with the query)
    2. Matches in ingredient names
    3. Matches in preparation steps
  * When the search query is empty, recipes are sorted alphabetically by name.
* Users can cancel a search quickly by pressing **ESC**, which clears the search field and restores the full list.
* The recipe overview includes a toggle button that allows users to switch between:
  * **All Recipes** – the complete recipe list.
  * **Favorites** – only recipes that the user has starred.
* When the toggle is enabled, the recipe list is filtered dynamically to show **only favorited recipes**, without opening a new view or reloading the scene.
* The button label updates according to its state (e.g. “All Recipes” ↔ “Favourites”), providing clear visual feedback.
* This filter integrates seamlessly with:
  * The search bar (searching within favorites only).
  * Language-based recipe filtering.
* This functionality fulfills the requirement of providing a **special overview of favorite recipes**, while still allowing users to easily return to the full recipe list.