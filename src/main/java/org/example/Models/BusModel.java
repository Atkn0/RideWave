package org.example.Models;

public class BusModel {
    private int id;
    private String busName;
    private String firstStation;
    private String lastStation;
    private String currentStation;
    private int busCrowd;

    // Getter ve setter metotları

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getFirstStation() {
        return firstStation;
    }

    public void setFirstStation(String firstStation) {
        this.firstStation = firstStation;
    }

    public String getLastStation() {
        return lastStation;
    }

    public void setLastStation(String lastStation) {
        this.lastStation = lastStation;
    }

    public String getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(String currentStation) {
        this.currentStation = currentStation;
    }

    public int getBusCrowd() {
        return busCrowd;
    }

    public void setBusCrowd(int busCrowd) {
        this.busCrowd = busCrowd;
    }

    @Override
    public String toString() {
        return "Bus{id=" + id + ", busName='" + busName + '\'' + ", firstStation='" + firstStation + '\'' +
                ", lastStation='" + lastStation + '\'' + ", currentStation='" + currentStation + '\'' +
                ", busCrowd=" + busCrowd + '}';
    }
}