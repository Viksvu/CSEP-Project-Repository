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
package client.utils;

import commons.IngredientInRecipe;
import commons.Ingredients;
import commons.PreparationStep;
import commons.Recipes;
import commons.request.*;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.net.ConnectException;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

	private static final String SERVER = System.getenv("SERVER_URL") == null
            ? "http://localhost:8080/"
            : System.getenv("SERVER_URL");

    /**
	* Gets a list of all the recipes in the database
     * @return the list
     */
    public List<Recipes> getRecipes() {
        return sendGetRequest(
                new GenericType<List<Recipes>>() {},
                "api/recipe/list"
        );
	}

    /**
     * Adds a recipe through the API endpoints
     * @return the recipe if added successfully
     */
	public Recipes addRecipe(Recipes recipe) {
		return ClientBuilder.newClient(new ClientConfig()) //
				.target(SERVER).path("api/recipe/add") //
				.request(APPLICATION_JSON) //
				.post(Entity
                        .entity(recipe, APPLICATION_JSON), Recipes.class);
	}

    /**
     * Remove a recipe from the database
     * @param recipe to remove
     * @return the recipe if removed successfully
     */
    public Recipes removeRecipe(Recipes recipe) {
       return ClientBuilder.newClient(new ClientConfig())
               .target(SERVER).path("api/recipe/delete")
               .request(APPLICATION_JSON)
               .post(Entity.entity(recipe, APPLICATION_JSON), Recipes.class);
    }

    /**
     * Clones a recipe from the database
     * @param recipe to be cloned
     * @return the recipe if cloned successfully
     */
    public Recipes cloneRecipe(Recipes recipe, String newName) {
        CloneRecipeRequest request = new CloneRecipeRequest(recipe, newName);

        return sendPostRequest(request, Recipes.class);
    }

    public List<Ingredients> getIngredientsFromDatabase() {
        return sendGetRequest(
                new GenericType<List<Ingredients>>(){},
                "api/ingredient/list"
        );
    }
    /**
     * Adds an ingredient to the ingredients database
     * if it does not already exist
     * @param ingredient to be added
     * @return
     */
    public Ingredients addIngredientToDatabase(Ingredients ingredient) {
        if (!getIngredientsFromDatabase().contains(ingredient)){
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/ingredient/add")
                    .request(APPLICATION_JSON) //
                    .post(Entity.entity(ingredient, APPLICATION_JSON),
                            Ingredients.class);
        }
        else return ingredient;
    }

    /**
     * Adds an ingredient to the recipe
     * @param recipe to add the ingredient to
     * @return a list with all ingredients in a recipe
     */
    public List<IngredientInRecipe> getIngredientsInRecipe(Recipes recipe) {
        return sendGetRequest(
                new GenericType<List<IngredientInRecipe>>(){},
                "api/recipeingredient/get",
                new QueryParam("id", recipe.getId())
        );
    }

    /**
     * Checks if a recipe exists
     * @param recipe the recipe that is checked
     * @return true or false
     */
    public boolean recipeExists(Recipes recipe) {
        if (recipe == null || recipe.getId() <= 0) {
            return false;
        }
        try {
            sendGetRequest(
                    new GenericType<List<IngredientInRecipe>>(){},
                    "api/recipeingredient/get",
                    new QueryParam("id", recipe.getId())
            );
            return true;
        }catch(jakarta.ws.rs.WebApplicationException e) {
            return false;
        }
    }

    /**
     * Adds an ingredient as to a recipe
     * @param ingredient
     * @return
     */
    public IngredientInRecipe
    addIngredientToRecipe(IngredientInRecipe ingredient, Recipes recipe) {
        Long recipeId = recipe.getId();
        AddIngredientInRecipeRequest request =
                new AddIngredientInRecipeRequest(recipeId, ingredient);

        return sendPostRequest(request, IngredientInRecipe.class);
    }


    /**
     * Adds an ingredient as to a recipe
     * @param ingredient
     * @return
     */
    public IngredientInRecipe
    editIngredientInRecipe(IngredientInRecipe ingredient, Recipes recipe) {
        Long recipeId = recipe.getId();
        EditIngredientInRecipeRequest request =
                new EditIngredientInRecipeRequest(recipeId, ingredient);

        return sendPostRequest(request, IngredientInRecipe.class);
    }

    /**
     * Deletes an ingredient from a recipe
     * @param ingredient
     * @param recipe
     * @return
     */
    public IngredientInRecipe
    removeIngredientFromRecipe(IngredientInRecipe ingredient,
                                                         Recipes recipe) {
        Long recipeId = recipe.getId();
        DeleteIngredientInRecipeRequest request =
                new DeleteIngredientInRecipeRequest(recipeId, ingredient);

        return sendPostRequest(request, IngredientInRecipe.class);
    }

    /**
     * Get all the preparation steps of a recipe
     * @param recipe recipe to get preparation steps from
     * @return List of PreparationSteps
     */
    public List<PreparationStep> getPreparationSteps(Recipes recipe) {
        Long recipeId = recipe.getId();
        return sendGetRequest(
                new GenericType<List<PreparationStep>>() {},
                "api/prep-step/list",
                new QueryParam("recipeId", recipeId));
    }

    /**
     * Add a preparationStep to a recipe
     * @param step PreparationStep to add
     * @param recipe Recipe to add to
     * @return The PreparationStep if successful
     */
    public PreparationStep addPreparationStepToRecipe(PreparationStep step,
                                                      Recipes recipe) {
        Long recipeId = recipe.getId();
        AddPreparationStepRequest request =
                new AddPreparationStepRequest(recipeId, step);

        return sendPostRequest(request, PreparationStep.class);
    }

    /**
     * edit a preparationStep in a recipe
     * @param step PreparationStep to edit
     * @param recipe Recipe in which it will be edited
     * @param index the index of the prep-step in recipe.
     * @return The PreparationStep if successful
     */
    public PreparationStep editPreparationStepFromRecipe(PreparationStep step,
                                                         Recipes recipe,
                                                         int index){
        Long recipeId = recipe.getId();
        EditPreparationStepRequest request =
                new EditPreparationStepRequest(recipeId, index, step);

        return sendPostRequest(request, PreparationStep.class);
    }

    /**
     * Delete a preparationStep from a recipe
     * @param step PreparationStep to delete
     * @param recipe Recipe to delete from
     * @return The PreparationStep if successful
     */
    public PreparationStep deletePreparationStepToRecipe(PreparationStep step,
                                                         Recipes recipe) {
        Long recipeId = recipe.getId();
        DeletePreparationStepRequest request =
                new DeletePreparationStepRequest(recipeId, step);

        return sendPostRequest(request, PreparationStep.class);
    }

    /**
     * Get a web target with the server url already in it
     * @return WebTarget for api usage
     */
    private WebTarget getBasicWebTarget() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER);
    }

    /**
     * Send an update request
     * @param postRequest request to send
     * @param responseType answer type to retrieve
     * @return Object of type responseType
     * @param <B> UpdateRequest class
     * @param <A> Response type
     */
    private <B extends PostRequest,A> A sendPostRequest(
            B postRequest,
            Class<A> responseType) {
        return getBasicWebTarget()
                .path(postRequest.serverPath())
                .request(APPLICATION_JSON)
                .post(Entity.entity(postRequest, APPLICATION_JSON),
                        responseType);
    }

    /**
     * Send a get request
     * @param responseType type of result
     * @param path path on server
     * @param params query param(s)
     * @return result with type specified
     * @param <A> result object type
     */
    private <A> A sendGetRequest(
            GenericType<A> responseType,
            String path, QueryParam... params) {
        WebTarget target = getBasicWebTarget()
                .path(path);

        for (QueryParam param : params) {
            target = target.queryParam(param.key(), param.value());
        }

        return target.request(APPLICATION_JSON)
                .get(responseType);
    }

	/**
     * temp
     * @return
     */
    public boolean isServerAvailable() {
		try {
			getBasicWebTarget()
					.request(APPLICATION_JSON)
					.get();
		} catch (ProcessingException e) {
			if (e.getCause() instanceof ConnectException) {
				return false;
			}
		}
		return true;
	}
}