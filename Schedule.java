import java.io.Serializable;
import java.util.*;

public class Schedule implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Rendezvous> sessions = new ArrayList<>();
    private int maxPatientPerDay;

    public Schedule(int maxPatientPerDay) {
        this.maxPatientPerDay = maxPatientPerDay;
    }

    public boolean addRendezvous(Patient patient, Date desiredDate) {
        // Count reservations for the desired date
        long count = sessions.stream()
                .filter(r -> isSameDay(r.getDateTime(), desiredDate))
                .count();

        if (count >= maxPatientPerDay) {
            return false; // No more slots for this date
        }

        sessions.add(new Rendezvous(patient, desiredDate));
        return true;
    }

    public boolean removeRendezvous(long patientId, Date appointmentDate) {
        return sessions.removeIf(r -> 
            r.getPatient().getNationalId() == patientId && isSameDay(r.getDateTime(), appointmentDate)
        );
    }

    public List<Rendezvous> getSessions() {
        return sessions;
    }

    private boolean isSameDay(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }
}
