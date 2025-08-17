package snhu;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    @Test
    void testValidate() {
        // Validate no errors when the Appointment is valid
        assertNull(new Appointment(new Date(), "description").validate());

        // Validate that the Description is required
        assertEquals("Description is required", new Appointment(new Date(), null).validate());
        // Validate that the Description cannot be longer than 50 characters
        assertEquals("Description must be less than 50 characters", new Appointment(new Date(), "12345678901234567890123456789012345678901234567890+1").validate());

        // Validate that the Date is required
        assertEquals("Date is required", new Appointment(null, "description").validate());
        // Validate that the Date can not be in the past
        assertEquals("Date can not be in the past", new Appointment(Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()), "description").validate());
    }

}
