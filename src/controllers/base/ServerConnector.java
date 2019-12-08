package controllers.base;

import client.ClientConnection;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public abstract class ServerConnector {
    protected abstract String buildRequestString();

    protected JSONObject requestServer(String requestString) throws IOException, ParseException {
        ClientConnection.getConnection();
        ClientConnection.getOut().write(requestString);
        ClientConnection.getOut().flush();
        String responseString = ClientConnection.getIn().readLine();
        return  (JSONObject) new JSONParser().parse(responseString);
    }
}
