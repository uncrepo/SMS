package org.project.sms.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Calendar {
    private StringProperty calendar;


    public Calendar(String grade) {
        this.calendar = new SimpleStringProperty(grade);
    }

    public void setCalendar(String calendar) {
        this.calendar.set(calendar);
    }


    public String getCalendar() { return calendar.get(); }
    public StringProperty calendarProperty() { return calendar; }

}
