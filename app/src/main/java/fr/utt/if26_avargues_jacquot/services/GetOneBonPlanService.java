package fr.utt.if26_avargues_jacquot.services;

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

/**
 * Created by EtienneJ on 26/12/2015.
 */
public class GetOneBonPlanService {

    /**
     * URL du webservice
     */
    protected final URL urlToRequest;

    /**
     * Constructeur
     * @throws MalformedURLException
     */
    public GetOneBonPlanService() throws MalformedURLException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        this.urlToRequest = new URL("http://51.254.37.59/StudentN3_WS/getOneBonPlan.php");
    }

    /**
     * Méthode permettant de récupérer le bon plan voulu en fonction de son nom
     * @param nom Titre du bon plan
     * @return La liste des bons plans au format JSON
     * @throws IOException
     * @throws JSONException
     */
    public String getBonPlan(String nom) throws IOException, JSONException {

        HttpURLConnection urlConnection = (HttpURLConnection) urlToRequest.openConnection();

        String params = URLEncoder.encode("nom", "UTF-8")
                + "=" + URLEncoder.encode(nom, "UTF-8");

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

        if(messageResponse != null) {
            return messageResponse;
        }
        else {
            return "error";
        }
    }

}

