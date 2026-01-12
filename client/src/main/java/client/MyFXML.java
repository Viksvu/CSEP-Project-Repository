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

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ResourceBundle;

import com.google.inject.Injector;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import javafx.util.Callback;
import javafx.util.Pair;

public class MyFXML {

    private Injector injector;
    /**
     * Constructs a new MyFXML instance using the provided injector.
     *
     * @param injector the dependency
     *                 injection container used to create controller instances
     */
    public MyFXML(Injector injector) {
        this.injector = injector;
    }

    /**
     * Loads an FXML file,
     * initializes its controller through dependency injection,
     * and returns both the controller and the root UI node.
     *
     * @param <T>   the type of the controller
     * @param c     the controller class
     * @param parts the path segments forming the FXML resource location
     * @return a Pair containing the controller and the UI root element
     */
    public <T> Pair<T, Parent> load(Class<T> c, ResourceBundle bundle, String... parts) {
        try {
            var loader =
                    new FXMLLoader(getLocation(parts),
                            bundle, null, new MyFactory(),
                            StandardCharsets.UTF_8);
            Parent parent = loader.load();
            T ctrl = loader.getController();
            return new Pair<>(ctrl, parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Resolves a resource location
     * for an FXML file using provided path segments.
     *
     * @param parts the path segments that form the relative FXML file path
     * @return the URL to the requested resource
     */
    private URL getLocation(String... parts) {
        var path = Path.of("", parts).toString();
        return MyFXML.class.getClassLoader().getResource(path);
    }

    /**
     * Factory used by FXMLLoader to build controllers and other objects
     * using dependency injection.
     */
    private class MyFactory implements BuilderFactory,
            Callback<Class<?>, Object> {

        /**
         * Returns a Builder that constructs objects through the injector.
         *
         * @param type the class type to build
         * @return a Builder for the given type
         */
        @Override
        @SuppressWarnings("rawtypes")
        public Builder<?> getBuilder(Class<?> type) {
            return new Builder() {
                @Override
                public Object build() {
                    return injector.getInstance(type);
                }
            };
        }

        /**
         * Supplies instances
         * requested by FXMLLoader using dependency injection.
         *
         * @param type the class type requested
         * @return an instance of the requested type
         */
        @Override
        public Object call(Class<?> type) {
            return injector.getInstance(type);
        }
    }
}