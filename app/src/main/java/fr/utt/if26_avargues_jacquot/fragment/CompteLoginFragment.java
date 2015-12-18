package fr.utt.if26_avargues_jacquot.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.example.guillaume.if26_avargues_jacquot.R;
import org.json.JSONException;
import java.io.IOException;
import java.net.MalformedURLException;

import fr.utt.if26_avargues_jacquot.activity.NouveauCompteActivity;
import fr.utt.if26_avargues_jacquot.services.CheckTokenService;
import fr.utt.if26_avargues_jacquot.services.LoginService;

/**
 * Created by guillaume on 26/11/2015.
 */
public class CompteLoginFragment extends Fragment implements View.OnClickListener {

    EditText TF_login;
    EditText TF_passwd;

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

    public void onLoginButtonClick(View view) throws IOException, JSONException {
        String STRING_login = TF_login.getText().toString();
        String STRING_passwd = TF_passwd.getText().toString();
        LoginService loginService = new LoginService();
        String loginServiceResponse;
        loginServiceResponse = loginService.validateLogin(STRING_login, STRING_passwd);
        switch(loginServiceResponse) {
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
                SharedPreferences settings = getContext().getSharedPreferences("StudenN3_storage", Context.MODE_PRIVATE);
                String token = settings.getString("token", "");
                break;
            default:
                Toast.makeText(getActivity().getApplicationContext(), "Erreur inconue", Toast.LENGTH_LONG).show();
        }
    }

    public void onNewAccountClick(View view) {
        Intent intent = new Intent(getActivity(), NouveauCompteActivity.class);
        startActivity(intent);
    }

    public String choiceView() throws IOException, JSONException {
        SharedPreferences settings = getContext().getSharedPreferences("StudenN3_storage", Context.MODE_PRIVATE);
        String token = settings.getString("token", "");

        Toast.makeText(getActivity().getApplicationContext(), token, Toast.LENGTH_LONG).show();

        CheckTokenService cts = new CheckTokenService();
        Boolean checkToken = cts.validateToken(token);

        if(checkToken) {
            return "connected";
        }
        else {
            return "login";
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String choice = null;
        try {
            choice = choiceView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        View view;

        if(choice == "connected") view = inflater.inflate(R.layout.fragment_compte_connecte, container, false);
        else {
            view = inflater.inflate(R.layout.fragment_compte_login, container, false);
            //On récupere la vue souhaitée et on lui affecte le Listener
            view.findViewById(R.id.BT_validation).setOnClickListener(this);
            view.findViewById(R.id.BT_noCompte).setOnClickListener(this);

            TF_login = (EditText) view.findViewById(R.id.TF_login);
            TF_passwd = (EditText) view.findViewById(R.id.TF_passwd);
        }

        return view;
    }
}
