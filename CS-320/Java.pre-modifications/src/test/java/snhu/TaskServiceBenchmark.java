package snhu;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * JMH benchmark to measure the performance of TaskService operations.
 * This benchmark tests the performance of adding, updating, and deleting tasks
 * with different storage sizes.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
@State(Scope.Benchmark)
public class TaskServiceBenchmark {

    /**
     * Different storage sizes to benchmark.
     */
    @Param({"10", "100", "1000"})
    private int storageSize;

    /**
     * The TaskService instance to benchmark.
     */
    private TaskService taskService;

    /**
     * A list to hold references to created tasks for update and delete operations.
     */
    private List<Task> createdTasks;

    /**
     * Task ID to use for benchmarking update and delete operations.
     */
    private String taskIdToFind;

    /**
     * Setup method that prepares the TaskService with a specified number of tasks.
     */
    @Setup
    public void setup() {
        taskService = new TaskService();
        createdTasks = new ArrayList<>();

        // Populate the storage with the specified number of tasks
        for (int i = 0; i < storageSize; i++) {
            Task task = new Task("Task " + i, "Description " + i);
            taskService.addTask(task);
            createdTasks.add(task);
        }

        // Get an ID from the middle of the list to update and delete benchmarks
        if (!createdTasks.isEmpty()) {
            taskIdToFind = createdTasks.get(storageSize / 2).getId();
        }
    }

    /**
     * Benchmark for the addTask operation.
     */
    @Benchmark
    public void addTaskBenchmark(Blackhole blackhole) {
        // Create a new task for each iteration to avoid ID conflicts
        Task task = new Task("New Task", "Description " + System.nanoTime());
        try {
            taskService.addTask(task);
            // Use Black hole to prevent dead code elimination
            blackhole.consume(task);
            // Clean up after the benchmark to maintain the original state
            taskService.deleteTask(task.getId());
        } catch (IllegalArgumentException e) {
            // Consume the exception to prevent it from affecting the benchmark
            blackhole.consume(e);
        }
    }

    /**
     * Benchmark for the updateTask operation.
     */
    @Benchmark
    public void updateTaskBenchmark(Blackhole blackhole) {
        if (taskIdToFind != null) {
            try {
                // Use a timestamp to ensure unique description
                String newDesc = "Updated Description " + System.nanoTime();
                taskService.updateTask(taskIdToFind, "Updated Task", newDesc);
                blackhole.consume(newDesc);
            } catch (IllegalArgumentException e) {
                blackhole.consume(e);
            }
        }
    }

    /**
     * Benchmark for the deleteTask operation.
     */
    @Benchmark
    public void deleteAndAddTaskBenchmark(Blackhole blackhole) {
        if (!createdTasks.isEmpty()) {
            // Use a task from the middle of the list
            Task taskToDeleteAndAdd = createdTasks.get(storageSize / 2);
            String id = taskToDeleteAndAdd.getId();
            String name = taskToDeleteAndAdd.getName();
            String description = taskToDeleteAndAdd.getDescription();

            try {
                // Delete the task
                taskService.deleteTask(id);
                blackhole.consume(id);

                // Re-add the same task with a new instance (since we can't reuse the ID)
                Task newTask = new Task(name, description);
                taskService.addTask(newTask);
                blackhole.consume(newTask);

                // Update our reference
                createdTasks.set(storageSize / 2, newTask);
                taskIdToFind = newTask.getId();
            } catch (IllegalArgumentException e) {
                blackhole.consume(e);
            }
        }
    }

    /**
     * Benchmark for the findTaskById operation.
     */
    @Benchmark
    public void findTaskByIdBenchmark(Blackhole blackhole) {
        if (taskIdToFind != null) {
            // This is a private method in TaskService, so we simulate it by
            // adding a task with a known ID and then performing an update
            // which will trigger the findTaskById method
            try {
                taskService.updateTask(taskIdToFind, "Find Task Test", "Find Description Test");
                blackhole.consume(taskIdToFind);
            } catch (IllegalArgumentException e) {
                blackhole.consume(e);
            }
        }
    }

    /**
     * Main method to run the benchmark from the command line.
     */
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(TaskServiceBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }

}
