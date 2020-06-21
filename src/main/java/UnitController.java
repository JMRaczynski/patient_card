import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.hl7.fhir.r4.model.Observation;

import java.io.IOException;
import java.time.ZoneId;

public class UnitController extends AnchorPane {

    @FXML private Label title;
    @FXML private Label additionalInfo;
    @FXML private Label timestamp;
    @FXML private Button moreButton;
    @FXML private VBox vboxHover;

    private String filename;
    private String timelineUnitId;
    private String details;
    private String resourceType;
    private MedicationRequest medicationRequest;
    private Observation observation;


    public Label getTitle() {
        return title;
    }

    public void setTitle(Label title) {
        this.title = title;
    }

    public Label getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Label additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Label getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Label timestamp) {
        this.timestamp = timestamp;
    }

    public Button getMoreButton() {
        return moreButton;
    }

    public void setMoreButton(Button moreButton) {
        this.moreButton = moreButton;
    }

    public String  getTimelineUnitId() {
        return timelineUnitId;
    }

    public void setTimelineUnitId(String timelineUnitId) {
        this.timelineUnitId = timelineUnitId;
    }

    public void setDetails(String details){
        this.details = details;
    }

    public void loadMore() {
        if (resourceType.equals("O")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("observationForm.fxml"));
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Edit information");
                ObservationFormController observationFormController = loader.getController();
                observationFormController.setObservation(observation);
                observationFormController.textFieldType.setText(observation.getCode().getText());
                observationFormController.textFieldValue.setText(observation.getValueQuantity().getValue().toString());
                observationFormController.textFieldUnit.setText(observation.getValueQuantity().getUnit());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.show();
        }
        if (resourceType.equals("M")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("medicationRequestForm.fxml"));
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Edit information");
                MedicationFormController medicationFormController = loader.getController();
                medicationFormController.setMedicationRequest(medicationRequest);
                medicationFormController.textFieldMedication.setText(medicationRequest.getMedicationCodeableConcept().getText());
                medicationFormController.datePicker.setValue(medicationRequest.getAuthoredOn().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.show();
        }
    }

    public VBox getVboxHover() {
        return vboxHover;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public void setMedicationRequest(MedicationRequest medicationRequest) {
        this.medicationRequest = medicationRequest;
    }

    public void setObservation(Observation observation) {
        this.observation = observation;
    }
}
