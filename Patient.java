public class Patient extends Person {
    private static final long serialVersionUID = 1L;

    public Patient(String name, long nationalId) {
        super(name, nationalId);
    }

    @Override
    public String toString() {
        return getName() + " (ID: " + getNationalId() + ")";
    }
}
