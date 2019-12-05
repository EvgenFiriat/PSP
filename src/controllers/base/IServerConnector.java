package controllers.base;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface IServerConnector {
    JSONObject requestServer() throws IOException, ParseException;
    String buildRequestString();
}
