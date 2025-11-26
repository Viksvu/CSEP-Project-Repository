package commons;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class PreparationStep {
    private String description;
    public PreparationStep(String description) {
        this.description = description;
    }

    public PreparationStep() {

    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
}
