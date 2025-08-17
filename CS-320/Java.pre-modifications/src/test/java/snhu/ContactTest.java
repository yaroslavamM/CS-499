package snhu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ContactTest {

    @Test
    void testValidate() {
        // Validate no errors when the Contact is valid
        assertNull(new Contact("first", "last", "1234567890", "address").validate());
        // Validate that the First name is required
        assertEquals("First name is required", new Contact(null, null, null, null).validate());
        // Validate that the First cannot be longer than 10 characters
        assertEquals("First name must be less than 10 characters", new Contact("1234567890+1", null, null, null).validate());
        // Validate that the Last name is required
        assertEquals("Last name is required", new Contact("first", null, null, null).validate());
        // Validate that the Last cannot be longer than 10 characters
        assertEquals("Last name must be less than 10 characters", new Contact("first", "1234567890+1", null, null).validate());
        // Validate that the Phone number is required
        assertEquals("Phone number is required", new Contact("first", "last", null, null).validate());
        // Validate that the Phone must be exactly 10 digits
        assertEquals("Phone number must be 10 digits", new Contact("first", "last", "1234567890+1", null).validate());
        // Validate that the Address is required
        assertEquals("Address is required", new Contact("first", "last", "1234567890", null).validate());
        // Validate that the Address cannot be longer than 30 characters
        assertEquals("Address must be less than 30 characters", new Contact("first", "last", "1234567890", "123456789012345678901234567890+1").validate());
    }

}
