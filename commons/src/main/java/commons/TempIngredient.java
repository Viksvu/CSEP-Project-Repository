package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TempIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String name;
}
