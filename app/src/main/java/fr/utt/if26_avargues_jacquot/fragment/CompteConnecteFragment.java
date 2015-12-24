package fr.utt.if26_avargues_jacquot.fragment;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guillaume.if26_avargues_jacquot.R;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;

import fr.utt.if26_avargues_jacquot.services.LogoutService;

/**
 * Created by guillaume on 27/11/2015.
 */
/* Cette classe définit le fragment du compte connecté.
C'est à dire lorsque le menu tabs est sur: Compte.
Elle hérite de Fragment.*/

public class CompteConnecteFragment extends Fragment implements View.OnClickListener {


    /* La méthode onCreateView permet de créer des vues: c'est à dire
      on dit quel fichier XML doit réprésenter l'écran quand on est sur
      la tab Compte. */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //XML fragment_compte_connecte
        View rootView = inflater.inflate(R.layout.fragment_compte_connecte, container, false);

        rootView.findViewById(R.id.BT_deconnexion).setOnClickListener(this);
        TextView identite = (TextView) rootView.findViewById(R.id.TX_nomCompte);

        SharedPreferences settings = getContext().getSharedPreferences("StudenN3_storage", 0);
        String nom = settings.getString("nom", "");
        String prenom = settings.getString("prenom", "");
        identite.setText(prenom + " " + nom);

        TextView description = (TextView) rootView.findViewById(R.id.TX_texteDescriptionUtilisateur);
        description.setText(nom + "\r\n" + prenom);
        return rootView;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.BT_deconnexion:
                LogoutService LOS = null;
                try {
                    LOS = new LogoutService();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                assert LOS != null;
                Boolean logoutResult = false;
                try {
                   logoutResult  = LOS.logOut();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(logoutResult) {
                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment ecranConnexion = new CompteLoginFragment();
                    ft.replace(R.id.ecran_compte_connecte, ecranConnexion);
                    ft.commit();
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Erreur à la déconnexion.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}