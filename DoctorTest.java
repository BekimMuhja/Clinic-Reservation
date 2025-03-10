import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {

    @Test
    void testGetDiplomaId() {
        Doctor doctor = new Doctor("Dr. John Smith", 123456789L, 1111);
        assertEquals(1111, doctor.getDiplomaId(), "Diploma ID should match the initialized value.");
    }

    @Test
    void testScheduleManagement() {
        Doctor doctor = new Doctor("Dr. John Smith", 123456789L, 1111);
        assertNotNull(doctor.getSchedule(), "Schedule should be initialized.");
    }

    @Test
    void testToString() {
        Doctor doctor = new Doctor("Dr. John Smith", 123456789L, 1111);
        assertEquals("Dr. John Smith (Diploma ID: 1111)", doctor.toString(), "toString method should return the expected string.");
    }
}
