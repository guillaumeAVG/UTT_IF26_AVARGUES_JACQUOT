package fr.utt.if26_avargues_jacquot.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guillaume.if26_avargues_jacquot.R;

/**
 * Created by guillaume on 27/11/2015.
 */
/* Cette classe définit le fragment du compte connecté.
C'est à dire lorsque le menu tabs est sur: Compte.
Elle hérite de Fragment.*/

public class CompteConnecteFragment extends Fragment {


    /* La méthode onCreateView permet de créer des vues: c'est à dire
      on dit quel fichier XML doit réprésenter l'écran quand on est sur
      la tab Compte. */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //XML fragment_compte_connecte
        View rootView = inflater.inflate(R.layout.fragment_compte_connecte, container, false);
        return rootView;
    }
}