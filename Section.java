import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Section implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private String name;
    private List<Doctor> doctors;

    public Section(int id, String name) {
        this.id = id;
        this.name = name;
        this.doctors = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addDoctor(Doctor doctor) {
        for (Doctor existingDoctor : doctors) {
            if (existingDoctor.getDiplomaId() == doctor.getDiplomaId()) {
                throw new DuplicateInfoException("A doctor with Diploma ID " + doctor.getDiplomaId() + " already exists in this section.");
            }
        }
        doctors.add(doctor);
    }


    public List<Doctor> getDoctors() {
        return doctors;
    }

    @Override
    public String toString() {
        return name;
    }
}
