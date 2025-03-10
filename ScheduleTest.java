import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class ScheduleTest {

    @Test
    void testAddRendezvous() {
        Schedule schedule = new Schedule(2);
        Patient patient = new Patient("John Doe", 123456789L);
        Date date = new Date();

        assertTrue(schedule.addRendezvous(patient, date), "Rendezvous should be added successfully.");
    }

    @Test
    void testMaxPatientPerDayLimit() {
        Schedule schedule = new Schedule(1);
        Patient patient1 = new Patient("John Doe", 123456789L);
        Patient patient2 = new Patient("Jane Doe", 987654321L);
        Date date = new Date();

        assertTrue(schedule.addRendezvous(patient1, date), "First Rendezvous should be added successfully.");
        assertFalse(schedule.addRendezvous(patient2, date), "Second Rendezvous should not be added due to max limit.");
    }

    @Test
    void testRemoveRendezvous() {
        Schedule schedule = new Schedule(2);
        Patient patient = new Patient("John Doe", 123456789L);
        Date date = new Date();

        schedule.addRendezvous(patient, date);
        assertTrue(schedule.removeRendezvous(123456789L, date), "Rendezvous should be removed successfully.");
    }
}
