import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;

public class PatientTimelineController {

    @FXML private AnchorPane anchorPane;
    @FXML private Label title;

    @FXML
    private void initialize(){
        //TODO: pobieranie daych o wydarzeniach z bazy?
        LinkedList<TimeLineUnit> timelineList = new LinkedList<>();
        timelineList.add(new TimeLineUnit(0, "Tytuł", "Dodatkowe informacje", "14.54 środa, 28.05.2014"));
        timelineList.add(new TimeLineUnit(1, "Tytuł2", "Dodatkowe informacje2", "17.52 środa, 28.05.2014"));
        timelineList.add(new TimeLineUnit(2, "Tytuł2", "Dodatkowe informacje2", "17.52 środa, 28.05.2014"));
        timelineList.add(new TimeLineUnit(3, "Tytuł2", "Dodatkowe informacje2", "17.52 środa, 28.05.2014"));
        timelineList.add(new TimeLineUnit(4, "Tytuł2", "Dodatkowe informacje2", "17.52 środa, 28.05.2014"));
        timelineList.add(new TimeLineUnit(5, "Tytuł2", "Dodatkowe informacje2", "17.52 środa, 28.05.2014"));
        timelineList.add(new TimeLineUnit(6, "Tytuł7", "Dodatkowe informacje2", "17.52 środa, 28.05.2014"));
        timelineList.add(new TimeLineUnit(6, "Tytuł8", "Dodatkowe informacje2", "17.52 środa, 28.05.2014"));


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

}
