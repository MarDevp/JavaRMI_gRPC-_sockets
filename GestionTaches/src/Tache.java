import java.io.Serializable;

public class Tache implements Serializable {
    private String description;

    public Tache(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
