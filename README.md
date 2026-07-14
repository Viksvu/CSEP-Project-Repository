# CSEP Project Team 10

This project was developed as part of the CSEP course in the Computer Science and Engineering bachelor’s programme at TU Delft. This repository is a copy of the original project repository. The original version is available at: https://gitlab.ewi.tudelft.nl/cse1105/2025-2026/teams/csep-team-10/-/tree/7a8c5f14233342c6ef4a6384cdbf54e9d3002c11/


My contributions focused on extending a Java-based client–server application built with JavaFX, Spring Boot, Maven, REST APIs, WebSockets, and a persistence layer. I mainly worked on real-time synchronization between clients, shopping-list functionality, live interface updates, user notifications, server shutdown handling, REST endpoints, and persistent data management.


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


### Automated Change Synchronization (WebSockets)
* The application implements **automated, real-time synchronization** of changes across all connected clients using **WebSockets**.
* Clients subscribe to server-side update channels instead of polling, ensuring efficient and modern change propagation.
* Manual refreshing is no longer required for normal operation; the refresh button exists only as a fallback.

* **Recipe title updates**
  * When a recipe title is changed by one user, all other clients immediately see the updated title without refreshing.
* **Recipe addition and deletion**
  * Adding or removing a recipe is propagated instantly to all connected clients.
  * The recipe list updates dynamically in real time.
* **Recipe content changes**
  * Changes to ingredients or preparation steps are automatically pushed to all clients currently viewing the same recipe.
  * Users do not need to manually reload or reselect the recipe.

* The client uses **WebSockets** to subscribe to relevant update topics.
* The server **pushes only relevant change events**, instead of broadcasting the full application state.
* Different WebSocket channels are used to:
  * Subscribe to changes in the **recipe list (titles, additions, deletions)**.
  * Subscribe to changes for **individual recipes**, reducing unnecessary data transfer.
* The client **does not poll** for updates; all changes are event-driven.
* Advanced conflict resolution is intentionally out of scope, as recommended in the project description.

#### Automated Change Synchronization – Extra Features
* Updated or newly added ingredients and preparation steps are **visually highlighted for a few seconds** when changes are propagated, helping users quickly identify what has changed.
* The application displays **notifications** to inform users when:
  * A recipe has been deleted by another user.
  * The currently viewed recipe has been modified elsewhere.
* If a user has **favorited a recipe** that is deleted by another user, a **popup notification** is shown to clearly communicate the action.

* Automated change propagation is applied consistently across multiple list-based views throughout the application, not only in the main recipe overview.
* In the **Delete Recipe** scene, dropdown lists of available recipes are updated in real time when recipes are added, removed or renamed.
* In the **Shopping List – Add Recipe** scene, recipe selection dropdowns automatically reflect recipe additions, deletions and rename.
* These updates are handled via WebSockets and require no manual refresh, ensuring a consistent and up-to-date user interface across all scenes.

* When the **server is shut down**, the server endpoint sends a shutdown notification to all connected clients.
* Upon receiving this message, the client displays a **warning window** informing the user that the server is unavailable.
* This warning window **blocks further interaction with the application**,  and closing the window, means shutting off the application.

* We believe that the consistent use of real-time synchronization, combined with enhanced visual feedback, notifications, and robust shutdown handling across multiple views, exceeds the basic requirements of this feature and thus justifies full points for it.


### Shopping List (User Story 4.5)
* The application provides a **local shopping list feature**, allowing users to plan their grocery shopping.
* The shopping list is **not stored on the server** and exists only on the client side, as required.

* Users can **add and remove ingredients directly** on the shopping list, enabling the inclusion of unrelated items (e.g. cereal, drinks).
* The shopping list is presented as a **flat, ordered list of ingredients**, optimized for easy use while shopping.
* Users can **reset the shopping list** at any time to start over.

* Users can add the ingredients of a recipe to the shopping list.
* Before adding, an **editable overview** of all ingredients is shown.
* Within this overview, users can:
  * **Adjust ingredient amounts** to fine-tune recipes to their preferences.
  * **Add or remove arbitrary ingredients** to customize the list.
* After confirmation, all items from the overview are added to the shopping list in one action.

* If the same ingredient is added through multiple recipes, it appears **multiple times** in the shopping list.
* Each entry includes the **name of the source recipe**.
* Simple string propagation is used; later changes to recipe names are **not reflected** in the shopping list.

* Users can **download a printable version of the shopping list** for use in the supermarket.
* The shopping list printing functionality reuses the **same implementation as recipe printing**, ensuring consistency and reliability.
* The exported format is suitable for printing.

#### Shopping list - Extra features:
* Users can **add a new recipe directly from the Shopping List view** for increased convenience.
  * This add recipe scene is also automatically changed based on recipe deletions and additions (See Automated Change Synchronization – Extra Features)
* Ingredients in the shopping list can be **marked off** by the user while shopping.
  * Marked items become **translucent** to visually indicate completion.
  * Completed items are **moved to the bottom of the list**, keeping remaining items clearly visible.
* These features enhance usability during real shopping scenarios and go beyond the basic functional requirements of the shopping list user story.









