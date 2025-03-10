import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hospital implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private String name;
    private List<Section> sections;

    public Hospital(int id, String name) {
        this.id = id;
        this.name = name;
        this.sections = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addSection(Section section) {
        sections.add(section);
    }

    public List<Section> getSections() {
        return sections;
    }

    @Override
    public String toString() {
        return name;
    }
}
