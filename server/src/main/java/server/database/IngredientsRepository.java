package server.database;

import commons.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsRepository
        extends JpaRepository<Ingredients, Long> {
}
