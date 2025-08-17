from pymongo import MongoClient
import os

class AnimalShelter(object):
    """ CRUD operations for Animal collection in MongoDB """

    def __init__(self, username, password):
        # Initializing the MongoClient. This helps to
        # access the MongoDB databases and collections.
        # This is hard-wired to use the aac database, the
        # animals collection, and the aac user.
        # Definitions of the connection string variables are
        # unique to the individual Apporto environment.
        #
        # You must edit the connection variables below to reflect
        # your own instance of MongoDB!
        #
        # Connection Variables
        #
        USER = 'aacuser'
        PASS = 'SNHU1234'
        HOST = os.getenv('MONGO_HOST', 'nv-desktop-services.apporto.com')
        PORT = os.getenv('MONGO_PORT', '31580')
        DB = 'AAC'
        COL = 'animals'
        #
        # Initialize Connection
        #
        self.client = MongoClient('mongodb://%s:%s@%s:%s' % (username,password,HOST,PORT))
        self.database = self.client['%s' % (DB)]
        self.collection = self.database['%s' % (COL)]

    # Complete this create method to implement the C in CRUD.
    def create(self, data):
        """
        Method to implement the C in CRUD.

        Parameters:
        data (dict): A dictionary containing the document to be inserted

        Returns:
        bool: True if the insertion was successful, False otherwise
        """
        if data is not None:
            try:
                self.database.animals.insert_one(data)  # data should be dictionary
                return True
            except Exception as general_error:
                # Handle any other unexpected exceptions
                print(f"Unexpected error during document insertion: {str(general_error)}")
                return False

        else:
            raise Exception("Nothing to save, because data parameter is empty")

    # Create method to implement the R in CRUD.
    def read(self, query):
        """
        Method to implement the R in CRUD.

        Parameters:
        query (dict): A dictionary containing the key/value pairs to search for

        Returns:
        list: List of documents matching the query if successful, otherwise an empty list
        """
        if query is not None:
            cursor = self.collection.find(query)
            result_list = list(cursor)
            return result_list
        else:
            return []

    # Create method to implement the U in CRUD
    def update(self, query, update_data):
        """
        Method to implement the U in CRUD.

        Parameters:
        query (dict): A dictionary containing the key/value pairs to search for documents to update
        update_data (dict): A dictionary containing the update operations to apply
                           (should be in the format required by MongoDB update operations,
                            typically using $set, $push, etc.)

        Returns:
        int: The number of documents modified in the collection
        """
        if query is None or update_data is None:
            raise Exception("Query and update_data parameters cannot be None")

        try:
            # Perform the update operation
            result = self.collection.update_many(query, update_data)
            return result.modified_count
        except Exception as e:
            print(f"Error during update operation: {str(e)}")
            return 0

    # Create method to implement the D in CRUD.
    def delete(self, query):
        """
        Method to implement the D in CRUD.

        Parameters:
        query (dict): A dictionary containing the key/value pairs to search for documents to delete

        Returns:
        int: The number of documents deleted from the collection
        """
        if query is None:
            raise Exception("Query parameter cannot be None")

        try:
            # Perform the delete operation
            result = self.collection.delete_many(query)
            return result.deleted_count
        except Exception as e:
            print(f"Error during delete operation: {str(e)}")
            return 0

