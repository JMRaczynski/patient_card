import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hl7.fhir.r4.model.Observation;

public class ObservationFormController {

    @FXML public TextField textFieldType;
    @FXML public TextField textFieldValue;
    @FXML public TextField textFieldUnit;
    @FXML public Button buttonSave;

    private Observation observation;


    public Observation getObservation() {
        return observation;
    }

    public void setObservation(Observation observation) {
        this.observation = observation;
    }

    public void saveResult(javafx.event.ActionEvent event) {
        //TODO: zapis danych
        String type = textFieldType.getText();
        String value = textFieldValue.getText();
        String unit = textFieldUnit.getText();
        Stage stage = (Stage) buttonSave.getScene().getWindow();
        stage.close();
    }
}
