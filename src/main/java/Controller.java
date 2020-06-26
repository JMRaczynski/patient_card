import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hl7.fhir.r4.model.Patient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller {

    @FXML
    private TextField lastnameTextField;
    @FXML
    private TableView patientTableView;
    @FXML
    private TableColumn<StoredPatient, Date> columnDate;

    private PatientTimelineController patientTimelineController;

    public TableView getPatientTable() {
        return patientTableView;
    }

    @FXML
    private void initialize(){

    }

    public void insertPatientTable(ArrayList<StoredPatient> patients){

        patientTableView.setRowFactory(tv -> {
            TableRow<StoredPatient> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    StoredPatient patient = row.getItem();
                    System.out.println("Double click on: "+ patient.getName());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("patientTimeline.fxml"));
                    Stage stage = new Stage();
                    //stage.setAlwaysOnTop(true);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    LinkedList<TimeLineUnit> observationList = Main.dbHandler.getPatientInfo(patient.getId());
                    try {
                        stage.setScene(new Scene((ScrollPane) loader.load()));
                        patientTimelineController = loader.getController();
                        patientTimelineController.setTimelineList(observationList);
                        patientTimelineController.setFilteredTimelineList(observationList);
                        patientTimelineController.getTitle().setText(patient.getName() + " " + patient.getLastname());
                        patientTimelineController.populateWithData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage.setTitle(patient.getName() + " " + patient.getLastname() + " - timeline");
                    stage.show();

                }

            });
            return row;
        });
        columnDate.setCellFactory(column -> {
            TableCell<StoredPatient, Date> cell = new TableCell<StoredPatient, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        ObservableList<StoredPatient> list = FXCollections.observableArrayList(patients);
        FilteredList<StoredPatient> filteredList = new FilteredList<StoredPatient>(list, p->true);
        lastnameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getLastname().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (person.getLastname().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        SortedList<StoredPatient> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(patientTableView.comparatorProperty());
        patientTableView.getItems().clear();
        patientTableView.setItems(sortedData);
    }

}
