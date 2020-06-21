import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
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

    private TimeLineUnit timeLineUnit;
    private Tooltip tooltip;

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

    public TimeLineUnit getTimeLineUnit() {
        return timeLineUnit;
    }

    public void setTimeLineUnit(TimeLineUnit timeLineUnit) {
        this.timeLineUnit = timeLineUnit;
    }

    public Tooltip getTooltip() {
        return tooltip;
    }

    public void setTooltip(Tooltip tooltip) {
        this.tooltip = tooltip;
    }

    public void openEditForm() {
        if (timeLineUnit.getResourceType().equals("O")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("observationForm.fxml"));
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Edit information");
                ObservationFormController observationFormController = loader.getController();
                observationFormController.setObservation(timeLineUnit);
                observationFormController.setUnitController(this);
                observationFormController.textFieldType.setText(timeLineUnit.getObservation().getCode().getText());
                observationFormController.textFieldValue.setText(timeLineUnit.getObservation().getValueQuantity().getValue().toString());
                observationFormController.textFieldUnit.setText(timeLineUnit.getObservation().getValueQuantity().getUnit());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.show();
        }
        if (timeLineUnit.getResourceType().equals("M")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("medicationRequestForm.fxml"));
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Edit information");
                MedicationFormController medicationFormController = loader.getController();
                medicationFormController.setMedicationRequest(timeLineUnit);
                medicationFormController.setUnitController(this);
                medicationFormController.textFieldMedication.setText(timeLineUnit.getMedicationRequest().getMedicationCodeableConcept().getText());
                medicationFormController.textFieldName.setText(timeLineUnit.getMedicationRequest().getRequester().getDisplay());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.show();
        }
    }

    public VBox getVboxHover() {
        return vboxHover;
    }

    public void updateUnitData() {
        this.title.setText(timeLineUnit.getTitle());
        this.additionalInfo.setText(timeLineUnit.getValue());
        if (this.tooltip != null) this.tooltip.setText(timeLineUnit.getDetails());
    }
}
