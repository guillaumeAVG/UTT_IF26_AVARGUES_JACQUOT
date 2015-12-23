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
 * Created by EtienneJ on 16/12/2015.
 * Classe faisant appel au webservice permettant de valider l'existence du token sur le serveur et donc d'authentifier l'utilisateur connecté
 */
public class CheckTokenService {

    /**
     * URL du webservice
     */
    protected final URL urlToRequest;

    /**
     * Constructeur
     * @throws MalformedURLException
     */
    public CheckTokenService() throws MalformedURLException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        this.urlToRequest = new URL("http://51.254.37.59/StudentN3_WS/checkToken.php");
    }

    /**
     * Méthode permettant de valider ou non un token
     * @param token Le token d'authentification
     * @return Boolean
     * @throws IOException
     * @throws JSONException
     */
    public Boolean validateToken(String token) throws IOException, JSONException {

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
                return true;
            default:
                return false;

        }
    }

}
