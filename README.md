# Clinic-Reservation
This is a Java project
Project Overview
The Clinic Reservation System is a Java Swing-based GUI application that allows users to book, view, and manage clinic appointments. It provides an easy-to-use interface for patients to schedule appointments with doctors across multiple hospitals and sections.

This system ensures that:

✔ Doctors can only have a maximum of 6 appointments per day.

✔ Appointments are scheduled sequentially from 08:00 AM, with each new appointment having a different time slot.

✔ Users can search for doctors by their diploma ID and view their scheduled appointments.

✔ All doctors, hospitals, and sections are preloaded at startup for easy selection.

Features
✅ Booking Appointments

Patients can enter their name and national ID.

Select a hospital, section, and doctor from dropdown menus.

Choose a date for the appointment.

The system automatically assigns an appointment time based on availability (starting at 08:00 AM).

📅 Viewing Appointments

Users can search for a doctor using their diploma ID.

The system displays all appointments for that doctor.

👨‍⚕️ Viewing All Doctors
A tab lists all doctors along with their diploma IDs for easy reference.

How It Works

1️⃣ Preloaded Data

The system initializes with sample hospitals, sections, and doctors.

2️⃣ Booking an Appointment

A patient enters their details and selects a doctor.

If the doctor has availability, the system assigns an appointment time.

The first appointment is at 08:00 AM, and subsequent ones are scheduled sequentially.

If a doctor is fully booked, the system notifies the user.

3️⃣ Viewing Appointments

Users enter a doctor’s diploma ID to see all their scheduled appointments.

4️⃣ Viewing Doctors

A separate tab displays all doctors with their diploma IDs.

Technologies Used

🟢 Java – Core programming language

🟢 Java Swing – GUI development

🟢 Serialization – Data persistence

🟢 Collections Framework – Managing data structures

Installation & Setup

📌 Prerequisites

Install Java JDK (8 or later)

Install an IDE (Eclipse, IntelliJ, or NetBeans)

🚀 Running the Project

1.Clone the repository

2.Open the project in your IDE

3.Compile and run the main class (ClinicReservationGUI.java)

Contributors

👤 Bekim Muhja – Developer




