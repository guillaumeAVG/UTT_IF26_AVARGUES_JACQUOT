package fr.utt.if26_avargues_jacquot.services;

import android.content.SharedPreferences;
import android.os.StrictMode;

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
 * Created by EtienneJ on 24/12/2015.
 */
public class LogoutService {

    /**
     * URL du webservice
     */
    protected final URL urlToRequest;

    public LogoutService() throws MalformedURLException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        this.urlToRequest = new URL("http://51.254.37.59/StudentN3_WS/logout.php");
    }

    public boolean logOut() throws IOException, JSONException {
        MainActivity main = new MainActivity();
        SharedPreferences settings = main.ma.getSharedPreferences("StudenN3_storage", 0);
        String token = settings.getString("token", "");

        HttpURLConnection urlConnection = (HttpURLConnection) urlToRequest.openConnection();

        String params = URLEncoder.encode("token", "UTF-8")
                + "=" + URLEncoder.encode(token, "UTF-8");

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
            case "Success":
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("token");
                editor.remove("nom");
                editor.remove("prenom");
                editor.apply();
                return true;
            default:
                return false;

        }

    }
}
