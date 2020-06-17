import javafx.fxml.FXML;
import javafx.scene.chart.Chart;
import javafx.scene.control.Label;

public class GraphController {

    @FXML
    private Label yaxisLabel;
    @FXML
    private Label xaxisLabel;
    @FXML
    private Chart graph;

    public void setYaxisLabel(String yaxisLabel) {
        this.yaxisLabel.setText(yaxisLabel);
    }

    public void setXaxisLabel(String xaxisLabel) {
        this.xaxisLabel.setText(xaxisLabel);
    }
}
