public class Doctor extends Person {
    private static final long serialVersionUID = 1L;

    private int diplomaId;
    private Schedule schedule;

    public Doctor(String name, long nationalId, int diplomaId) {
        super(name, nationalId);
        this.diplomaId = diplomaId;
        this.schedule = new Schedule(6); // Max 6 reservations per day
    }

    public int getDiplomaId() {
        return diplomaId;
    }

    public void setDiplomaId(int diplomaId) {
        this.diplomaId = diplomaId;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    @Override
    public String toString() {
        return getName() + " (Diploma ID: " + diplomaId + ")";
    }


}
