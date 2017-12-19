package Utils;

public class Utils {

    // AccuWeather
    public static final String ACCU_BASE_URL_CURR = "http://dataservice.accuweather.com/currentconditions/v1/";
    public static final String ACCU_BASE_URL24 = "http://dataservice.accuweather.com/forecasts/v1/hourly/24hour/";
    public static final String ACCU_BASE_URL12 = "http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/";
    public static final String ACCU_LOCATION_BASE_URL = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search";
    public static final String ACCU_API = "?apikey=M0oSkwlU0CRh1FLdL2prvLLF3qlhXV7L";

    // DarkSky
    public static final String DARKSKY_BASE_URL = "https://api.darksky.net/forecast/";
    public static final String DARKSKY_API = "63815b97e4c79ae4ecd27fa98748c869/";

    // WU
    public static final String WU_API = "1cb2f464d1feeb55/";
    public static final String WU_BASE_URL_CURR = "http://api.wunderground.com/api/" + WU_API + "forecast/q/";
    public static final String WU_BASE_URL_HR = "http://api.wunderground.com/api/" + WU_API + "hourly/q/";

    // NOAA
    public static final String NOAA_BASE_URL_CURR ="/forecast";
    public static final String NOAA_BASE_URL_HOURLY = "/forecast/hourly";
    public static final String NOAA_BASE_URL = "https://api.weather.gov/points/";
    public static final String NOAA_API = "ZsGlFnYcyWTHOdxOCQmpsNZqfkOTxKvA";


}
