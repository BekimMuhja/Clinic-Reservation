import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    @Test
    void testGetNationalId() {
        Patient patient = new Patient("John Doe", 123456789L);
        assertEquals(123456789L, patient.getNationalId(), "National ID should match the initialized value.");
    }

    @Test
    void testToString() {
        Patient patient = new Patient("John Doe", 123456789L);
        assertEquals("John Doe (ID: 123456789)", patient.toString(), "toString method should return the expected string.");
    }
}
