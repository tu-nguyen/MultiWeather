package data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Utils.Utils;

public class LocationHttpClient {

    public String getLocationData(double lat, double lon) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        BufferedReader reader = null;
        String end = "&q=" + lat + "," + lon;

        try {
            connection = (HttpURLConnection) (new URL(Utils.ACCU_LOCATION_BASE_URL +  Utils.ACCU_API + end)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            //connection.setDoOutput(true);
            connection.connect();

            StringBuffer stringBuffer = new StringBuffer();
            inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;

            while((line = reader.readLine()) != null) {
                stringBuffer.append(line + "\r\n");
            }

            inputStream.close();
            connection.disconnect();

            return stringBuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}