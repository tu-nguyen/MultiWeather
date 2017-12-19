package model;

/**
 * Created by valcrune on 12/16/17.
 */

public class Hourly {
    private int time;
    private double temperature;
    private double percipitation;
    private double wind;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }
}
