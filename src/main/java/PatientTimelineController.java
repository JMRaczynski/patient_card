import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hl7.fhir.r4.model.Patient;

import java.io.IOException;
import java.util.LinkedList;

public class PatientTimelineController {

    private StoredPatient patient;
    private LinkedList<TimeLineUnit> timelineList;
    @FXML private AnchorPane anchorPane;
    @FXML private Label title;

    @FXML
    private void initialize(){

    }

    public void populateWithData() {

        timelineList = new LinkedList<>();
        timelineList.add(new TimeLineUnit("0", "( ͡° ͜ʖ ͡°)", "baba", "14.54 poniedzialek, 22.05.2014"));
        timelineList.add(new TimeLineUnit("1", "( ͡° ͜ʖ ͡°)", "do", "17.52 wtorek, 23.05.2014"));
        timelineList.add(new TimeLineUnit("2", "( ͡° ͜ʖ ͡°)", "garow", "17.52 środa, 24.05.2014"));
        timelineList.add(new TimeLineUnit("3", "( ͡° ͜ʖ ͡°)", "to", "17.52 czwartek, 25.05.2014"));
        timelineList.add(new TimeLineUnit("4", "( ͡° ͜ʖ ͡°)", "bedzie", "17.52 piatek, 26.05.2014"));
        timelineList.add(new TimeLineUnit("5", "( ͡° ͜ʖ ͡°)", "na githubie", "17.52 sobota, 27.05.2014"));
        timelineList.add(new TimeLineUnit("6", "( ͡° ͜ʖ ͡°)", "w commicie", "17.52 niedziela, 28.05.2014"));
        timelineList.add(new TimeLineUnit("7", "( ͡° ͜ʖ ͡°)", "xD", "i znowu, tak 24/7"));

        VBox vbox = new VBox();
        for (TimeLineUnit timelineUnit: timelineList) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("unit.fxml"));
            try {
                AnchorPane newLoadedPane = loader.load();
                UnitController unitController = loader.getController();
                unitController.getTitle().setText(timelineUnit.getTitle());
                unitController.getAdditionalInfo().setText(timelineUnit.getDetails());
                unitController.getTimestamp().setText(timelineUnit.getDate());
                unitController.setTimelineUnitId(timelineUnit.getId());
                vbox.getChildren().add(newLoadedPane);
                vbox.setLayoutY(50);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        anchorPane.getChildren().add(vbox);
    }

    public void setTimelineList(LinkedList<TimeLineUnit> timelineList) {
        this.timelineList = timelineList;
    }
}
