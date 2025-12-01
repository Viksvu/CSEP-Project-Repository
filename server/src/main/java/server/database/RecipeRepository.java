package server.database;

import commons.TestRecipeForDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<TestRecipeForDB, Long> {
}
