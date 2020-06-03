import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.*;

import java.util.ArrayList;
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
                HumanName patientName = patient.getName().get(0);
                String firstName = patientName.getGivenAsSingleString();
                String lastName = patientName.getFamily();
                patientList.add(new StoredPatient(firstName, lastName, patient.getBirthDate()));
            }

            if (bundle.getLink(Bundle.LINK_NEXT) != null) bundle = client.loadPage().next(bundle).execute();
            else bundle = null;
        }
        return patientList;
    }
}
