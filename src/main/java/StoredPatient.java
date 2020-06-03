import java.util.Date;

public class StoredPatient {
    private String name;
    private String lastname;
    private Date birthdate;

public StoredPatient(String name, String lastname, Date birthdate){
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
