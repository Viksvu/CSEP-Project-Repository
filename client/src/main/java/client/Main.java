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
package client;

import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import client.scenes.*;
import client.utils.ServerUtils;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Main extends Application {

	private static final Injector INJECTOR = createInjector(new MyModule());
	private static final MyFXML FXML = new MyFXML(INJECTOR);

    /**
     * Main method: Starting point of the program
     * @param args arguments to pass on when starting the program
     * @throws URISyntaxException not really relevant but present because
     * Initializable is implemented
     * @throws IOException in case of exceptions while outputting on screen
     * or while reading relevant fxml files
     */
	public static void main(String[] args)
            throws URISyntaxException, IOException {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		var serverUtils = INJECTOR.getInstance(ServerUtils.class);
		if (!serverUtils.isServerAvailable()) {
			var msg = "Server needs to be started before the client"
		+",but it does not seem to be available. Shutting down.";
			System.err.println(msg);
			return;
		}

		Map<String, Pair<?, Parent>> scenes = new HashMap<>();

		scenes.put("overview",
				FXML.load(RecipeOverviewCtrl.class,
						"client", "scenes", "RecipeOverview.fxml"));

		scenes.put("addRecipe",
				FXML.load(AddRecipeCtrl.class,
						"client", "scenes", "AddRecipe.fxml"));

		scenes.put("removeRecipe",
				FXML.load(RemoveRecipeCtrl.class,
						"client", "scenes", "RemoveRecipe.fxml"));

		scenes.put("addIngredient",
				FXML.load(AddIngredientCtrl.class,
						"client", "scenes", "AddIngredient.fxml"));

		scenes.put("shoppingList",
				FXML.load(ShoppingListCtrl.class,
						"client", "scenes", "ShoppingList.fxml"));

		scenes.put("addRecipeIngredients",
				FXML.load(AddRecipeIngredientsCtrl.class,
						"client", "scenes",
						"AddRecipeIngredientsOverview.fxml"));

		scenes.put("overviewList",
				FXML.load(OverviewListCtrl.class,
						"client", "scenes", "OverviewList.fxml"));

		scenes.put("editIngredient",
				FXML.load(EditIngredientCtrl.class,
						"client", "scenes", "EditIngredient.fxml"));

		scenes.put("addPreparationStep",
				FXML.load(AddPreparationStepCtrl.class,
						"client", "scenes",
						"AddPreparationStep.fxml"));
		scenes.put("editPreparationStep",
				FXML.load(AddPreparationStepCtrl.class,
						"client", "scenes",
						"EditPreparationStep.fxml"));


		var mainCtrl = INJECTOR.getInstance(MainCtrl.class);

		mainCtrl.initialize(
                primaryStage,
                scenes
		);
	}
}