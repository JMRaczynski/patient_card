import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hl7.fhir.r4.model.DateType;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Main extends Application {

    public static DatabaseHandler dbHandler;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(new Locale("en", "US"));
        //dbHandler = new DatabaseHandler("http://localhost:8080/baseR4");
        FXMLLoader firstLoader = new FXMLLoader(getClass().getResource("mainView.fxml"));
        Parent root = firstLoader.load();
        Controller controller = firstLoader.getController();
        primaryStage.setTitle("Patient Card");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();

        StoredPatient patient = new StoredPatient("OMEGALUL","Julia", "Tadej", new Date(1995,4,1, 21, 37));
        ArrayList<StoredPatient> lista = new ArrayList<>();
        lista.add(patient);

        //ArrayList<StoredPatient> lista = dbHandler.getPatientList();
        controller.insertPatientTable(lista);
    }

    public static void main(String[] args) {
        launch(args);
    }


}
