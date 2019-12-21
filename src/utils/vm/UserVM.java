package utils.vm;

import org.json.simple.JSONObject;

public class UserVM {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String position;
    private String level;
    private Boolean isBanned;

    public UserVM(JSONObject dataObj) {
        this.id = (Long) dataObj.get("id");
        this.name = (String) dataObj.get("name");
        this.surname = (String) dataObj.get("surname");
        this.email = (String) dataObj.get("email");
        this.position = (String) dataObj.get("position");
        this.level = (String) dataObj.get("level");
        this.isBanned = (Boolean) dataObj.get("isBanned");
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Boolean getBanned() {
        return isBanned;
    }
}
