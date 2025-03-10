import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HospitalTest {

    @Test
    void testAddSection() {
        Hospital hospital = new Hospital(1, "City Hospital");
        Section section = new Section(101, "Emergency Room");
        hospital.addSection(section);

        assertTrue(hospital.getSections().contains(section), "Section should be added successfully.");
    }

    @Test
    void testGetName() {
        Hospital hospital = new Hospital(1, "City Hospital");
        assertEquals("City Hospital", hospital.getName(), "Hospital name should match.");
    }

    @Test
    void testGetId() {
        Hospital hospital = new Hospital(1, "City Hospital");
        assertEquals(1, hospital.getId(), "Hospital ID should match the initialized value.");
    }
}
