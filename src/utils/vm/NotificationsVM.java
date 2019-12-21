package utils.vm;

import org.json.simple.JSONObject;

public class NotificationsVM {
    private Long id;
    private String name;
    private String surname;
    private String requestType;
    private String comment;
    private String date;

    public NotificationsVM(JSONObject data) {
        this.id = (Long) data.get("id");
        this.name = (String) data.get("name");
        this.surname = (String) data.get("surname");
        this.requestType = (String) data.get("requestType");
        this.comment = (String) data.get("comment");
        this.date = (String) data.get("date");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
