package snhu;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * JMH benchmark to measure the performance of AppointmentService operations.
 * This benchmark tests the performance of adding, updating, and deleting appointments
 * with different storage sizes.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
@State(Scope.Benchmark)
public class AppointmentServiceBenchmark {

    /**
     * Different storage sizes to benchmark.
     */
    @Param({"10", "100", "1000"})
    private int storageSize;

    /**
     * The AppointmentService instance to benchmark.
     */
    private AppointmentService appointmentService;

    /**
     * A list to hold references to created appointments for update and delete operations.
     */
    private List<Appointment> createdAppointments;

    /**
     * Appointment ID to use for benchmarking update and delete operations.
     */
    private String appointmentIdToFind;

    /**
     * Setup method that prepares the AppointmentService with a specified number of appointments.
     */
    @Setup
    public void setup() {
        appointmentService = new AppointmentService();
        createdAppointments = new ArrayList<>();

        // Create a future date for appointments (1 week from now)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);

        // Populate the storage with the specified number of appointments
        for (int i = 0; i < storageSize; i++) {
            // Add 10 minutes for each appointment to make them unique
            calendar.add(Calendar.MINUTE, 10);
            Date appointmentDate = calendar.getTime();

            Appointment appointment = new Appointment(appointmentDate, "Appointment " + i);
            appointmentService.addAppointment(appointment);
            createdAppointments.add(appointment);
        }

        // Get an ID from the middle of the list for update and delete benchmarks
        if (!createdAppointments.isEmpty()) {
            appointmentIdToFind = createdAppointments.get(storageSize / 2).getId();
        }
    }

    /**
     * Benchmark for the addAppointment operation.
     */
    @Benchmark
    public void addAppointmentBenchmark(Blackhole blackhole) {
        // Create a future date for the new appointment (30 days from now)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        // Add nanoseconds to ensure the date is unique for each iteration
        calendar.add(Calendar.MILLISECOND, (int)(System.nanoTime() % 1000));
        Date appointmentDate = calendar.getTime();

        // Create a new appointment for each iteration
        Appointment appointment = new Appointment(appointmentDate, "Benchmark Appointment " + System.nanoTime());
        try {
            appointmentService.addAppointment(appointment);
            // Use Blackhole to prevent dead code elimination
            blackhole.consume(appointment);
            // Clean up after the benchmark to maintain the original state
            appointmentService.deleteAppointment(appointment.getId());
        } catch (IllegalArgumentException e) {
            // Consume the exception to prevent it from affecting the benchmark
            blackhole.consume(e);
        }
    }

    /**
     * Benchmark for the updateAppointment operation.
     */
    @Benchmark
    public void updateAppointmentBenchmark(Blackhole blackhole) {
        if (appointmentIdToFind != null) {
            try {
                // Create a future date for the updated appointment (60 days from now)
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, 60);
                // Add nanoseconds to ensure the date is unique for each iteration
                calendar.add(Calendar.MILLISECOND, (int)(System.nanoTime() % 1000));
                Date newDate = calendar.getTime();

                // Update the appointment
                String newDesc = "Updated Appointment " + System.nanoTime();
                appointmentService.updateAppointment(appointmentIdToFind, newDate, newDesc);
                blackhole.consume(newDesc);
                blackhole.consume(newDate);
            } catch (IllegalArgumentException e) {
                blackhole.consume(e);
            }
        }
    }

    /**
     * Benchmark for the deleteAppointment operation followed by an add operation.
     */
    @Benchmark
    public void deleteAndAddAppointmentBenchmark(Blackhole blackhole) {
        if (!createdAppointments.isEmpty()) {
            // Use an appointment from the middle of the list
            Appointment appointmentToDeleteAndAdd = createdAppointments.get(storageSize / 2);
            String id = appointmentToDeleteAndAdd.getId();
            Date date = appointmentToDeleteAndAdd.getDate();
            String description = appointmentToDeleteAndAdd.getDescription();

            try {
                // Delete the appointment
                appointmentService.deleteAppointment(id);
                blackhole.consume(id);

                // Create a new date 10 minutes later to avoid conflicts
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.MINUTE, 10);
                Date newDate = calendar.getTime();

                // Add a new appointment
                Appointment newAppointment = new Appointment(newDate, description);
                appointmentService.addAppointment(newAppointment);
                blackhole.consume(newAppointment);

                // Update our reference
                createdAppointments.set(storageSize / 2, newAppointment);
                appointmentIdToFind = newAppointment.getId();
            } catch (IllegalArgumentException e) {
                blackhole.consume(e);
            }
        }
    }

    /**
     * Benchmark for the findAppointmentById operation.
     */
    @Benchmark
    public void findAppointmentByIdBenchmark(Blackhole blackhole) {
        if (appointmentIdToFind != null) {
            // This is a private method in AppointmentService, so we simulate it by
            // performing an update which will trigger the findAppointmentById method
            try {
                // Create a future date for the test update (90 days from now)
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, 90);
                Date testDate = calendar.getTime();

                appointmentService.updateAppointment(appointmentIdToFind, testDate, "Find Test " + System.nanoTime());
                blackhole.consume(appointmentIdToFind);
                blackhole.consume(testDate);
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
                .include(AppointmentServiceBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }

}
