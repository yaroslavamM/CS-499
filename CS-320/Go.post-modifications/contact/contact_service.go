package contact

import (
	"fmt"
)

// Service ContactService manages a collection of Contact objects
type Service struct {
	// storage is a map that holds Contact objects associated with their unique identifiers
	storage map[string]*Contact
}

// NewContactService creates a new ContactService instance
func NewContactService() *Service {
	return &Service{
		storage: make(map[string]*Contact),
	}
}

// AddContact adds a new contact to the storage.
// Ensures the contact is valid and does not already exist in the storage.
// Returns an error if the contact is invalid or already exists in the storage.
func (cs *Service) AddContact(contact *Contact) error {
	// Make sure the contact's ID is unique
	if _, exists := cs.storage[contact.ID]; exists {
		return fmt.Errorf("contact with ID %s already exists", contact.ID)
	}

	// Make sure the contact is valid
	if validationMsg := contact.Validate(); validationMsg != "" {
		return fmt.Errorf(validationMsg)
	}

	// Store the contact
	cs.storage[contact.ID] = contact
	return nil
}

// DeleteContact deletes a contact from the storage by its unique identifier.
// Returns an error if a contact with the specified ID does not exist.
func (cs *Service) DeleteContact(id string) error {
	// Make sure the contact exists
	if _, exists := cs.storage[id]; !exists {
		return fmt.Errorf("Contact with ID %s does not exist", id)
	}

	// Remove the contact
	delete(cs.storage, id)
	return nil
}

// UpdateContact updates an existing contact in the storage.
// Validates the new contact information and replaces the current contact details if valid.
// Returns an error if the contact does not exist or if the new contact information is invalid.
func (cs *Service) UpdateContact(id, firstName, lastName, phone, address string) error {
	// Make sure the contact exists
	if _, exists := cs.storage[id]; !exists {
		return fmt.Errorf("Contact with ID %s does not exist", id)
	}

	// Make sure the contact update is valid
	contact := NewContactFull(id, firstName, lastName, phone, address)
	if validationMsg := contact.Validate(); validationMsg != "" {
		return fmt.Errorf(validationMsg)
	}

	// Update the Contact
	cs.storage[id] = contact
	return nil
}
