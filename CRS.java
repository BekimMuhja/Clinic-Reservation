import java.io.*;
import java.util.*;

public class CRS implements Serializable {
    private static final long serialVersionUID = 1L;

    public static boolean isGuiMode = false; // Default: Non-GUI mode

    public Map<Long, Patient> patients = new HashMap<>();
    public Map<Integer, Hospital> hospitals = new HashMap<>();

    // Save CRS object to a file
    public void saveToFile(String fileName) {
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(this);
            System.out.println("CRS data has been saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving CRS data to file: " + e.getMessage());
        }
    }

    // Load CRS object from a file
    public static CRS loadFromFile(String fileName) {
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            return (CRS) objectIn.readObject();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading CRS data from file: " + e.getMessage());
        }
        return null;
    }

    public void addPatient(Patient patient) {
        if (patients.containsKey(patient.getNationalId())) {
            if (isGuiMode) {
                System.err.println("Duplicate National ID: " + patient.getNationalId());
                return; // Log and return in GUI mode
            } else {
                throw new DuplicateInfoException("This national ID is already used!");
            }
        }
        patients.put(patient.getNationalId(), patient);
    }

    public void addHospital(Hospital hospital) {
        hospitals.put(hospital.getId(), hospital);
    }

    public Hospital getHospital(int id) {
        if (!hospitals.containsKey(id)) {
            throw new IDException("Hospital ID not found: " + id);
        }
        return hospitals.get(id);
    }

    public Patient getPatient(long id) {
        if (!patients.containsKey(id)) {
            throw new IDException("Patient ID not found: " + id);
        }
        return patients.get(id);
    }

    	
      
    public static CRS initializeWithSampleData() {
        CRS crs = new CRS();

        // Create hospitals
        Hospital hospital1 = new Hospital(1, "City Hospital");
        Hospital hospital2 = new Hospital(2, "General Hospital");

        // Create sections
        Section emergency = new Section(101, "Emergency Room");
        Section cardiology = new Section(102, "Cardiology");
        Section pediatrics = new Section(103, "Pediatrics");
        Section oncology = new Section(104, "Oncology");
        Section radiology = new Section(105, "Radiology");

        // Create doctors
        Doctor doc1 = new Doctor("Dr. John Smith", 123456789L, 1111);
        Doctor doc2 = new Doctor("Dr. Elma Brown", 987654321L, 1112);
        Doctor doc3 = new Doctor("Dr. Eve White", 555444333L, 1113);
        Doctor doc4 = new Doctor("Dr. Henry Johnson", 222333444L, 1114); 
        Doctor doc5 = new Doctor("Dr. Susan Taylor", 777888999L, 1115); 
        Doctor doc6 = new Doctor("Dr. Elma Canga", 448548484L, 1119);
        // Add doctors to sections (no duplicate diploma IDs in the same section)
        try {
            emergency.addDoctor(doc1);
            cardiology.addDoctor(doc2);
            pediatrics.addDoctor(doc3);
            oncology.addDoctor(doc4);
            radiology.addDoctor(doc5);
            radiology.addDoctor(doc6);
        } catch (DuplicateInfoException e) {
            System.err.println("Error adding doctor: " + e.getMessage());
        }

        // Add sections to hospitals
        hospital1.addSection(emergency);
        hospital1.addSection(cardiology);
        hospital2.addSection(pediatrics);
        hospital2.addSection(oncology);
        hospital2.addSection(radiology);

        // Add hospitals to CRS
        crs.addHospital(hospital1);
        crs.addHospital(hospital2);

        return crs;
    }


    public boolean makeRendezvous(long patientId, int hospitalId, int sectionId, int diplomaId, Date appointmentDate) {
        Patient patient = getPatient(patientId);

        if (patient == null && isGuiMode) {
            return false; // GUI mode: fail silently
        }

        Hospital hospital = getHospital(hospitalId);
        Section section = hospital.getSections().stream()
                .filter(s -> s.getId() == sectionId)
                .findFirst()
                .orElseThrow(() -> new IDException("Section ID not found: " + sectionId));
        Doctor doctor = section.getDoctors().stream()
                .filter(d -> d.getDiplomaId() == diplomaId)
                .findFirst()
                .orElseThrow(() -> new IDException("Doctor with Diploma ID not found: " + diplomaId));

        return doctor.getSchedule().addRendezvous(patient, appointmentDate);
    }
    
    public boolean cancelReservation(int diplomaId, long patientId, Date appointmentDate) {
        for (Hospital hospital : hospitals.values()) {
            for (Section section : hospital.getSections()) {
                for (Doctor doctor : section.getDoctors()) {
                    if (doctor.getDiplomaId() == diplomaId) {
                        return doctor.getSchedule().removeRendezvous(patientId, appointmentDate);
                    }
                }
            }
        }
        return false; // Reservation not found
    }


    
    public List<Rendezvous> getReservationsByDoctor(int diplomaId) {
        return hospitals.values().stream()
                .flatMap(h -> h.getSections().stream())
                .flatMap(s -> s.getDoctors().stream())
                .filter(d -> d.getDiplomaId() == diplomaId)
                .findFirst()
                .map(d -> d.getSchedule().getSessions())
                .orElse(Collections.emptyList());
    }
}
