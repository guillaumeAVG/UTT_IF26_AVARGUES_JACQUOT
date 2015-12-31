package fr.utt.if26_avargues_jacquot.services;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.utt.if26_avargues_jacquot.activity.MainActivity;

/**
 * Created by EtienneJ on 27/12/2015.
 */
public class SignInService {

    /**
     * URL du webservice
     */
    protected final URL urlToRequest;

    /**
     * Constructeur
     *
     * @throws MalformedURLException
     */
    public SignInService() throws MalformedURLException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        this.urlToRequest = new URL("http://51.254.37.59/StudentN3_WS/register.php");
    }

    /**
     * Permet d'ajouter un nouveau compte dans le système.
     * @param nom Nom de l'utilisateur
     * @param prenom Prénom de l'utilisateur
     * @param dateNaissance Date de naissance de l'utilisateur
     * @param email Email de l'utilisateur
     * @param ecole Ecole (UTT, EPF, ...) de l'utilisateur
     * @param telephone Téléphone (optionnel) de l'utilisateur
     * @param motdepasse Mot de passe de l'utilisateur
     * @return Success si tout va bien, sinon un message d'erreur.
     * @throws IOException
     * @throws JSONException
     */
    public String addAccount(String nom, String prenom, String dateNaissance, String email, String ecole, Integer telephone, String motdepasse) throws IOException, JSONException {

        HttpURLConnection urlConnection = (HttpURLConnection) urlToRequest.openConnection();

        String params = URLEncoder.encode("nom", "UTF-8")
                + "=" + URLEncoder.encode(nom, "UTF-8");

        params += "&" + URLEncoder.encode("prenom", "UTF-8") + "="
                + URLEncoder.encode(prenom, "UTF-8");

        params += "&" + URLEncoder.encode("dateNaissance", "UTF-8") + "="
                + URLEncoder.encode(dateNaissance, "UTF-8");

        params += "&" + URLEncoder.encode("email", "UTF-8") + "="
                + URLEncoder.encode(email, "UTF-8");

        params += "&" + URLEncoder.encode("ecole", "UTF-8") + "="
                + URLEncoder.encode(ecole, "UTF-8");

        params += "&" + URLEncoder.encode("telephone", "UTF-8") + "="
                + URLEncoder.encode(telephone.toString(), "UTF-8");

        params += "&" + URLEncoder.encode("motdepasse", "UTF-8") + "="
                + URLEncoder.encode(motdepasse, "UTF-8");



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
            case "Missing params":
            case "Telephone length error":
            case "No valide email":
                Log.d("Webservice error", messageResponse);
                return "Internal error";
            case "Already registered":
                return "Already registered";
            case "Success":
                return "Success";
            default:
                return "Undefined error";
        }
    }
}
