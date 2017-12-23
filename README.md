# MultiWeather - An Android project for CS410

MultiWeather is a weather app mainly focus for an individual who has interests in outdoor activities such as camping, 
hiking, fishing etc. Said individual always need accurate and highly localized forecase to make an informed dicision
relating to their interest. Many different weather apps and services exist, and what this app does is collect relevant 
short-term forecase from these sources. "Temp, chance of rain, and wind speed" Compute and show those data aggregated 
to mean, max and min. 

## Features
This app:

- Collect specific weather information(temp, pop, wind speed) from 4 APIs(AccuWeather, Dark Sky, Weather Underground, and NOAA).
- Features autocomplete provided by Google Place API.
- Display aggregated data from all four providers into Mean, Max and Min for current forecast and hourly up tp 12 hours.
- Compare current forecast from

There are a few features missing from the original proposed plan like comparing hourly side by side,
and major issues like the code could be more organized for readibily plus the app does too much woke on the main thread
but due to the short time given this was the best that could be done.

Only tested on Google

## Screenshots
[<img src="/readme/multiweather_main.png" align="left"
width="200"
    hspace="10" vspace="10">](/readme/multiweather_main.png)
[<img src="/readme/multiweather_compare.png" align="center"
width="200"
    hspace="10" vspace="10">](/readme/multiweather_compare.png)
[<img src="/readme/multiweather_autocomplete.png" align="right"
width="200"
    hspace="10" vspace="10">](/readme/multiweather_autocomplete.png)

## Permissions

- Full Network Access.
- GPS Location 

## License

This application is released under GNU GPLv3 (see [LICENSE](LICENSE)).
Some of the used libraries are released under different licenses.
