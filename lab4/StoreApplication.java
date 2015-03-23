// Brad Cardello (bcardell)
// CMPS 180
// lab4

import java.sql.*;
import java.util.*;

/**
 * The class implements methods of the video and bookstore database
 * interface.
 *
 * All methods of the class receive a Connection object through which all
 * communication to the database should be performed. Note: the
 * Connection object should not be closed by any method.
 *
 * Also, no method should throw any exceptions. In particular, in case
 * that an error occurs in the database, then the method should print an
 * error message and call System.exit(-1);
 */
public class StoreApplication {

	/**
	 * Return a list of phone numbers of customers, given a first name and
	 * last name.
	 */
	public List<String> getCustomerPhoneFromFirstLastName(Connection connection,
			String firstName, String lastName) {
		List<String> result = new ArrayList<String>();
      // SQL query to be executed
      String queryString = "SELECT phone " + 
                           "FROM dv_address a, mg_customers c " +
                           "WHERE a.address_id = c.address_id AND " + 
                                 "c.first_name = '" + firstName + "' AND " + 
                                 "c.last_name = '" + lastName + 
                           "';\n";
      try{
         // Used to implement simple SQL statements with no parameters
         Statement statement = connection.createStatement();
         // set of results returned when SQL runs the query
         ResultSet rs = statement.executeQuery(queryString);
         while (rs.next()) { // while result set has next element
            String phoneNum = rs.getString("phone");
            result.add(phoneNum); // put this phone number into the result list
         }
         rs.close();
         statement.close();
      }
      catch (Exception exc) {
         System.err.println("Exception caught.\n " + exc);
         System.exit(-1);
      }
      return result;
	}

	/**
	 * Return list of film titles whose length is is equal to or greater
	 * than the minimum length, and less than or equal to the maximum
	 * length.
	 */
	public List<String> getFilmTitlesBasedOnLengthRange(Connection connection,
			int minLength, int maxLength) {
		List<String> result = new LinkedList<String>();
      // SQL query to be executed
      String queryString = "SELECT title " + 
                           "FROM dv_film " +
                           "WHERE length >= " + minLength + " AND " + 
                                 "length <= " + maxLength +
                           ";\n";
      try{
         // Used to implement simple SQL statements with no parameters
         Statement statement = connection.createStatement();
         // set of results returned when SQL runs the query
         ResultSet rs = statement.executeQuery(queryString);
         while (rs.next()) { // while result set has next element
            String filmTitle = rs.getString("title");
            result.add(filmTitle); // put this film title into the result list
         }
         rs.close();
         statement.close();
      }
      catch (Exception exc) {
         System.err.println("Exception caught.\n " + exc);
         System.exit(-1);
      }
		return result;
	}

	/**
	 * Return the number of customers that live in a given district and
	 * have the given active status.
	 */
	public final int countCustomersInDistrict(Connection connection,
			String districtName, boolean active) {
		int result = -1;
      // SQL query to be executed
      String queryString = "SELECT COUNT (c.customer_id)" + 
                           "FROM mg_customers c, dv_address a " +
                           "WHERE c.address_id = a.address_id AND " + 
                                 "a.district = '" + districtName + "' AND " +
                                 "c.active = " + active +
                           ";\n";
      try{
         // Used to implement simple SQL statements with no parameters
         Statement statement = connection.createStatement();
         // set of results returned when SQL runs the query
         ResultSet rs = statement.executeQuery(queryString);
         while (rs.next()) { // while result set has next element
            result = rs.getInt(1); // result = count
         }
         rs.close();
         statement.close();
      }
      catch (Exception exc) {
         System.err.println("Exception caught.\n " + exc);
         System.exit(-1);
      }
		return result;
	}

	/**
	 * Add a film to the inventory, given its title, description,
	 * length, and rating.
	 *
	 * Your query will need to cast the rating parameter to the
	 * enumerared type mpaa_rating. Whereas an uncast parameter is
	 * simply a question mark, casting would look like ?::mpaa_rating 
	 */
	public void insertFilmIntoInventory(Connection connection, String
			title, String description, int length, String rating)
	{
      PreparedStatement pst = null;
      // SQL statement to be executed
      String str = "INSERT INTO dv_film(title, description, length, rating) " +
                    "VALUES('" + title + "', '" + description + "', " +
                            length + ", ?::mpaa_rating)" + 
                    ";\n";
      try{
         pst = connection.prepareStatement(str);
         // sets rating attribute to be rating argument cast as mpaa_rating
         pst.setString(1, rating);
         // Executes insertion into the relation
         pst.executeUpdate();
         pst.close();
      }
      catch (Exception exc) {
         System.err.println("Exception caught.\n " + exc);
         System.exit(-1);
      }
	}

	/**
	 * Constructor
	 */
	public StoreApplication()
	{}

};
