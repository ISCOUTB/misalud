package com.prospect.health;

public class DateAlarm {

    public String lunes;
    public String martes;

    public DateAlarm() {
    }

    public DateAlarm(String lunes, String martes) {
        this.lunes = lunes;
        this.martes = martes;
    }

    public String getLunes() {
        return lunes;
    }

    public void setLunes(String lunes) {
        this.lunes = lunes;
    }

    public String getMartes() {
        return martes;
    }

    public void setMartes(String martes) {
        this.martes = martes;
    }
}
