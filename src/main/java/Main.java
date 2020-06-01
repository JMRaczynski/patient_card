import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hl7.fhir.r4.model.*;

import java.util.ArrayList;
import java.util.Date;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader firstLoader = new FXMLLoader(getClass().getResource("mainView.fxml"));
        Parent root = firstLoader.load();
        Controller controller = firstLoader.getController();
        primaryStage.setTitle("Patient Card");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();

        StoredPatient patient = new StoredPatient("Adam", "Nowak", new DateType("1995-04-01"));
        ArrayList<StoredPatient> lista = new ArrayList<>();
        lista.add(patient);
        controller.insertPatientTable(lista);
    }

    public static void main(String[] args) {
        launch(args);
        /*FhirContext ctx = FhirContext.forR4();
        String serverBase = "http://localhost:8080/baseR4";

        IGenericClient client = ctx.newRestfulGenericClient(serverBase);

// Perform a search
        Bundle results = client
                .search()
                .forResource(Patient.class)
                .returnBundle(Bundle.class)
                .execute();

        Patient p1 = (Patient) results.getEntry().get(0).getResource();
        HumanName p1name = p1.getName().get(0);
        System.out.println(p1name.getFamily());

        Bundle results2 = client
                .search()
                .forResource(Observation.class)
                .returnBundle(Bundle.class)
                .execute();

        System.out.println(results2.getEntry().size());

        Bundle results3 = client
                .search()
                .forResource(MedicationRequest.class)
                .returnBundle(Bundle.class)
                .execute();

        System.out.println(results3.getEntry().size());*/

    }


}
