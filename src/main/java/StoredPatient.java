import java.util.Date;

public class StoredPatient {

    private String id;
    private String name;
    private String lastname;
    private Date birthdate;

public StoredPatient(String id, String name, String lastname, Date birthdate){
    this.id = id;
    this.name = name;
    this.lastname = lastname;
    this.birthdate = birthdate;
}

    public String getId() {
        return id;
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
