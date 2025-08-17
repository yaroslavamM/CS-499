package snhu;

import java.util.concurrent.atomic.AtomicInteger;

public class Task {

    private static final AtomicInteger taskIdGenerator = new AtomicInteger(0);

    /**
     * Unique ID of the Task.
     */
    private final String id;

    /**
     * Name of the Task.
     */
    private String name;

    /**
     * Description of the Task.
     */
    private String description;

    /**
     * Constructs a new Task with the specified parameters.
     *
     * @param name        the name of the Task
     * @param description the description of the Task
     */
    public Task(final String name, final String description) {
        this.id = generateId();
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the unique identifier of the Task.
     *
     * @return the unique identifier as a String
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the name of the Task.
     *
     * @return the name of the Task as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Task.
     *
     * @param name a new name of the Task.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the description of the Task.
     *
     * @return the description of the Task as a String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description a new description of the Task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Validates the fields of the Task object.
     * Checks for null values and length constraints for id, name, and description fields.
     *
     * @return a validation error message as a String if any validation rule is violated.
     * Returns null if all fields are valid.
     */
    public String validate() {
        if (getName() == null) {
            return "Name is required";
        }
        if (getName().length() > 20) {
            return "Name must be less than 20 characters";
        }
        if (getDescription() == null) {
            return "Description is required";
        }
        if (getDescription().length() > 50) {
            return "Description must be less than 50 characters";
        }
        return null;
    }

    /**
     * Generate a unique ID that is no longer then 10 digits.
     *
     * @return a unique ID.
     */
    private static synchronized String generateId() {
        // Take the next ID from the sequence generator
        String nextId = Integer.toString(taskIdGenerator.incrementAndGet(), 10);
        if (nextId.length() > 10) {
            // Rest the sequence generator if the ID is too long
            taskIdGenerator.set(1);
            // Generate the ID again
            return generateId();
        }
        return nextId;
    }

}
