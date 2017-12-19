package model;

/**
 * Created by valcrune on 12/15/17.
 */

public class uPlace {
    private float lon;
    private float lat;
    private String country;
    private String state;
    private String stateSymbol;
    private String city;
    private String zip;
    private String code;

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getStateSymbol() {
        return stateSymbol;
    }

    public void setStateSymbol(String stateSymbol) {
        this.stateSymbol = stateSymbol;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
