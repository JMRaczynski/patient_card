import java.util.Date;

public class TimeLineUnit {
    private String id;
    private String title;
    private String details;
    private Date date;
    private String resourceType;

    public TimeLineUnit(String id, String title, String details, Date date, String resourceType) {
        this.id = id;
        this.title = title;
        this.details = details;
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
}
