import org.hl7.fhir.r4.model.Medication;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.hl7.fhir.r4.model.Observation;

import java.util.Date;

public class TimeLineUnit {
    private String id;
    private String title;
    private String value;
    private String details;
    private Date date;
    private String resourceType;
    private Observation observation;
    private MedicationRequest medication;

    public TimeLineUnit(String id, String title, String details, String value, Date date, String resourceType) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.value = value;
        this.date = date;
        this.resourceType = resourceType;
    }

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Observation getObservation() {
        return observation;
    }

    public void setObservation(Observation observation) {
        this.observation = observation;
    }

    public MedicationRequest getMedicationRequest() {
        return medication;
    }

    public void setMedicationRequest(MedicationRequest medication) {
        this.medication = medication;
    }
}
