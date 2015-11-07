package kax.team;

import java.util.Date;

/**
 * Created by Pc on 07/11/2015.
 */
public class HistoryClass {
    private double distance;
    private String duration;
    private double averageSpeed;
    private Date data;

    public HistoryClass(double distance, String duration, double averageSpeed, Date data) {
        this.distance = distance;
        this.duration = duration;
        this.averageSpeed = averageSpeed;
        this.data = data;
    }
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

}
