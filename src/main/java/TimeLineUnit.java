public class TimeLineUnit {
    private  int id;
    private String title;
    private String details;
    private String date;

    public TimeLineUnit(int id, String title, String details, String date) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
