package model;

import java.util.ArrayList;

public class Weather {
    //public uPlace place;
    public CurrentCondition currentCondition = new CurrentCondition();

    //public Time time = new Time();
    //public Temperature temperature = new Temperature();
    //public Percipitation percipitation = new Percipitation();
    //public Wind wind = new Wind();

    public ArrayList<Hourly> hourly = new ArrayList<Hourly>();
}
