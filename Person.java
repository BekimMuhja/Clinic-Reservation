import java.io.Serializable;

public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private final long nationalId;

    public Person(String name, long nationalId) {
        this.name = name;
        this.nationalId = nationalId;
    }

    public String getName() {
        return name;
    }

    public long getNationalId() {
        return nationalId;
    }
}
