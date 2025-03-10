import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SectionTest {

    @Test
    void testAddDoctor() {
        Section section = new Section(101, "Emergency Room");
        Doctor doctor = new Doctor("Dr. John Smith", 123456789L, 1111);
        section.addDoctor(doctor);

        assertTrue(section.getDoctors().contains(doctor), "Doctor should be added successfully.");
    }

    @Test
    void testDuplicateDoctorException() {
        Section section = new Section(101, "Emergency Room");
        Doctor doctor = new Doctor("Dr. John Smith", 123456789L, 1111);
        section.addDoctor(doctor);

        assertThrows(DuplicateInfoException.class, () -> section.addDoctor(doctor),
            "Adding a duplicate doctor should throw DuplicateInfoException.");
    }
}
