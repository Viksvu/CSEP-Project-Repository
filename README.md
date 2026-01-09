# CSEP Project Team 10
## How to run the project
### 1. Setup environment
* Setup intellij with maven installed
* Clone this repository to your local system
* Download a JavaFX SDK
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
TODO: In readme: state what you did extra for the project