package contact

// Contact represents a person's contact information
type Contact struct {
	// ID is the unique identifier of the Contact
	ID string `json:"id"`

	// FirstName is the first name of the Contact
	FirstName string `json:"firstName"`

	// LastName is the last name of the Contact
	LastName string `json:"lastName"`

	// Phone is the phone number of the Contact
	Phone string `json:"phone"`

	// Address is the address of the Contact
	Address string `json:"address"`
}

// NewContact creates a new Contact with the specified unique identifier
func NewContact(id string) *Contact {
	return &Contact{
		ID: id,
	}
}

// NewContactFull creates a new Contact with all the specified parameters
func NewContactFull(id, firstName, lastName, phone, address string) *Contact {
	return &Contact{
		ID:        id,
		FirstName: firstName,
		LastName:  lastName,
		Phone:     phone,
		Address:   address,
	}
}

//// GetID returns the unique identifier of the contact
//func (c *Contact) GetID() string {
//	return c.ID
//}
//
//// GetFirstName retrieves the first name of the contact
//func (c *Contact) GetFirstName() string {
//	return c.FirstName
//}
//
//// SetFirstName sets the first name of the contact
//func (c *Contact) SetFirstName(firstName string) {
//	c.FirstName = firstName
//}
//
//// GetLastName retrieves the last name of the contact
//func (c *Contact) GetLastName() string {
//	return c.LastName
//}
//
//// SetLastName sets the last name of the contact
//func (c *Contact) SetLastName(lastName string) {
//	c.LastName = lastName
//}
//
//// GetPhone retrieves the phone number of the contact
//func (c *Contact) GetPhone() string {
//	return c.Phone
//}
//
//// SetPhone sets the phone number of the contact
//func (c *Contact) SetPhone(phone string) {
//	c.Phone = phone
//}
//
//// GetAddress retrieves the address of the contact
//func (c *Contact) GetAddress() string {
//	return c.Address
//}
//
//// SetAddress sets the address of the contact
//func (c *Contact) SetAddress(address string) {
//	c.Address = address
//}

// Validate checks the fields of the Contact object for validity
// It returns an error message as a string if any validation rule is violated
// Returns nil if all fields are valid
func (c *Contact) Validate() string {
	if c.ID == "" {
		return "ID is required"
	}
	if len(c.ID) > 10 {
		return "ID must be less than 10 characters"
	}
	if c.FirstName == "" {
		return "First name is required"
	}
	if len(c.FirstName) > 10 {
		return "First name must be less than 10 characters"
	}
	if c.LastName == "" {
		return "Last name is required"
	}
	if len(c.LastName) > 10 {
		return "Last name must be less than 10 characters"
	}
	if c.Phone == "" {
		return "Phone number is required"
	}
	if len(c.Phone) != 10 {
		return "Phone number must be 10 digits"
	}
	if c.Address == "" {
		return "Address is required"
	}
	if len(c.Address) > 30 {
		return "Address must be less than 30 characters"
	}
	return ""
}
