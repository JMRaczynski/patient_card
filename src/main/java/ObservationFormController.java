import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hl7.fhir.r4.model.Observation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;

public class ObservationFormController {

    @FXML public TextField textFieldType;
    @FXML public TextField textFieldValue;
    @FXML public TextField textFieldUnit;
    @FXML public Button buttonSave;
    public Label error;

    private TimeLineUnit observation;
    private UnitController unitController;

    @FXML
    private void initialize(){
        error.setVisible(false);
    }

    public TimeLineUnit getObservation() {
        return observation;
    }

    public void setObservation(TimeLineUnit observation) {
        this.observation = observation;
    }

    public void saveResult(javafx.event.ActionEvent event) {
        String type = textFieldType.getText();
        String value = textFieldValue.getText();
        double valueNumber;
        try {
            valueNumber = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            error.setVisible(true);
            return;
        }
        error.setVisible(false);
        String unit = textFieldUnit.getText();
        observation.setTitle(type);
        observation.setValue(new BigDecimal(valueNumber).setScale(2, RoundingMode.HALF_UP).toString() + " [" + unit + "]");
        observation.getObservation().getValueQuantity().setValue(valueNumber);
        observation.getObservation().getValueQuantity().setUnit(unit);
        observation.getObservation().getCode().setText(type);
        unitController.updateUnitData();
        Main.dbHandler.updateObservation(observation.getObservation());
        Stage stage = (Stage) buttonSave.getScene().getWindow();
        stage.close();
    }

    public void setUnitController(UnitController unitController) {
        this.unitController = unitController;
    }
}
