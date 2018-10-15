package contactsassignment1;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author jaret_000
 */
public class LaunchContacts extends Application{
    
    public static void main(String[] args)
    {
        launch(args);    
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ContactsTableView.fxml"));
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Contacts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
