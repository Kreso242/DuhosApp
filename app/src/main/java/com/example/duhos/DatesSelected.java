package com.example.duhos;

import io.realm.Realm;
import io.realm.RealmObject;

public class DatesSelected extends RealmObject {
    String datum;
    int sati;
    int minute;

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getSati() {
        return sati;
    }

    public void setSati(int sati) {
        this.sati = sati;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return datum;
    }
}
