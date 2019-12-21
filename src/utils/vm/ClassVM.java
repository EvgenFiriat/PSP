package utils.vm;

import org.json.simple.JSONObject;


public class ClassVM {
    private String timeString;
    private String classNumber;

    public ClassVM(JSONObject dataObj) {
        this.timeString = (String) dataObj.get("timeString");
        this.classNumber = (String) dataObj.get("classNumber");
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }
}
