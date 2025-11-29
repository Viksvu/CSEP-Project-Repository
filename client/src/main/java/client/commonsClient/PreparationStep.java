package client.commonsClient;


import java.util.Objects;

public class PreparationStep {
    private String description;

    /**
     * A constructor for a preparation step.
     *
     * @param description the description of a preparation step.
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
