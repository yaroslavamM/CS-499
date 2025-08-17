package contact

import (
	"strings"
	"testing"
)

// TestAddContact tests the AddContact method of the ContactService
func TestAddContact(t *testing.T) {
	contactService := NewContactService()

	// Test adding a valid contact
	contact := NewContactFull("1", "first", "last", "1234567890", "address")
	err := contactService.AddContact(contact)
	if err != nil {
		t.Errorf("Expected no error when adding valid contact, got: %v", err)
	}

	// Test adding a contact with duplicate ID
	duplicateContact := NewContactFull("1", "first", "last", "1234567890", "address")
	err = contactService.AddContact(duplicateContact)
	if err == nil {
		t.Error("Expected error when adding contact with duplicate ID, got nil")
	}
	expectedErrMsg := "contact with ID 1 already exists"
	if err != nil && !strings.Contains(err.Error(), expectedErrMsg) {
		t.Errorf("Expected error message '%s', got: %v", expectedErrMsg, err)
	}

	// Test adding an invalid contact
	invalidContact := NewContact("")
	err = contactService.AddContact(invalidContact)
	if err == nil {
		t.Error("Expected error when adding invalid contact, got nil")
	}
	expectedErrMsg = "ID is required"
	if err != nil && !strings.Contains(err.Error(), expectedErrMsg) {
		t.Errorf("Expected error message '%s', got: %v", expectedErrMsg, err)
	}
}

// TestDeleteContact tests the DeleteContact method of the ContactService
func TestDeleteContact(t *testing.T) {
	contactService := NewContactService()
	contact := NewContactFull("1", "first", "last", "1234567890", "address")

	// Setup: Add a contact
	err := contactService.AddContact(contact)
	if err != nil {
		t.Fatalf("Failed to set up test: %v", err)
	}

	// Test deleting an existing contact
	err = contactService.DeleteContact(contact.ID)
	if err != nil {
		t.Errorf("Expected no error when deleting existing contact, got: %v", err)
	}

	// Test deleting a non-existent contact
	err = contactService.DeleteContact(contact.ID)
	if err == nil {
		t.Error("Expected error when deleting non-existent contact, got nil")
	}
	expectedErrMsg := "Contact with ID 1 does not exist"
	if err != nil && !strings.Contains(err.Error(), expectedErrMsg) {
		t.Errorf("Expected error message '%s', got: %v", expectedErrMsg, err)
	}
}

// TestUpdateContact tests the UpdateContact method of the ContactService
func TestUpdateContact(t *testing.T) {
	contactService := NewContactService()
	contact := NewContactFull("1", "first", "last", "1234567890", "address")

	// Setup: Add a contact
	err := contactService.AddContact(contact)
	if err != nil {
		t.Fatalf("Failed to set up test: %v", err)
	}

	// Test updating an existing contact
	err = contactService.UpdateContact(contact.ID, "new first", "new last", "1234567891", "new address")
	if err != nil {
		t.Errorf("Expected no error when updating existing contact, got: %v", err)
	}

	// Test updating a non-existent contact
	err = contactService.DeleteContact("2")
	if err == nil {
		t.Error("Expected error when deleting non-existent contact, got nil")
	}
	expectedErrMsg := "Contact with ID 2 does not exist"
	if err != nil && !strings.Contains(err.Error(), expectedErrMsg) {
		t.Errorf("Expected error message '%s', got: %v", expectedErrMsg, err)
	}

	// Test updating with invalid data
	err = contactService.UpdateContact(contact.ID, "", "", "", "")
	if err == nil {
		t.Error("Expected error when updating with invalid data, got nil")
	}
	expectedErrMsg = "First name is required"
	if err != nil && !strings.Contains(err.Error(), expectedErrMsg) {
		t.Errorf("Expected error message '%s', got: %v", expectedErrMsg, err)
	}
}
