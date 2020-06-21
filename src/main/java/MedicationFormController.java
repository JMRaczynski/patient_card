import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hl7.fhir.r4.model.MedicationRequest;

public class MedicationFormController {

    @FXML public TextField textFieldMedication;
    @FXML public DatePicker datePicker;
    @FXML public Button buttonSave;

    private MedicationRequest medicationRequest;

    public MedicationRequest getMedicationRequest() {
        return medicationRequest;
    }

    public void setMedicationRequest(MedicationRequest medicationRequest) {
        this.medicationRequest = medicationRequest;
    }

    public void saveResult(javafx.event.ActionEvent event) {
        //TODO: zapis danych
        String medication = textFieldMedication.getText();
        Stage stage = (Stage) buttonSave.getScene().getWindow();
        stage.close();
    }
}


