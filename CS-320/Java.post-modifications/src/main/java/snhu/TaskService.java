package snhu;

import java.util.HashMap;
import java.util.Map;

public class TaskService {

    /**
     * A storage list that holds Task objects.
     */
    private final Map<String, Task> storage = new HashMap<>();

    /**
     * Adds a new Task to the storage.
     * Ensures the Task is valid and does not already exist in the storage.
     *
     * @param task the Task object to be added to the storage
     * @throws IllegalArgumentException if the Task is invalid or already exists in the storage
     */
    public void addTask(final Task task) {
        // Make sure the Task's ID is unique
        if (storage.containsKey(task.getId())) {
            throw new IllegalArgumentException("Task with ID " + task.getId() + " already exists");
        }

        // Make sure the Task is valid
        if (task.validate() != null) {
            throw new IllegalArgumentException(task.validate());
        }

        // Store the Task
        storage.put(task.getId(), task);
    }

    /**
     * Deletes a Task from the storage by its unique identifier.
     *
     * @param id the unique identifier of the Task to be deleted
     * @throws IllegalArgumentException if a Task with the specified ID does not exist
     */
    public void deleteTask(final String id) {
        // Make sure the task exists
        if (!storage.containsKey(id)) {
            throw new IllegalArgumentException("Task with ID " + id + " does not exist");
        }

        // Remove the task
        storage.remove(id);
    }

    /**
     * Updates an existing Task in the storage.
     * Validates the new Task information and replaces the current Task details if valid.
     *
     * @param id the unique identifier of the Task to be updated, must exist in the storage
     * @param name the updated name of the Task
     * @param description the updated description of the Task
     * @throws IllegalArgumentException if the Task does not exist or if the new Task information is invalid
     */
    public void updateTask(
            final String id,
            final String name,
            final String description
    ) {
        // Find the task with the given ID
        Task existingTask = storage.get(id);

        // Make sure the task exists
        if (existingTask == null) {
            throw new IllegalArgumentException("Task with ID " + id + " does not exist");
        }

        // Make sure the Task update is valid
        final Task task = new Task(name, description);
        if (task.validate() != null) {
            throw new IllegalArgumentException(task.validate());
        }

        // Update the Task
        existingTask.setName(name);
        existingTask.setDescription(description);
    }

}
