package fr.utt.if26_avargues_jacquot.services;

import android.os.StrictMode;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by EtienneJ on 18/12/2015.
 * Class faisant appel au webservice permettant de récupérer tous les webservice en activité à la date du jour.
 */
public class GetBonsPlansService {

    /**
     * URL du webservice
     */
    protected final URL urlToRequest;

    /**
     * Constructeur
     * @throws MalformedURLException
     */
    public GetBonsPlansService() throws MalformedURLException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        this.urlToRequest = new URL("http://51.254.37.59/StudentN3_WS/getBonsPlans.php");
    }

    /**
     * Méthode permettant de récupérer les bons plans actifs à la date du jour
     * @return La liste des bons plans au format JSON
     * @throws IOException
     * @throws JSONException
     */
    public String getCurrentBonsPlans() throws IOException, JSONException {

        HttpURLConnection urlConnection = (HttpURLConnection) urlToRequest.openConnection();

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

        if(messageResponse != null) {
            return messageResponse;
        }
        else {
            return "error";
        }
    }

}
