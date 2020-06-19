import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.ObjectUtils;
import org.hl7.fhir.r4.model.Patient;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javafx.util.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;

public class PatientTimelineController {

    private StoredPatient patient;
    private LinkedList<TimeLineUnit> timelineList;
    @FXML private AnchorPane anchorPane;
    @FXML private Label title;
    @FXML private DatePicker pickerStart;
    @FXML private DatePicker pickerEnd;

    @FXML
    private void initialize(){

    }

    public void populateWithData() {

        timelineList = new LinkedList<>();
        timelineList.add(new TimeLineUnit("0", "( ͡° ͜ʖ ͡°)", "baba", "w commicie", new Date(2014, 05, 22, 14, 22, 33), "O"));
        timelineList.add(new TimeLineUnit("1", "( ͡° ͜ʖ ͡°)", "do","w commicie", new Date(2014, 05, 23, 14, 22, 33), "O"));
        timelineList.add(new TimeLineUnit("2", "( ͡° ͜ʖ ͡°)", "garow","w commicie", new Date(2014, 05, 24, 14, 22, 33), "O"));
        timelineList.add(new TimeLineUnit("3", "( ͡° ͜ʖ ͡°)", "to","w commicie", new Date(2014, 05, 25, 14, 22, 33), "O"));
        timelineList.add(new TimeLineUnit("4", "( ͡° ͜ʖ ͡°)", "bedzie", "w commicie", new Date(2014, 05, 26, 14, 22, 33), "O"));
        timelineList.add(new TimeLineUnit("5", "( ͡° ͜ʖ ͡°)", "na githubie","w commicie", new Date(2014, 05, 27, 14, 22, 33), "O"));
        timelineList.add(new TimeLineUnit("6", "( ͡° ͜ʖ ͡°)", "w commicie", "w commicie", new Date(2014, 05, 28, 14, 22, 33), "O"));
        timelineList.add(new TimeLineUnit("7", "( ͡° ͜ʖ ͡°)", "xD", "xD", new Date(3014, 05, 29, 14, 22, 33), "M"));

        VBox vbox = new VBox();
        for (TimeLineUnit timelineUnit: timelineList) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("unit.fxml"));
            try {
                AnchorPane newLoadedPane = loader.load();
                UnitController unitController = loader.getController();
                unitController.getTitle().setText(timelineUnit.getTitle());
                unitController.getAdditionalInfo().setText(timelineUnit.getValue());
                SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
                unitController.getTimestamp().setText(format.format(timelineUnit.getDate()));
                unitController.setTimelineUnitId(timelineUnit.getId());
                if (timelineUnit.getResourceType().equals("M")) {
                    Tooltip tooltip = createTooltip(timelineUnit);
                    Tooltip.install(unitController.getVboxHover(), tooltip);
                }
                vbox.getChildren().add(newLoadedPane);
                vbox.setLayoutY(50);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        anchorPane.getChildren().add(vbox);
    }

    private Tooltip createTooltip(TimeLineUnit timelineUnit) {
        Tooltip tooltip = new Tooltip(timelineUnit.getDetails());
        tooltip.setStyle("-fx-font-size: 12;");
        tooltip.setShowDelay(Duration.millis(200));
        tooltip.setShowDuration(Duration.minutes(2));
        return tooltip;
    }

    public void setTimelineList(LinkedList<TimeLineUnit> timelineList) {
        this.timelineList = timelineList;
    }


    public void openGraph(javafx.event.ActionEvent event) {
        try{
            LocalDate localDate = pickerStart.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date startDate = Date.from(instant);
        } catch (NullPointerException e) {
            System.out.println("OK");
        }
        try{
            LocalDate localDate = pickerEnd.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date endDate = Date.from(instant);
        } catch (NullPointerException e){
            System.out.println("OK");
        }

        //TODO: ograniczyc zakres danych według pobranej daty (uwzględnić nullvalue


        FXMLLoader loader = new FXMLLoader(getClass().getResource("graph.fxml"));
        Stage stage = new Stage();
        //stage.setAlwaysOnTop(true);
        Button source = ((Button) event.getSource());
        String type = source.getText().toString();
        System.out.println(type);

        //TODO: załadowac wlasciwe dane wzgledem typu
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            stage.setScene(new Scene((AnchorPane) loader.load()));
            GraphController graphController = loader.getController();
            graphController.setXaxisLabel("Time");
            if (type.equals("BMI")){
                graphController.setYaxisLabel("BMI");
            } else if (type.equals("Weight")) {
                graphController.setYaxisLabel("Weight");
            } else {
                graphController.setYaxisLabel("Temperature");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.show();
    }
}
