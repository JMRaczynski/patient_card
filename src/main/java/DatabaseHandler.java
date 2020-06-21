import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.*;

import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

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
                String id = observation.getIdElement().getIdPart();
                String title = observation.getCode().getText();
                String value;
                try {
                    value = observation.getValueQuantity().getValue().setScale(2, RoundingMode.HALF_UP).toString() + " [" + observation.getValueQuantity().getUnit() + "]";
                    Date date = observation.getIssued();
                    TimeLineUnit timeLineUnit = new TimeLineUnit(id, title, "", value, date, "O");
                    timeLineUnit.setObservation(observation);
                    observationList.add(timeLineUnit);
                } catch (Exception ignored) {

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
                Date date = medication.getAuthoredOn();
                String details = parseDosage(medication);
                TimeLineUnit timeLineUnit = new TimeLineUnit(id, title, details, "", date, "M");
                timeLineUnit.setMedicationRequest(medication);
                observationList.add(timeLineUnit);
            }

            if (bundle.getLink(Bundle.LINK_NEXT) != null) bundle = client.loadPage().next(bundle).execute();
            else bundle = null;
        }

        observationList.sort(Comparator.comparing(TimeLineUnit::getDate).reversed());

        return observationList;
    }

    private String parseDosage(MedicationRequest medication) {
        Dosage dosage = medication.getDosageInstructionFirstRep();
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        StringBuilder text = new StringBuilder(medication.getMedicationCodeableConcept().getText());
        text.append("\nPrescribed by: ")
                .append(medication.getRequester().getDisplay())
                .append("\nAuthored on: ")
                .append(format.format(medication.getAuthoredOn()))
                .append("\n\nDosage instruction:");
        if (dosage.hasAsNeeded()) {
            if (dosage.getAsNeededBooleanType().booleanValue()) {
                text.append("\n  - Administer as needed");
            }
        }
        if (dosage.hasDoseAndRate()) {
            text.append("\n  - Number of units per dose: ")
                    .append(dosage.getDoseAndRateFirstRep().getDoseQuantity().getValue());
        }

        if (dosage.hasMaxDosePerLifetime()) {
            text.append("\n  - Maximal dose per lifetime: ")
                    .append(dosage.getMaxDosePerLifetime().getValue())
                    .append(" ")
                    .append(dosage.getMaxDosePerLifetime().getUnit());
        }
        if (dosage.hasMaxDosePerAdministration()) {
            text.append("\n  - Maximal dose per administration: ")
                    .append(dosage.getMaxDosePerAdministration().getValue())
                    .append(" ")
                    .append(dosage.getMaxDosePerAdministration().getUnit());
        }
        if (dosage.hasMaxDosePerPeriod()) {
            text.append("\n  - Maximal dose per period: ")
                    .append(dosage.getMaxDosePerPeriod().getNumerator().getValue())
                    .append(" ")
                    .append(dosage.getMaxDosePerPeriod().getDenominator().getValue())
                    .append(" ")
                    .append(dosage.getMaxDosePerPeriod().getNumerator().getUnit())
                    .append(dosage.getMaxDosePerPeriod().getDenominator().getUnit());
        }
        if (dosage.hasRoute()) {
            text.append("\n  - Administration route: ")
                    .append(dosage.getRoute().getText());
        }
        if (dosage.hasTiming()) {
            Timing.TimingRepeatComponent repeat = dosage.getTiming().getRepeat();
            text.append("\n  - Administration frequency: ")
                    .append(repeat.getFrequency())
                    .append(" time(s) per ")
                    .append(repeat.getPeriod())
                    .append(" ")
                    .append(repeat.getPeriodUnit().getDisplay())
                    .append("(s)");
        }
        if (dosage.hasMethod()) {
            text.append("\n  - Administration method:")
                    .append(dosage.getMethod().getText());
        }
        if (dosage.hasSite()) {
            text.append("\n  - Administration site:")
                    .append(dosage.getSite().getText());
        }
        if (dosage.getAdditionalInstructionFirstRep().getText() != null) {
            text.append("\n  - Additional instructions: ")
                    .append(dosage.getAdditionalInstructionFirstRep().getText());
        }
        if (text.toString().endsWith("Dosage instruction:")) {
            return text.toString().substring(0, text.toString().lastIndexOf("\n") - 1);
        }
        return text.toString();
    }

    private void updateMedicationRequest(MedicationRequest request) {
        client.update()
                .resource(request)
                .execute();
    }
}
