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

    public void initialize(Stage primaryStage
            , Pair<RecipeOverviewCtrl, Parent> overview
            , Pair<AddRecipeCtrl, Parent> add
            , Pair<RemoveRecipeCtrl, Parent> remove
    ) {
        this.primaryStage = primaryStage;
        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.addCtrl = add.getKey();
        this.add = new Scene(add.getValue());

        this.removeCtrl = remove.getKey();
        this.remove = new Scene(remove.getValue());

        showOverview();
        primaryStage.show();
    }

    public void showOverview() {
        primaryStage.setTitle("Recipes: Overview");
        primaryStage.setScene(overview);
        overviewCtrl.refresh();
    }

    public void showAdd() {
        primaryStage.setTitle("Recipes: Adding Recipe");
        primaryStage.setScene(add);
//        add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }

    public void showRemove() {
        primaryStage.setTitle("Recipes: Removing Recipe");
        primaryStage.setScene(remove);
    }

    // NEEDS TO BE REPLACED
    public void addRecipeToList(String recipeName) {
        overviewCtrl.addRecipeToList(recipeName);
    }

    public ObservableList<String> getRecipes() {
        return overviewCtrl.getRecipeObservableList();
    }
}
