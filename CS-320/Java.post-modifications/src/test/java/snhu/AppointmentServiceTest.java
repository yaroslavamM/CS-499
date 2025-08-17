package snhu;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceTest {

    /**
     * Tests for the addAppointment method in the AppointmentService class.
     * The addAppointment method is responsible for adding a new Appointment into the storage.
     */
    @Test
    void testAddAppointment() {
        AppointmentService appointmentService = new AppointmentService();
        // We can add a valid Appointment
        assertDoesNotThrow(() -> {
            Appointment appointment = new Appointment(new Date(), "description");
            appointmentService.addAppointment(appointment);
        });
        // Throw exception when the Appointment is not valid
        IllegalArgumentException error1 = assertThrows(IllegalArgumentException.class, () -> {
            Appointment appointment = new Appointment(new Date(), null);
            appointmentService.addAppointment(appointment);
        });
        assertEquals("Description is required", error1.getMessage());
    }

    /**
     * Tests for the deleteAppointment method in the AppointmentService class.
     * The deleteAppointment method is responsible for removing an existing Appointment from the storage.
     */
    @Test
    void testDeleteAppointment() {
        AppointmentService appointmentService = new AppointmentService();
        Appointment appointment = new Appointment(new Date(), "description");
        appointmentService.addAppointment(appointment);
        // We can remove an existing Appointment
        assertDoesNotThrow(() -> {
            appointmentService.deleteAppointment(appointment.getId());
        });
        // Throw exception when the Appointment does not exist
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.deleteAppointment(appointment.getId());
        });
        assertEquals("Appointment with ID " + appointment.getId() + " does not exist", error.getMessage());
    }

    /**
     * Tests for the updateAppointment method in the AppointmentService class.
     * The updateAppointment method is responsible for updating an existing Appointment in the storage.
     */
    @Test
    void testUpdateAppointment() {
        AppointmentService appointmentService = new AppointmentService();
        Appointment appointment = new Appointment(new Date(), "description");
        appointmentService.addAppointment(appointment);
        // We can update an existing Appointment
        assertDoesNotThrow(() -> {
            appointmentService.updateAppointment(appointment.getId(), Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()), "new description");
        });
        // Throw exception when the Appointment does not exist
        IllegalArgumentException error1 = assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.updateAppointment("-1", new Date(), "description");
        });
        assertEquals("Appointment with ID -1 does not exist", error1.getMessage());
        // Throw exception when the Appointment update makes it not valid
        IllegalArgumentException error2 = assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.updateAppointment(appointment.getId(), null, null);
        });
        assertEquals("Description is required", error2.getMessage());
    }

}
