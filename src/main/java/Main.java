import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.*;

public class Main {

    public static void main(String[] args) {
        FhirContext ctx = FhirContext.forR4();
        String serverBase = "http://localhost:8080/baseR4";

        IGenericClient client = ctx.newRestfulGenericClient(serverBase);

// Perform a search
        Bundle results = client
                .search()
                .forResource(Patient.class)
                .returnBundle(Bundle.class)
                .execute();

        Patient p1 = (Patient) results.getEntry().get(0).getResource();
        HumanName p1name = p1.getName().get(0);
        System.out.println(p1name.getFamily());

        Bundle results2 = client
                .search()
                .forResource(Observation.class)
                .returnBundle(Bundle.class)
                .execute();

        System.out.println(results2.getEntry().size());

        Bundle results3 = client
                .search()
                .forResource(MedicationRequest.class)
                .returnBundle(Bundle.class)
                .execute();

        System.out.println(results3.getEntry().size());
    }
}
