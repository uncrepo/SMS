package org.project.sms.Models;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Admin {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty userId = new SimpleStringProperty();
    private final StringProperty fullName = new SimpleStringProperty();

    public Admin() {}

    public StringProperty idProperty() { return id; }
    public StringProperty userIdProperty() { return userId; }
    public StringProperty fullNameProperty() { return fullName; }

    public String getId() { return id.get(); }
    public void setId(String id) { this.id.set(id); }

    public String getUserId() { return userId.get(); }
    public void setUserId(String userId) { this.userId.set(userId); }

    public String getFullName() { return fullName.get(); }
    public void setFullName(String fullName) { this.fullName.set(fullName); }
}
