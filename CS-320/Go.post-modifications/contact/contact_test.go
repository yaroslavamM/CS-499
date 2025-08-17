package contact

import (
	"testing"
)

func TestContactValidate(t *testing.T) {
	// Test cases table
	tests := []struct {
		name          string
		contact       *Contact
		expectedError string
	}{
		{
			name:          "Valid contact",
			contact:       NewContactFull("1", "first", "last", "1234567890", "address"),
			expectedError: "",
		},
		{
			name:          "ID is required",
			contact:       NewContact(""),
			expectedError: "ID is required",
		},
		{
			name:          "ID cannot be longer than 10 characters",
			contact:       NewContact("1234567890+1"),
			expectedError: "ID must be less than 10 characters",
		},
		{
			name:          "First name is required",
			contact:       NewContactFull("1", "", "", "", ""),
			expectedError: "First name is required",
		},
		{
			name:          "First name cannot be longer than 10 characters",
			contact:       NewContactFull("1", "1234567890+1", "", "", ""),
			expectedError: "First name must be less than 10 characters",
		},
		{
			name:          "Last name is required",
			contact:       NewContactFull("1", "first", "", "", ""),
			expectedError: "Last name is required",
		},
		{
			name:          "Last name cannot be longer than 10 characters",
			contact:       NewContactFull("1", "first", "1234567890+1", "", ""),
			expectedError: "Last name must be less than 10 characters",
		},
		{
			name:          "Phone number is required",
			contact:       NewContactFull("1", "first", "last", "", ""),
			expectedError: "Phone number is required",
		},
		{
			name:          "Phone must be exactly 10 digits",
			contact:       NewContactFull("1", "first", "last", "1234567890+1", ""),
			expectedError: "Phone number must be 10 digits",
		},
		{
			name:          "Address is required",
			contact:       NewContactFull("1", "first", "last", "1234567890", ""),
			expectedError: "Address is required",
		},
		{
			name:          "Address cannot be longer than 30 characters",
			contact:       NewContactFull("1", "first", "last", "1234567890", "123456789012345678901234567890+1"),
			expectedError: "Address must be less than 30 characters",
		},
	}

	// Run all tests
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			result := tt.contact.Validate()
			if result != tt.expectedError {
				t.Errorf("Expected %q, got %q", tt.expectedError, result)
			}
		})
	}
}
