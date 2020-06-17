import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class UnitController extends AnchorPane {

    @FXML private Label title;
    @FXML private Label additionalInfo;
    @FXML private Label timestamp;
    @FXML private Button moreButton;
    @FXML private VBox vboxHover;

    private String filename;
    private String timelineUnitId;
    private String details;

    public Label getTitle() {
        return title;
    }

    public void setTitle(Label title) {
        this.title = title;
    }

    public Label getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Label additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Label getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Label timestamp) {
        this.timestamp = timestamp;
    }

    public Button getMoreButton() {
        return moreButton;
    }

    public void setMoreButton(Button moreButton) {
        this.moreButton = moreButton;
    }

    public String  getTimelineUnitId() {
        return timelineUnitId;
    }

    public void setTimelineUnitId(String timelineUnitId) {
        this.timelineUnitId = timelineUnitId;
    }

    public void setDetails(String details){
        this.details = details;
    }

    public void loadMore() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("patientTimeline.fxml"));
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene((ScrollPane) loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();

    }

    public VBox getVboxHover() {
        return vboxHover;
    }
}
