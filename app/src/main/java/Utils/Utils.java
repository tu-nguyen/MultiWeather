package Utils;

public class Utils {

    // AccuWeather
    public static final String ACCU_API = "?apikey=ENTER ACCUWEATHER API KEY HERE";
    public static final String ACCU_BASE_URL_CURR = "http://dataservice.accuweather.com/currentconditions/v1/";
    public static final String ACCU_BASE_URL24 = "http://dataservice.accuweather.com/forecasts/v1/hourly/24hour/";
    public static final String ACCU_BASE_URL12 = "http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/";
    public static final String ACCU_LOCATION_GEO_BASE_URL = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search";
    public static final String ACCU_LOCATION_ZIP_BASE_URL = "http://dataservice.accuweather.com/locations/v1/postalcodes/search";
    public static final String ACCU_LOCATION_CITY_BASE_URL = "http://dataservice.accuweather.com/locations/v1/search";




    // DarkSky
    public static final String DARKSKY_API = "ENTER DARK SKY API KEY HERE/";
    public static final String DARKSKY_BASE_URL = "https://api.darksky.net/forecast/";

    // WU
    public static final String WU_API = "ENTER WEATHER UNDERGROUND API KEY HERE/";
    public static final String WU_BASE_URL_CURR = "http://api.wunderground.com/api/" + WU_API + "forecast/q/";
    public static final String WU_BASE_URL_HR = "http://api.wunderground.com/api/" + WU_API + "hourly/q/";

    // NOAA
    public static final String NOAA_API = "ENTER NOAA API KEY HERE"; // Although the info pulls without it?
    public static final String NOAA_BASE_URL_CURR ="/forecast";
    public static final String NOAA_BASE_URL_HOURLY = "/forecast/hourly";
    public static final String NOAA_BASE_URL = "https://api.weather.gov/points/";
}
