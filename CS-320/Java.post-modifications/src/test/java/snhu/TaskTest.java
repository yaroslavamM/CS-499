package snhu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testValidate() {
        // Validate no errors when the Task is valid
        assertNull(new Task("name", "description").validate());
        // Validate that the Name is required
        assertEquals("Name is required", new Task(null, null).validate());
        // Validate that the Name cannot be longer than 20 characters
        assertEquals("Name must be less than 20 characters", new Task("12345678901234567890+1", null).validate());
        // Validate that the Description is required
        assertEquals("Description is required", new Task("name", null).validate());
        // Validate that the Last cannot be longer than 50 characters
        assertEquals("Description must be less than 50 characters", new Task("first", "12345678901234567890123456789012345678901234567890+1").validate());
    }

}
