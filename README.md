# MultiWeather - An Android project for CS410
MultiWeather is a weather app mainly focused for an individual who has interests in outdoor activities such as camping, 
hiking, fishing etc. Said individual always need accurate and highly localized forecast to make an informed decision
relating to their interest. Many different weather apps and services exist, and what this app does is collect relevant 
short-term forecase from these sources. "Temp, chance of rain, and wind speed" Compute and show those data aggregated 
to mean, max and min. 

## Prerequisites 
- Requires API keys from Google Location API in AndroidManifest.xml
- Requires API keys from AccuWeather, Dark Sky, Weather Underground, and NOAA in java/Utils/Utils.java

## Features
This app:

- Collect specific weather information(temp, pop, wind speed) from 4 APIs(AccuWeather, Dark Sky, Weather Underground, and NOAA).
- Features autocomplete provided by Google Place API.
- Display aggregated data from all four providers into Mean, Max and Min for current forecast and hourly up tp 12 hours.
- Compare current forecast from

There are a few features missing from the original proposed plan like comparing hourly side by side,
and issues like the code could be more organized for readability plus the app does too much wake on the main thread
but due to the short time frame, it came out much better than expected.

Only tested on Nexus 5X (Android 7.1.1,API 25) and Google Pixel (8.1.0,API 27).

## Screenshots
[<img src="/readme/multiweather_main.png" align="left"
width="200"
    hspace="5" vspace="10">](/readme/multiweather_main.png)
[<img src="/readme/multiweather_compare.png" align=""
width="200"
    hspace="5" vspace="10">](/readme/multiweather_compare.png)
[<img src="/readme/multiweather_autocomplete.png" align=""
width="200"
    hspace="5" vspace="10">](/readme/multiweather_autocomplete.png)

## Permissions
- Full Network Access.
- GPS Location 

## License
This application is released under GNU GPLv3 (see [LICENSE](LICENSE)).
Some of the used libraries are released under different licenses.
