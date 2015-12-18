package fr.utt.if26_avargues_jacquot.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.utt.if26_avargues_jacquot.activity.MainActivity;

/**
 * Created by EtienneJ on 18/12/2015.
 */
public class addBonPlanService {

    protected final URL urlToRequest;
    protected double Longitude;
    protected double Latitude;

    public addBonPlanService() throws MalformedURLException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        this.urlToRequest = new URL("http://51.254.37.59/StudentN3_WS/addBonPlan.php");
    }


    public String addBonPlan(String token, String nom, String adresse, String description, String type, Date dateDebut, Date dateFin) throws IOException, JSONException {

        HttpURLConnection urlConnection = (HttpURLConnection) urlToRequest.openConnection();

        String params = URLEncoder.encode("token", "UTF-8")
                + "=" + URLEncoder.encode(token, "UTF-8");

        params += "&" + URLEncoder.encode("nom", "UTF-8") + "="
                + URLEncoder.encode(nom, "UTF-8");

        params += "&" + URLEncoder.encode("adresse", "UTF-8") + "="
                + URLEncoder.encode(adresse, "UTF-8");

        params += "&" + URLEncoder.encode("description", "UTF-8") + "="
                + URLEncoder.encode(description, "UTF-8");

        params += "&" + URLEncoder.encode("type", "UTF-8") + "="
                + URLEncoder.encode(type, "UTF-8");


        params += "&" + URLEncoder.encode("longitude", "UTF-8") + "="
                + URLEncoder.encode(String.valueOf(Longitude), "UTF-8");

        params += "&" + URLEncoder.encode("latitude", "UTF-8") + "="
                + URLEncoder.encode(String.valueOf(Latitude), "UTF-8");

        params += "&" + URLEncoder.encode("dateDebut", "UTF-8") + "="
                + URLEncoder.encode(dateDebut.toString(), "UTF-8");

        params += "&" + URLEncoder.encode("dateFin", "UTF-8") + "="
                + URLEncoder.encode(dateFin.toString(), "UTF-8");

        urlConnection.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
        wr.write(params);
        wr.flush();

        InputStream is;

        is = urlConnection.getInputStream();
        int ch;
        StringBuffer sb = new StringBuffer();
        while ((ch = is.read()) != -1) {
            sb.append((char) ch);
        }

        String response = sb.toString();

        JSONObject jsonResponse = new JSONObject(response);
        String messageResponse = jsonResponse.getString("message");

        switch (messageResponse) {
            case "No POST":
            case "No token gived":
            case "Invalid IP":
            case "Couldn't connect to mongodb, is the \"mongo\" process running?":
            case "Missing params":
                Log.d("Webservice error", messageResponse);
                return "Internal error";
            case "Invalid token":
            case "Bad date format":
            case "Bad format":
                return messageResponse;
            case "Success":
                return "Success";
            default:
                return "Undefined error";
        }
    }

    public boolean setGeoPoint(String adresse) {
        MainActivity main = new MainActivity();
        Geocoder geoCoder = new Geocoder(main.ma.getApplicationContext(), Locale.getDefault());
        List<Address> address = null;
        try {
            address = geoCoder.getFromLocationName(adresse+", France", 1);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        Latitude = address.get(0).getLatitude();
        Longitude = address.get(0).getLongitude();

        return true;
    }

}
