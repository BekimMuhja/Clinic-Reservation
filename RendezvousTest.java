import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class RendezvousTest {

    @Test
    void testRendezvousCreation() {
        Patient patient = new Patient("John Doe", 123456789L);
        Date date = new Date();
        Rendezvous rendezvous = new Rendezvous(patient, date);

        assertEquals(patient, rendezvous.getPatient(), "Patient should match the one set in the Rendezvous.");
        assertEquals(date, rendezvous.getDateTime(), "Date should match the one set in the Rendezvous.");
    }

    @Test
    void testToString() {
        Patient patient = new Patient("John Doe", 123456789L);
        Date date = new Date();
        Rendezvous rendezvous = new Rendezvous(patient, date);

        String expected = "Appointment with John Doe (ID: 123456789) on " + date;
        assertEquals(expected, rendezvous.toString(), "toString method should return the expected string.");
    }
}
