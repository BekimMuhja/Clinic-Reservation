import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClinicReservationGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private CRS crs;
    private JTextField patientNameField, patientIdField, appointmentDateField, diplomaIdField;
    private JComboBox<Hospital> hospitalDropdown;
    private JComboBox<Section> sectionDropdown;
    private JComboBox<Doctor> doctorDropdown;
    private JTextArea appointmentDisplayArea, doctorDisplayArea;

    public ClinicReservationGUI(CRS crs) {
        this.crs = crs;

        setTitle("Clinic Reservation System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Book Appointment", createBookingPanel());
        tabbedPane.addTab("View Appointments", createViewAppointmentsPanel());
        tabbedPane.addTab("View All Doctors", createViewDoctorsPanel());
        tabbedPane.addTab("Manage Reservations", createManageReservationsPanel());


        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createBookingPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 2));

        patientNameField = new JTextField();
        patientIdField = new JTextField();
        appointmentDateField = new JTextField("dd-MM-yyyy");
        hospitalDropdown = new JComboBox<>(crs.hospitals.values().toArray(new Hospital[0]));
        sectionDropdown = new JComboBox<>();
        doctorDropdown = new JComboBox<>();

        hospitalDropdown.addActionListener(e -> updateSections());
        sectionDropdown.addActionListener(e -> updateDoctors());
        hospitalDropdown.setSelectedIndex(0); // Trigger section/doctor update

        JButton bookButton = new JButton("Book Appointment");
        bookButton.addActionListener(e -> bookAppointment());

        panel.add(new JLabel("Patient Name:"));
        panel.add(patientNameField);
        panel.add(new JLabel("Patient National ID:"));
        panel.add(patientIdField);
        panel.add(new JLabel("Appointment Date (dd-MM-yyyy):"));
        panel.add(appointmentDateField);
        panel.add(new JLabel("Hospital:"));
        panel.add(hospitalDropdown);
        panel.add(new JLabel("Section:"));
        panel.add(sectionDropdown);
        panel.add(new JLabel("Doctor:"));
        panel.add(doctorDropdown);
        panel.add(new JLabel(""));
        panel.add(bookButton);

        return panel;
    }

    private JPanel createViewAppointmentsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        diplomaIdField = new JTextField();
        JButton searchButton = new JButton("Search by Diploma ID");
        appointmentDisplayArea = new JTextArea(20, 30);
        appointmentDisplayArea.setEditable(false);

        searchButton.addActionListener(e -> viewAppointments());

        JPanel inputPanel = new JPanel(new GridLayout(1, 2));
        inputPanel.add(new JLabel("Enter Doctor's Diploma ID:"));
        inputPanel.add(diplomaIdField);
        inputPanel.add(searchButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(appointmentDisplayArea), BorderLayout.CENTER);

        return panel;
    }
    
    private JPanel createManageReservationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JComboBox<Doctor> doctorDropdown = new JComboBox<>();
        JTextArea reservationDisplayArea = new JTextArea(20, 30);
        reservationDisplayArea.setEditable(false);
        JTextField patientIdField = new JTextField();
        JTextField appointmentDateField = new JTextField("dd-MM-yyyy");

        JButton cancelButton = new JButton("Cancel Reservation");

        // Populate doctor dropdown
        for (Hospital hospital : crs.hospitals.values()) {
            for (Section section : hospital.getSections()) {
                for (Doctor doctor : section.getDoctors()) {
                    doctorDropdown.addItem(doctor);
                }
            }
        }

        // Update reservations display when a doctor is selected
        doctorDropdown.addActionListener(e -> {
            Doctor selectedDoctor = (Doctor) doctorDropdown.getSelectedItem();
            if (selectedDoctor != null) {
                List<Rendezvous> reservations = selectedDoctor.getSchedule().getSessions();
                if (reservations.isEmpty()) {
                    reservationDisplayArea.setText("No reservations found for this doctor.");
                } else {
                    StringBuilder sb = new StringBuilder("Reservations:\n");
                    for (Rendezvous r : reservations) {
                        sb.append("Patient: ").append(r.getPatient())
                                .append(", Date: ").append(r.getDateTime()).append("\n");
                    }
                    reservationDisplayArea.setText(sb.toString());
                }
            }
        });

        // Cancel a reservation
        cancelButton.addActionListener(e -> {
            try {
                Doctor selectedDoctor = (Doctor) doctorDropdown.getSelectedItem();
                long patientId = Long.parseLong(patientIdField.getText());
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date appointmentDate = sdf.parse(appointmentDateField.getText());

                if (selectedDoctor != null) {
                    boolean success = crs.cancelReservation(selectedDoctor.getDiplomaId(), patientId, appointmentDate);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Reservation canceled successfully.");
                        // Refresh the reservations display
                        doctorDropdown.setSelectedItem(selectedDoctor);
                    } else {
                        JOptionPane.showMessageDialog(this, "Reservation not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a doctor.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid patient ID.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });


        // Layout
        JPanel inputPanel = new JPanel(new GridLayout(4, 3));
        inputPanel.add(new JLabel("Select Doctor:"));
        inputPanel.add(doctorDropdown);
        inputPanel.add(new JLabel("Enter Patient National ID:"));
        inputPanel.add(patientIdField);
        inputPanel.add(new JLabel("Enter Appointment Date (dd-MM-yyyy):"));
        inputPanel.add(appointmentDateField);
        inputPanel.add(cancelButton);


        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(reservationDisplayArea), BorderLayout.CENTER);

        return panel;
    }


    private JPanel createViewDoctorsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        doctorDisplayArea = new JTextArea(20, 30);
        doctorDisplayArea.setEditable(false);
        populateDoctorDisplay();

        panel.add(new JScrollPane(doctorDisplayArea), BorderLayout.CENTER);

        return panel;
    }

    private void populateDoctorDisplay() {
        StringBuilder sb = new StringBuilder("Doctors List:\n");
        for (Hospital hospital : crs.hospitals.values()) {
            sb.append("Hospital: ").append(hospital.getName()).append("\n");
            for (Section section : hospital.getSections()) {
                sb.append("  Section: ").append(section.getName()).append("\n");
                for (Doctor doctor : section.getDoctors()) {
                    sb.append("    Doctor: ").append(doctor.getName())
                      .append(" (National ID: ").append(doctor.getNationalId())
                      .append(", Diploma ID: ").append(doctor.getDiplomaId()).append(")\n");
                }
            }
        }
        doctorDisplayArea.setText(sb.toString());
    }


    private void updateSections() {
        Hospital selectedHospital = (Hospital) hospitalDropdown.getSelectedItem();
        if (selectedHospital != null) {
            sectionDropdown.removeAllItems();
            for (Section section : selectedHospital.getSections()) {
                sectionDropdown.addItem(section);
            }
            updateDoctors();
        }
    }

    private void updateDoctors() {
        Section selectedSection = (Section) sectionDropdown.getSelectedItem();
        if (selectedSection != null) {
            doctorDropdown.removeAllItems();
            for (Doctor doctor : selectedSection.getDoctors()) {
                doctorDropdown.addItem(doctor);
            }
        }
    }
    
    private void bookAppointment() {
        try {
            String name = patientNameField.getText();
            long nationalId = Long.parseLong(patientIdField.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date appointmentDate = sdf.parse(appointmentDateField.getText());

            // Add the patient
            Patient patient = new Patient(name, nationalId);
            crs.addPatient(patient);

            // Check if the patient already exists
            if (crs.getPatient(nationalId) == null) {
                JOptionPane.showMessageDialog(this, "Error: National ID already in use.");
                return;
            }

            // Get selected hospital, section, and doctor
            Hospital hospital = (Hospital) hospitalDropdown.getSelectedItem();
            Section section = (Section) sectionDropdown.getSelectedItem();
            Doctor doctor = (Doctor) doctorDropdown.getSelectedItem();

            // Generate appointment time based on existing appointments
            Date appointmentWithTime = generateAppointmentTime(doctor, appointmentDate);

            // Attempt to make a reservation
            if (crs.makeRendezvous(nationalId, hospital.getId(), section.getId(), doctor.getDiplomaId(), appointmentWithTime)) {
                JOptionPane.showMessageDialog(this, "Appointment successfully booked!");
            } else {
                JOptionPane.showMessageDialog(this, "Doctor's schedule is full for the selected date.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
   
    private Date generateAppointmentTime(Doctor doctor, Date baseDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseDate);

        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        List<Rendezvous> reservations = doctor.getSchedule().getSessions();
        calendar.add(Calendar.HOUR_OF_DAY, reservations.size());

        return calendar.getTime();
    }

    private void viewAppointments() {
        try {
            int diplomaId = Integer.parseInt(diplomaIdField.getText());

            // Check if the doctor exists
            Doctor selectedDoctor = null;
            for (Hospital hospital : crs.hospitals.values()) {
                for (Section section : hospital.getSections()) {
                    for (Doctor doctor : section.getDoctors()) {
                        if (doctor.getDiplomaId() == diplomaId) {
                            selectedDoctor = doctor;
                            break;
                        }
                    }
                    if (selectedDoctor != null) break;
                }
                if (selectedDoctor != null) break;
            }

            // If no doctor found, show error
            if (selectedDoctor == null) {
                JOptionPane.showMessageDialog(this, "Error: Doctor with Diploma ID " + diplomaId + " does not exist.");
                return;
            }

            // Get reservations for the found doctor
            List<Rendezvous> appointments = selectedDoctor.getSchedule().getSessions();

            if (appointments.isEmpty()) {
                appointmentDisplayArea.setText("No appointments found for this doctor.");
            } else {
                StringBuilder sb = new StringBuilder("Appointments for Diploma ID: ").append(diplomaId).append("\n");
                for (Rendezvous r : appointments) {
                    sb.append(r).append("\n");
                }
                appointmentDisplayArea.setText(sb.toString());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid diploma ID.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        CRS.isGuiMode = true;

        String fileName = "crs_data.ser"; // File name for serialized data
        CRS loadedCrs = CRS.loadFromFile(fileName);

        // Assign crs based on whether data was loaded successfully
        CRS crs = (loadedCrs != null) ? loadedCrs : CRS.initializeWithSampleData();

        // Add a shutdown hook to save CRS data on application close
        Runtime.getRuntime().addShutdownHook(new Thread(() -> crs.saveToFile(fileName)));

        // Launch the GUI
        SwingUtilities.invokeLater(() -> new ClinicReservationGUI(crs).setVisible(true));
    }


}