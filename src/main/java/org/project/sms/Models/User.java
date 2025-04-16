package org.project.sms.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.project.sms.options.AccountType;

public class User {
    private StringProperty user_id;
    private StringProperty email;
    private StringProperty fullName;
    private AccountType role;

    public User(String user_id, String fullName, String email, String name, AccountType role) {
        this.user_id = new SimpleStringProperty(user_id);
        this.fullName = new SimpleStringProperty(fullName);
        this.email = new SimpleStringProperty(email);
        this.role = role;
    }

    public User(String user_id, String fullName, AccountType role) {
        this.user_id = new SimpleStringProperty(user_id);
        this.fullName = new SimpleStringProperty(fullName);
        this.role = role;
    }


    public void setUserId(String user_id) {
        this.user_id.set(user_id);
    }

    public void setFullName(String name) {
        this.fullName.set(name);
    }


    public void setEmail(String email) {
        this.email.set(email);
    }


    public String getUserId() { return user_id.get(); }
    public StringProperty userIDProperty() { return user_id; }

    public String getFullName() { return fullName.get(); }
    public StringProperty FullNameProperty() { return fullName; }


    public String getEmail() { return email.get(); }
    public StringProperty emailProperty() { return email; }

    public AccountType getRole() {
        return role;
    }

}
