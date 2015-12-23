package fr.utt.if26_avargues_jacquot.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guillaume.if26_avargues_jacquot.R;

import org.json.JSONException;

import java.io.IOException;

import fr.utt.if26_avargues_jacquot.activity.NouveauCompteActivity;
import fr.utt.if26_avargues_jacquot.services.CheckTokenService;
import fr.utt.if26_avargues_jacquot.services.LoginService;

/**
 * Created by guillaume on 26/11/2015.
 */
/* Cette classe définit le fragment du compte.
C'est à dire lorsque le menu tabs est sur: Compte.
Elle hérite de Fragment et implémente View.OnClickListener car cet écran possède des éléments qui sont cliquables.*/

public class CompteLoginFragment extends Fragment implements View.OnClickListener {

    // On définit les attributs login et mot de passe pour la connexion
    EditText TF_login;
    EditText TF_passwd;

    /**
     * La méthode onClick permet de définir une action lorsque l'utilisateur clique soit
     sur valider, soit sur je n'ai pas de compte.
     * @param view Vue
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.BT_validation:
                try {
                    onLoginButtonClick(view);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.BT_noCompte:
                onNewAccountClick(view);
                break;

        }
    }

    /**
     * La méthode onLoginButtonClick permet de valider les informations rentrées par l'utilisateur.
     * C'est à dire s'il y a un problème d'authentification, de mot de passe, d'adresse email invalide.
     * @param view Vue
     * @throws IOException
     * @throws JSONException
     */
    public void onLoginButtonClick(View view) throws IOException, JSONException {
        String STRING_login = TF_login.getText().toString();
        String STRING_passwd = TF_passwd.getText().toString();
        LoginService loginService = new LoginService();
        String loginServiceResponse;
        loginServiceResponse = loginService.validateLogin(STRING_login, STRING_passwd);
        switch (loginServiceResponse) {
            case "":
            case "Internal error":
            case "Undefined error":
                Toast.makeText(getActivity().getApplicationContext(), "Error. Please try again", Toast.LENGTH_LONG).show();
                break;
            case "Bad password":
            case "Not match":
            case "No valid email":
                Toast.makeText(getActivity().getApplicationContext(), "Erreur d'identifiant ou de mot de passe", Toast.LENGTH_LONG).show();
                break;
            case "Success":
                SharedPreferences settings = getContext().getSharedPreferences("StudenN3_storage", 0);
                String token = settings.getString("token", "");
                break;
            default:
                Toast.makeText(getActivity().getApplicationContext(), "Erreur inconue", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * La méthode onNewAccountClick est utilisée quand l'utilisateur clique
     * sur le bouton je n'ai pas de compte. Elle permet de créer la nouvelle
     * activité NouveauCompteActivity.
     * @param view Vue
     */
    public void onNewAccountClick(View view) {
        Intent intent = new Intent(getActivity(), NouveauCompteActivity.class);
        startActivity(intent);
    }

    /**
     * La méthode choiceView permet de savoir si l'utilisateur est connecté à partir du toke enregistré sur le téléphone.
     * @return String "connected" ou "login"
     * @throws IOException
     * @throws JSONException
     */
    public String choiceView() throws IOException, JSONException {
        SharedPreferences settings = getContext().getSharedPreferences("StudenN3_storage", 0);
        String token = settings.getString("token", "");

        CheckTokenService cts = new CheckTokenService();
        Boolean checkToken = cts.validateToken(token);

        if (checkToken) {
            return "connected";
        } else {
            return "login";
        }
    }

    /**
     * Permet d'afficher la bonne vue en fonction si l'utilisateur est connecté ou non.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return Vue
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // On créé une variable choice qui permet de voir si l'utilisateur est connecté ou pas
        String choice = null;
        try {
            choice = choiceView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        View view;

        //S'il est connecté, on affiche le XML fragment_compte_connecte
        if (choice == "connected")
            view = inflater.inflate(R.layout.fragment_compte_connecte, container, false);
            //S'il n'est pas connecté on affiche le XML fragment_compte_login
        else {
            view = inflater.inflate(R.layout.fragment_compte_login, container, false);
            //On récupere la vue souhaitée et on lui affecte le Listener
            // On créé des vues pour les boutons valider ou je n'ai pas de compte
            view.findViewById(R.id.BT_validation).setOnClickListener(this);
            view.findViewById(R.id.BT_noCompte).setOnClickListener(this);

            // On récupère les EditText complétés par l'utilisateur
            TF_login = (EditText) view.findViewById(R.id.TF_login);
            TF_passwd = (EditText) view.findViewById(R.id.TF_passwd);
        }

        return view;
    }

}
