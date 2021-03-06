import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
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
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;

public class PatientTimelineController {

    private SimpleDateFormat format;
    private LinkedList<TimeLineUnit> timelineList;
    private LinkedList<TimeLineUnit> filteredTimelineList;
    private Date startDate;
    private Date endDate;
    @FXML private AnchorPane anchorPane;
    @FXML private Label title;
    @FXML private DatePicker pickerStart;
    @FXML private DatePicker pickerEnd;

    @FXML
    private void initialize(){
        startDate = new Date(Long.MIN_VALUE);
        endDate = new Date(Long.MAX_VALUE);
        timelineList = new LinkedList<>();
        filteredTimelineList = new LinkedList<>();
        format = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");

        pickerStart.valueProperty().addListener(((observableValue, oldDate, newDate) -> {
            startDate = Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            filterEvents();
            populateWithData();
        }));

        pickerEnd.valueProperty().addListener(((observableValue, oldDate, newDate) -> {
            endDate = Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            filterEvents();
            populateWithData();
        }));

    }

    private void filterEvents() {
        filteredTimelineList.clear();
        for (TimeLineUnit event: timelineList) {
            if (event.getDate().compareTo(startDate) >= 0 && event.getDate().compareTo(endDate) <= 0) {
                filteredTimelineList.add(event);
            }
        }
    }

    public void populateWithData() {
        if (anchorPane.getChildren().size() > 7) anchorPane.getChildren().remove(7);
        VBox vbox = new VBox();
        for (TimeLineUnit timelineUnit: filteredTimelineList) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("unit.fxml"));
            try {
                AnchorPane newLoadedPane = loader.load();
                UnitController unitController = loader.getController();
                unitController.getTitle().setText(timelineUnit.getTitle());
                unitController.getAdditionalInfo().setText(timelineUnit.getValue());
                unitController.getTimestamp().setText(format.format(timelineUnit.getDate()));
                unitController.setTimeLineUnit(timelineUnit);
                if (timelineUnit.getResourceType().equals("M")) {
                    Tooltip tooltip = createTooltip(timelineUnit);
                    Tooltip.install(unitController.getVboxHover(), tooltip);
                    unitController.setTooltip(tooltip);
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

    public void setFilteredTimelineList(LinkedList<TimeLineUnit> timelineList) {
        this.filteredTimelineList = (LinkedList<TimeLineUnit>) timelineList.clone();
    }

    public void openGraph(javafx.event.ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graph.fxml"));
        Stage stage = new Stage();
        Button source = ((Button) event.getSource());
        String type = source.getText();

        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            stage.setScene(new Scene((AnchorPane) loader.load()));
            GraphController graphController = loader.getController();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            XYChart.Series<String, Double> series = new XYChart.Series();
            if (type.equals("BMI")){
                series.setName("BMI");
                graphController.graph.getYAxis().setLabel("BMI [kg/m^2]");
                getDataForChart("Body Mass Index", series);
                stage.setTitle("BMI over time");
            } else if (type.equals("Weight")) {
                series.setName("Body weight");
                graphController.graph.getYAxis().setLabel("Weight [kg]");
                stage.setTitle("Body weight over time");
                getDataForChart("Body Weight", series);
            } else {
                series.setName("Temperature");
                graphController.graph.getYAxis().setLabel("Temperature [°C]");
                stage.setTitle("Temperature over time");
                getDataForChart("Oral temperature", series);
            }
            graphController.graph.getData().add(series);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.show();
    }

    private void getDataForChart(String dataType, XYChart.Series<String, Double> series) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        for (int i = filteredTimelineList.size() - 1; i >= 0; i--) {
            if (filteredTimelineList.get(i).getTitle().equals(dataType)) {
                series.getData()
                        .add(new XYChart.Data<String, Double>(dateFormat.format(filteredTimelineList.get(i).getDate()),
                                Double.parseDouble(filteredTimelineList.get(i).getValue().split(" ")[0])));
            }
        }
    }

    public Label getTitle() {
        return title;
    }
}

