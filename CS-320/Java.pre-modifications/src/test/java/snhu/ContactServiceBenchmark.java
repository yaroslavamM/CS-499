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
 * JMH benchmark to measure the performance of ContactService operations.
 * This benchmark tests the performance of adding, updating, and deleting contacts
 * with different storage sizes.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
@State(Scope.Benchmark)
public class ContactServiceBenchmark {

    /**
     * Different storage sizes to benchmark.
     */
    @Param({"10", "100", "1000"})
    private int storageSize;

    /**
     * The ContactService instance to benchmark.
     */
    private ContactService contactService;

    /**
     * A list to hold references to created contacts for update and delete operations.
     */
    private List<Contact> createdContacts;

    /**
     * Contact ID to use for benchmarking update and delete operations.
     */
    private String contactIdToFind;

    /**
     * Setup method that prepares the ContactService with a specified number of contacts.
     */
    @Setup
    public void setup() {
        contactService = new ContactService();
        createdContacts = new ArrayList<>();

        // Populate the storage with the specified number of contacts
        for (int i = 0; i < storageSize; i++) {
            Contact contact = new Contact(
                    "FirstName" + i,
                    "LastName" + i,
                    "555" + String.format("%07d", i),  // Ensure 10-digit phone number
                    "Address " + i
            );
            contactService.addContact(contact);
            createdContacts.add(contact);
        }

        // Get an ID from the middle of the list for update and delete benchmarks
        if (!createdContacts.isEmpty()) {
            contactIdToFind = createdContacts.get(storageSize / 2).getId();
        }
    }

    /**
     * Benchmark for the addContact operation.
     */
    @Benchmark
    public void addContactBenchmark(Blackhole blackhole) {
        // Create a unique contact for each iteration
        long timestamp = System.nanoTime();
        Contact contact = new Contact(
                "NewFirst" + timestamp,
                "NewLast" + timestamp,
                "9" + String.format("%09d", timestamp % 1000000000),  // Ensure 10-digit phone
                "New Address " + timestamp
        );

        try {
            contactService.addContact(contact);
            // Use Blackhole to prevent dead code elimination
            blackhole.consume(contact);
            // Clean up after the benchmark to maintain the original state
            contactService.deleteContact(contact.getId());
        } catch (IllegalArgumentException e) {
            // Consume the exception to prevent it from affecting the benchmark
            blackhole.consume(e);
        }
    }

    /**
     * Benchmark for the updateContact operation.
     */
    @Benchmark
    public void updateContactBenchmark(Blackhole blackhole) {
        if (contactIdToFind != null) {
            try {
                // Use a timestamp to ensure unique values
                long timestamp = System.nanoTime();
                String firstName = "UpdatedFirst" + timestamp;
                String lastName = "UpdatedLast" + timestamp;
                String phone = "8" + String.format("%09d", timestamp % 1000000000);  // Ensure 10-digit phone
                String address = "Updated Address " + timestamp;

                contactService.updateContact(contactIdToFind, firstName, lastName, phone, address);
                blackhole.consume(firstName);
                blackhole.consume(lastName);
                blackhole.consume(phone);
                blackhole.consume(address);
            } catch (IllegalArgumentException e) {
                blackhole.consume(e);
            }
        }
    }

    /**
     * Benchmark for the deleteContact operation followed by an add operation.
     */
    @Benchmark
    public void deleteAndAddContactBenchmark(Blackhole blackhole) {
        if (!createdContacts.isEmpty()) {
            // Use a contact from the middle of the list
            Contact contactToDeleteAndAdd = createdContacts.get(storageSize / 2);
            String id = contactToDeleteAndAdd.getId();
            String firstName = contactToDeleteAndAdd.getFirstName();
            String lastName = contactToDeleteAndAdd.getLastName();
            String phone = contactToDeleteAndAdd.getPhone();
            String address = contactToDeleteAndAdd.getAddress();

            try {
                // Delete the contact
                contactService.deleteContact(id);
                blackhole.consume(id);

                // Create a new contact with similar data but will have a new ID
                Contact newContact = new Contact(firstName, lastName, phone, address);
                contactService.addContact(newContact);
                blackhole.consume(newContact);

                // Update our reference
                createdContacts.set(storageSize / 2, newContact);
                contactIdToFind = newContact.getId();
            } catch (IllegalArgumentException e) {
                blackhole.consume(e);
            }
        }
    }

    /**
     * Benchmark for the findContactById operation.
     */
    @Benchmark
    public void findContactByIdBenchmark(Blackhole blackhole) {
        if (contactIdToFind != null) {
            // This is a private method in ContactService, so we simulate it by
            // performing an update which will trigger the findContactById method
            try {
                // Create unique values to ensure the update is valid
                long timestamp = System.nanoTime();
                contactService.updateContact(
                        contactIdToFind,
                        "FindTest" + timestamp,
                        "LastTest" + timestamp,
                        "7" + String.format("%09d", timestamp % 1000000000),
                        "Find Test Address"
                );
                blackhole.consume(contactIdToFind);
            } catch (IllegalArgumentException e) {
                blackhole.consume(e);
            }
        }
    }

    /**
     * Additional benchmark to test performance with complex queries.
     * This simulates finding contacts by a partial name match, which would
     * be a common operation in a real application.
     */
    @Benchmark
    public void findContactsByNameBenchmark(Blackhole blackhole) {
        // This is a simulated search since ContactService doesn't have this method
        String searchPattern = "Name";
        List<Contact> results = new ArrayList<>();

        for (Contact contact : createdContacts) {
            if (contact.getFirstName().contains(searchPattern) ||
                contact.getLastName().contains(searchPattern)) {
                results.add(contact);
            }
        }

        blackhole.consume(results);
    }

    /**
     * Main method to run the benchmark from the command line.
     */
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(ContactServiceBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }

}
