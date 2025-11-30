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

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.ConnectException;
import java.util.List;

import commons.Recipes;
import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;

public class ServerUtils {

	private static final String SERVER = "http://localhost:8080/";


    /**
	* temp
     * @return
     */
    public List<Recipes> getRecipes() {
		return ClientBuilder.newClient(new ClientConfig()) //
				.target(SERVER).path("api/recipe/list") //
				.request(APPLICATION_JSON) //
				.get(new GenericType<List<Recipes>>() {});
	}/**
     * temp
     * @return
     */
	public Recipes addRecipe(Recipes recipe) {
		return ClientBuilder.newClient(new ClientConfig()) //
				.target(SERVER).path("api/recipe/add") //
				.request(APPLICATION_JSON) //
				.post(Entity.entity(recipe, APPLICATION_JSON), Recipes.class);
	}

    /**
     * temp
     * @param recipe
     * @return
     */
    public Recipes removeRecipe(Recipes recipe) {
       return ClientBuilder.newClient(new ClientConfig())
               .target(SERVER).path("api/recipe/delete")
               .request(APPLICATION_JSON)
               .post(Entity.entity(recipe, APPLICATION_JSON), Recipes.class);
    }

	/**
     * temp
     * @return
     */
    public boolean isServerAvailable() {
		try {
			ClientBuilder.newClient(new ClientConfig()) //
					.target(SERVER) //
					.request(APPLICATION_JSON) //
					.get();
		} catch (ProcessingException e) {
			if (e.getCause() instanceof ConnectException) {
				return false;
			}
		}
		return true;
	}
}