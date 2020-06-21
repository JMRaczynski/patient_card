import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hl7.fhir.r4.model.MedicationRequest;

public class MedicationFormController {

    @FXML public TextField textFieldMedication;
    @FXML public TextField textFieldName;
    @FXML public Button buttonSave;

    private TimeLineUnit medicationRequest;
    private UnitController unitController;

    public TimeLineUnit getMedicationRequest() {
        return medicationRequest;
    }

    public void setMedicationRequest(TimeLineUnit medicationRequest) {
        this.medicationRequest = medicationRequest;
    }

    public void saveResult(javafx.event.ActionEvent event) {
        //TODO: zapis danych
        String medication = textFieldMedication.getText();
        String name = textFieldName.getText();
        medicationRequest.setTitle(medication);
        String updatedDetails = updateDetails(medication, name);
        medicationRequest.setDetails(updatedDetails);
        medicationRequest.getMedicationRequest().getMedicationCodeableConcept().setText(medication);
        medicationRequest.getMedicationRequest().getRequester().setDisplay(name);
        unitController.updateUnitData();
        Main.dbHandler.updateMedication(medicationRequest.getMedicationRequest());
        Stage stage = (Stage) buttonSave.getScene().getWindow();
        stage.close();
    }

    public void setUnitController(UnitController unitController) {
        this.unitController = unitController;
    }

    private String updateDetails(String newMedication, String newDoctorName) {
        String[] lines = medicationRequest.getDetails().split("\n");
        lines[0] = newMedication;
        lines[1] = lines[1].substring(0, 15) + newDoctorName;
        return String.join("\n", lines);
    }
}


