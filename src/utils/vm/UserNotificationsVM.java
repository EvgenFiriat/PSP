package utils.vm;

import org.json.simple.JSONObject;

public class UserNotificationsVM {
    private Long id;
    private String approverName;
    private String requestType;
    private String date;
    private String isApproved;

    public UserNotificationsVM(JSONObject data) {
        this.id = (Long) data.get("id");
        this.approverName = (String) data.get("approverName");
        this.date = (String) data.get("date");
        this.requestType = (String) data.get("requestType");
        this.isApproved = (String) data.get("isApproved");
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String approved) {
        isApproved = approved;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
