package fr.utt.if26_avargues_jacquot.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.guillaume.if26_avargues_jacquot.R;

import fr.utt.if26_avargues_jacquot.webservices.LoginService;

/**
 * Created by guillaume on 26/11/2015.
 */
public class CompteLoginFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.BT_validation:
                    /* TODO
                    * Récupérer les login et password tapés par l'utilisateur dans les champs de texte
                    * */
                    String login = "test";
                    String passwd = "test";
                LoginService loginService = new LoginService();
                if(loginService.validateLogin(login, passwd) == true) {
                    Toast.makeText(getActivity().getApplicationContext(), "Vous avez cliqué sur le bouton Me." , Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Erreur durant le login." , Toast.LENGTH_LONG).show();
                }

                break;

        }
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compte_login, container, false);
        //On récupere la vue souhaitée et on lui affecte le Listener
        view.findViewById(R.id.BT_validation).setOnClickListener(this);
        return view;
    }
}
