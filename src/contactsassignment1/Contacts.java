package contactsassignment1;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author Calum
 */
public class Contacts {
    private String firstName, lastName, address, phoneNumber;
    private LocalDate birthday;
    private File imageFile;
    private int contactID;

    public Contacts(String firstName, String lastName, String address, String phoneNumber, LocalDate birthday) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        setBirthday(birthday);
        setImageFile(new File("./src/images/defaultPerson.png"));
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber.matches("[2-9]\\d{2}[-.]?\\d{3}[-.]\\d{4}"))
            this.phoneNumber = phoneNumber;
        else
            throw new IllegalArgumentException("Any phone number must be in the pattern of NXX-XXX-XXXX");
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
         int age = Period.between(birthday, LocalDate.now()).getYears();
        
        if (age >= 5 && age <= 100)
            this.birthday = birthday;
        else
            throw new IllegalArgumentException("Contacts must be 5-100 years of age.");
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        if (contactID >= 0)
            this.contactID = contactID;
        else
            throw new IllegalArgumentException("ContactID must be >= 0");
    }
    
     public void insertIntoDB() throws SQLException
    {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        
        try
        {
            //1. Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/contacts?useSSL=false", "root", "root");
            
            //2. Create a String that holds the query with ? as user inputs
            String sql = "INSERT INTO volunteers (firstName, lastName, address, phoneNumber, birthday, imageFile)"
                    + "VALUES (?,?,?,?,?,?)";
                    
            //3. prepare the query
            preparedStatement = conn.prepareStatement(sql);
            
            //4. Convert the birthday into a SQL date
            Date db = Date.valueOf(birthday);
                   
            //5. Bind the values to the parameters
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setDate(5, db);
            preparedStatement.setString(6, imageFile.getName());
            
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            if (preparedStatement != null)
                preparedStatement.close();
            
            if (conn != null)
                conn.close();
        }
    }
    
}
