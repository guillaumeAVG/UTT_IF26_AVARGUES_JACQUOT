package fr.utt.if26_avargues_jacquot.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guillaume.if26_avargues_jacquot.R;

/**
 * Created by guillaume on 29/11/2015.
 */
/* Cette classe définit le fragment lorsqu'on affiche les détails d'un bon plan.
Elle hérite de Fragment.*/

public class FicheFragment extends Fragment {

    /* La méthode onCreateView permet de créer des vues: c'est à dire
      on dit quel fichier XML doit réprésenter l'écran d'affichage
      des informations d'un bon plan.*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //XML fragment_fiche
        View rootView = inflater.inflate(R.layout.fragment_fiche, container, false);

        return rootView;
    }
}
