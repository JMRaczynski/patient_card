import org.hl7.fhir.r4.model.DateType;

public class StoredPatient {
    private String name;
    private String lastname;
    private DateType birthdate;

public StoredPatient(String name, String lastname, DateType birthdate){
    this.name = name;
    this.lastname = lastname;
    this.birthdate = birthdate;
}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public DateType getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(DateType birthdate) {
        this.birthdate = birthdate;
    }
}
