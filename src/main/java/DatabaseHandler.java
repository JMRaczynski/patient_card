import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DatabaseHandler {

    public IGenericClient client;
    private FhirContext context;
    private String baseURL;


    DatabaseHandler(String path) {
        context = FhirContext.forR4();
        baseURL = path;
        client = context.newRestfulGenericClient(baseURL);
    }

    public ArrayList<StoredPatient> getPatientList() {
        ArrayList<StoredPatient> patientList = new ArrayList<>();
        Bundle bundle = client
            .search()
            .forResource(Patient.class)
            .returnBundle(Bundle.class)
            .execute();
        while (bundle != null) {
            List<Bundle.BundleEntryComponent> patients = bundle.getEntry();

            for (int i = 0; i < patients.size(); i++) {
                Patient patient = (Patient) patients.get(i).getResource();
                String id = patient.getIdElement().getIdPart();
                HumanName patientName = patient.getName().get(0);
                String firstName = patientName.getGivenAsSingleString();
                String lastName = patientName.getFamily();
                patientList.add(new StoredPatient(id, firstName, lastName, patient.getBirthDate()));
            }

            if (bundle.getLink(Bundle.LINK_NEXT) != null) bundle = client.loadPage().next(bundle).execute();
            else bundle = null;
        }
        return patientList;
    }

    public LinkedList<TimeLineUnit> getPatientInfo(String patientId) {
        LinkedList<TimeLineUnit> observationList = new LinkedList<>();
        Bundle bundle = client
                .search()
                .forResource(Observation.class)
                .where(Observation.PATIENT.hasId(patientId))
                .returnBundle(Bundle.class)
                .execute();
        while (bundle != null) {
            List<Bundle.BundleEntryComponent> observations = bundle.getEntry();

            for (int i = 0; i < observations.size(); i++) {
                Observation observation = (Observation) observations.get(i).getResource();
                if (!observation.getCode().getText().equals("Tobacco smoking status NHIS") &&
                        !observation.getCode().getText().equals("Blood Pressure")) {
                    String id = observation.getIdElement().getIdPart();
                    String title = observation.getCode().getText();
                    String details;
                    try {
                        details = observation.getValueQuantity().getValue().toString() + " " + observation.getValueQuantity().getUnit();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        details = "XD";
                    }
                    String date = observation.getIssued().toString();
                    observationList.add(new TimeLineUnit(id, title, details, date));
                }
            }

            if (bundle.getLink(Bundle.LINK_NEXT) != null) bundle = client.loadPage().next(bundle).execute();
            else bundle = null;
        }

        bundle = client
                .search()
                .forResource(MedicationRequest.class)
                .where(MedicationRequest.PATIENT.hasId(patientId))
                .returnBundle(Bundle.class)
                .execute();
        while (bundle != null) {
            List<Bundle.BundleEntryComponent> medications = bundle.getEntry();

            for (int i = 0; i < medications.size(); i++) {
                MedicationRequest medication = (MedicationRequest) medications.get(i).getResource();
                String id = medication.getIdElement().getIdPart();
                String title = medication.getMedicationCodeableConcept().getText();
                String date = medication.getAuthoredOn().toString();
                observationList.add(new TimeLineUnit(id, title, "", date));
            }

            if (bundle.getLink(Bundle.LINK_NEXT) != null) bundle = client.loadPage().next(bundle).execute();
            else bundle = null;
        }

        return observationList;
    }
}
