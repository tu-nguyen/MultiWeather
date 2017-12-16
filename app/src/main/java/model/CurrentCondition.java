package model;

/**
 * Created by valcrune on 12/15/17.
 */

public class CurrentCondition {
    private int time;
    private String description;
    private double temperature;
    private double percipitation;
    private double windSpeed;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPercipitation() {
        return percipitation;
    }

    public void setPercipitation(double percipitation) {
        this.percipitation = percipitation;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
}
