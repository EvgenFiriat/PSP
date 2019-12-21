package controllers.admin;

import controllers.base.ServerConnector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.Constants;
import utils.WindowDispatcher;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PIEChartController extends ServerConnector implements Initializable {

    @FXML
    public PieChart chart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            JSONObject response = requestServer(buildRequestString());

            if ((Boolean) response.get("success")) {
                response.remove("success");
                for (Object key: response.keySet()) {
                    PieChart.Data data = new PieChart.Data((String) key, (Long) response.get(key));
                    chart.getData().add(data);
                }
            } else {
                WindowDispatcher.showErrorMessage("Ошибка", (String) response.get("errorMessage"));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String buildRequestString() {
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        request.put("action", Constants.ACTION_INIT_PIE_CHART);
        request.put("data", data);
        return request.toJSONString() + "\n";
    }
}
