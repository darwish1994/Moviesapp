package com.example.darwish.helper;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by darwish on 9/15/2016.
 */
public class ConnectionHelper {
    //get class name
    private final String classTAG = getClass().getName();

    //make instance for internet connection
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    // Will contain the raw JSON response as a string.
    String JsonStr = null;
    public String getJsonString(String...sentValues) {
        //check if the url is empty or not
        if (sentValues == null && !(sentValues[0].length() > 0)) {
            return null;
        }

        try {
            final String apiKey = "api_key";
            //bulid the url
            Uri builtUri = Uri.parse(sentValues[0]).buildUpon()
                    .appendQueryParameter(apiKey, "a12135aa5f06f79599ddbc879dd1ff5d")
                    .build();

            URL url = new URL(builtUri.toString());

            // Create the request to get jason string for moves ,and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            JsonStr = buffer.toString();


        } catch (IOException e) {

            Log.e(classTAG, "Error ", e);
            return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(classTAG, "Error closing stream", e);
                }
            }

        }

return JsonStr;
}
}
