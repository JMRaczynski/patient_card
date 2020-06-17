import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hl7.fhir.r4.model.Patient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        timelineList.add(new TimeLineUnit("0", "( ͡° ͜ʖ ͡°)", "baba", new Date(2014, 05, 22, 14, 22, 33), "O"));
        timelineList.add(new TimeLineUnit("1", "( ͡° ͜ʖ ͡°)", "do", new Date(2014, 05, 23, 14, 22, 33), "O"));
        timelineList.add(new TimeLineUnit("2", "( ͡° ͜ʖ ͡°)", "garow", new Date(2014, 05, 24, 14, 22, 33), "O"));
        timelineList.add(new TimeLineUnit("3", "( ͡° ͜ʖ ͡°)", "to", new Date(2014, 05, 25, 14, 22, 33), "O"));
        timelineList.add(new TimeLineUnit("4", "( ͡° ͜ʖ ͡°)", "bedzie", new Date(2014, 05, 26, 14, 22, 33), "O"));
        timelineList.add(new TimeLineUnit("5", "( ͡° ͜ʖ ͡°)", "na githubie", new Date(2014, 05, 27, 14, 22, 33), "O"));
        timelineList.add(new TimeLineUnit("6", "( ͡° ͜ʖ ͡°)", "w commicie", new Date(2014, 05, 28, 14, 22, 33), "O"));
        timelineList.add(new TimeLineUnit("7", "( ͡° ͜ʖ ͡°)", "xD", new Date(3014, 05, 29, 14, 22, 33), "M"));

        VBox vbox = new VBox();
        for (TimeLineUnit timelineUnit: timelineList) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("unit.fxml"));
            try {
                AnchorPane newLoadedPane = loader.load();
                UnitController unitController = loader.getController();
                if (timelineUnit.getResourceType().equals("O")) unitController.getMoreButton().setVisible(false);
                unitController.getTitle().setText(timelineUnit.getTitle());
                unitController.getAdditionalInfo().setText(timelineUnit.getDetails());
                SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss");
                unitController.getTimestamp().setText(format.format(timelineUnit.getDate()));
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
