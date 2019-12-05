package database.Serializers.base;

import org.json.simple.JSONObject;

public interface IBaseSerializer {
    JSONObject dump(String str);
    String serialize(JSONObject obj);
}
