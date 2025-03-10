import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class CRSTest {

    @Test
    void testAddPatient() {
        CRS crs = CRS.initializeWithSampleData();
        Patient patient = new Patient("John Doe", 123456789L);
        crs.addPatient(patient);

        assertEquals(patient, crs.getPatient(123456789L), "Patient should be added successfully.");
    }

    @Test
    void testDuplicatePatientException() {
        CRS crs = CRS.initializeWithSampleData();
        Patient patient = new Patient("John Doe", 123456789L);
        crs.addPatient(patient);

        assertThrows(DuplicateInfoException.class, () -> crs.addPatient(patient),
            "Adding a duplicate patient should throw DuplicateInfoException.");
    }

    @Test
    void testAddHospital() {
        CRS crs = new CRS();
        Hospital hospital = new Hospital(1, "Test Hospital");
        crs.addHospital(hospital);

        assertEquals(hospital, crs.getHospital(1), "Hospital should be added successfully.");
    }

    @Test
    void testMakeRendezvous() {
        // Initialize CRS with sample data
        CRS crs = CRS.initializeWithSampleData();

        // Add the missing patient
        Patient patient = new Patient("John Doe", 123456789L);
        crs.addPatient(patient); // Ensure the patient is added to the CRS

        // Prepare appointment data
        Date appointmentDate = new Date();

        // Attempt to make a rendezvous
        boolean success = crs.makeRendezvous(123456789L, 1, 101, 1111, appointmentDate);

        // Assert that the rendezvous was successfully created
        assertTrue(success, "Rendezvous should be made successfully.");
    }


    @Test
    void testSerialization() {
        CRS crs = CRS.initializeWithSampleData();
        String fileName = "crs_test_data.ser";

        crs.saveToFile(fileName);
        CRS loadedCrs = CRS.loadFromFile(fileName);

        assertNotNull(loadedCrs, "Loaded CRS object should not be null.");
        assertEquals(crs.hospitals.size(), loadedCrs.hospitals.size(), "Hospital data should match after deserialization.");
    }
}
