package database.Serializers.base;

import org.json.simple.JSONObject;

public abstract class IBaseSerializer {
    abstract JSONObject dump(String str);
    abstract String serialize(JSONObject obj);
}
