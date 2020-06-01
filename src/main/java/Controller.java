import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hl7.fhir.r4.model.Patient;

import java.util.*;

public class Controller {

    @FXML
    private TextField lastnameTextField;
    @FXML
    private TableView patientTableView;

    public TableView getPatientTable() {
        return patientTableView;
    }

    @FXML
    private void initialize(){

    }

    public void insertPatientTable(ArrayList<StoredPatient> patients){

        /*TableView<StoredPatient> patientTableView = new TableView();
        TableColumn name = new TableColumn("Imię");
        name.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn lastname = new TableColumn("Nazwisko");
        lastname.setCellValueFactory(new PropertyValueFactory("lastname"));
        TableColumn birthdate = new TableColumn("Data urodzenia");
        lastname.setCellValueFactory(new PropertyValueFactory("birthdate"));
        patientTableView.getColumns().addAll(name, lastname, birthdate);*/
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
