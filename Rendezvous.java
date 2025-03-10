import java.io.Serializable;
import java.util.Date;

public class Rendezvous implements Serializable {
    private static final long serialVersionUID = 1L;

    private Patient patient;
    private Date dateTime;

    public Rendezvous(Patient patient, Date dateTime) {
        this.patient = patient;
        this.dateTime = dateTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Appointment with " + patient + " on " + dateTime;
    }
}
