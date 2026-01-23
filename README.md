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
* Then start the client with the following java options
* ```--module-path=C:\path\to\your\downloaded\javafx-sdk-25.0.1\lib --add-modules=javafx.controls,javafx.fxml,javafx.web```
* And the following maven command
* ```mvn -pl client -am javafx:run```

## Basic feature overview
TODO: Have an overview about how you implement the basic requirements

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