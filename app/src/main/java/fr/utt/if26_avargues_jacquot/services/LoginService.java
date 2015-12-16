package fr.utt.if26_avargues_jacquot.services;

import android.content.Context;
import android.content.SharedPreferences;
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

import fr.utt.if26_avargues_jacquot.activity.MainActivity;

/**
 * Created by EtienneJ on 27/11/2015.
 */
public class LoginService {

    protected final URL urlToRequest;
    private static final char PARAMETER_DELIMITER = '&';
    private static final char PARAMETER_EQUALS_CHAR = '=';

    public LoginService() throws MalformedURLException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        this.urlToRequest = new URL("http://51.254.37.59/StudentN3_WS/login.php");
    }


    public String validateLogin(String user, String passwd) throws IOException, JSONException {

        HttpURLConnection urlConnection = (HttpURLConnection) urlToRequest.openConnection();

        String params = URLEncoder.encode("email", "UTF-8")
                + "=" + URLEncoder.encode(user, "UTF-8");

        params += "&" + URLEncoder.encode("passwd", "UTF-8") + "="
                + URLEncoder.encode(passwd, "UTF-8");

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
            case "Missing arguments":
            case "No valid ipaddress":
                Log.d("Webservice error", messageResponse);
                return "Internal error";
            case "Bad password":
            case "Not match":
            case "No valid email":
                return messageResponse;
            case "Success":
            case "Already registered":
                MainActivity main = new MainActivity();
                SharedPreferences settings = main.ma.getSharedPreferences("StudenN3_storage", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();

                editor.remove("token");
                editor.apply();
                editor.putString("token", jsonResponse.getString("token"));
                editor.apply();

                return "Success";
            default:
                return "Undefined error";
        }
    }

}