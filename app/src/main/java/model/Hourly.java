package model;

/**
 * Created by valcrune on 12/16/17.
 */

public class Hourly {
    private int time;
    private float temperature;
    private float percipitation;
    private float wind;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getPercipitation() {
        return percipitation;
    }

    public void setPercipitation(float percipitation) {
        this.percipitation = percipitation;
    }

    public float getWind() {
        return wind;
    }

    public void setWind(float wind) {
        this.wind = wind;
    }
}
