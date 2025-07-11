/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactsassignment1;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Calum
 */
public class ContactsController implements Initializable {

    @FXML private TableView<Contacts> contactsTable;
    @FXML private TableColumn<Contacts, Integer> contactsIDColumn;
    @FXML private TableColumn<Contacts, String> firstNameColumn;
    @FXML private TableColumn<Contacts, String> lastNameColumn;
    @FXML private TableColumn<Contacts, String> addressColumn;
    @FXML private TableColumn<Contacts, String> phoneColumn;
    
    @FXML private Button editContactButton;
    @FXML private Button searchButton;
    
        public void newContactButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("NewContactView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        editContactButton.setDisable(true);
        
        
        contactsIDColumn.setCellValueFactory(new PropertyValueFactory<Contacts, Integer>("contactID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Contacts, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Contacts, String>("lastName"));     
        addressColumn.setCellValueFactory(new PropertyValueFactory<Contacts, String>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Contacts, String>("phoneNumber"));
        
        try{
            loadContacts();
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void loadContacts() throws SQLException
        {
            ObservableList<Contacts> contacts = FXCollections.observableArrayList();
        
            Connection conn = null;
            Statement statement = null;
            ResultSet resultSet = null;
        
            try{
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/contacts?useSSL=false", "root", "");
                statement = conn.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM contacts");
            
                while (resultSet.next())
                {
                    Contacts newContacts = new Contacts(resultSet.getString("contact_firstname"),
                                                        resultSet.getString("contact_lastname"),
                                                        resultSet.getString("contact_address"),
                                                        resultSet.getString("contact_phonenumber"),
                                                        resultSet.getDate("contact_birthday").toLocalDate());
                    newContacts.setContactID(resultSet.getInt("contact_ID"));
                    newContacts.setImageFile(new File(resultSet.getString("imageFile")));
                
                    contacts.add(newContacts);
                }
            
                contactsTable.getItems().addAll(contacts);
            
            } catch (Exception e)
            {
                System.err.println(e);
            }
            finally
            {
                if (conn != null)
                    conn.close();
                if(statement != null)
                    statement.close();
                if(resultSet != null)
                    resultSet.close();
            }       
    }
}   
