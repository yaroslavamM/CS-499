package snhu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    /**
     * Tests for the addTask method in the TaskService class.
     * The addTask method is responsible for adding a new Task into the storage.
     */
    @Test
    void testAddTask() {
        TaskService taskService = new TaskService();
        Task task1 = new Task("name", "description");
        // We can add a valid Task
        assertDoesNotThrow(() -> {
            taskService.addTask(task1);
        });
        // Throw exception when Task ID already exists
        IllegalArgumentException error1 = assertThrows(IllegalArgumentException.class, () -> {
            taskService.addTask(task1);
        });
        assertEquals("Task with ID 1 already exists", error1.getMessage());
        // Throw exception when the Task is not valid
        IllegalArgumentException error2 = assertThrows(IllegalArgumentException.class, () -> {
            Task task = new Task(null, null);
            taskService.addTask(task);
        });
        assertEquals("Name is required", error2.getMessage());
    }

    /**
     * Tests for the deleteTask method in the TaskService class.
     * The deleteTask method is responsible for removing an existing Task from the storage.
     */
    @Test
    void testDeleteTask() {
        TaskService taskService = new TaskService();
        Task task = new Task("name", "description");
        taskService.addTask(task);
        // We can remove an existing Task
        assertDoesNotThrow(() -> {
            taskService.deleteTask(task.getId());
        });
        // Throw exception when the Task does not exist
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> {
            taskService.deleteTask(task.getId());
        });
        assertEquals("Task with ID " + task.getId() + " does not exist", error.getMessage());
    }

    /**
     * Tests for the updateTask method in the TaskService class.
     * The updateTask method is responsible for updating an existing Task in the storage.
     */
    @Test
    void testUpdateTask() {
        TaskService taskService = new TaskService();
        Task task = new Task("name", "description");
        taskService.addTask(task);
        // We can update an existing Task
        assertDoesNotThrow(() -> {
            taskService.updateTask(task.getId(), "new name", "new description");
        });
        // Throw exception when the Task does not exist
        IllegalArgumentException error1 = assertThrows(IllegalArgumentException.class, () -> {
            taskService.deleteTask("0");
        });
        assertEquals("Task with ID 0 does not exist", error1.getMessage());
        // Throw exception when the Task update makes it not valid
        IllegalArgumentException error2 = assertThrows(IllegalArgumentException.class, () -> {
            taskService.updateTask(task.getId(), null, null);
        });
        assertEquals("Name is required", error2.getMessage());
    }

}
