package commons;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Embeddable
public class PreparationStep {
    @NotBlank(message = "PreparationStep description cannot be blank")
    private String description;

    /**
     * A constructor for preparation step
     *
     * @param description of the preparation step.
     */
    public PreparationStep(String description) {
        this.description = description;
    }

    /**
     * No-arg constructor for JPA.
     */
    public PreparationStep() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Clones "this" by cloning all the attributes
     * @return the clone
     */
    public PreparationStep clonePreparationStep() {
        return new PreparationStep(this.description);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PreparationStep that = (PreparationStep) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(description);
    }

    @Override
    public String toString() {
        return description;
    }
}
