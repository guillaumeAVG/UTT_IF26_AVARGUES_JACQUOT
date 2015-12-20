package fr.utt.if26_avargues_jacquot.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guillaume.if26_avargues_jacquot.R;

/**
 * Created by guillaume on 04/12/2015.
 */
/* Cette classe définit le fragment lorsqu'on affiche le filtre de la carte.
Elle hérite de Fragment et de View.OnClickListener.*/

public class FiltreFragment extends Fragment implements View.OnClickListener {

    /* La méthode onCreateView permet de créer des vues: c'est à dire
     on dit quel fichier XML doit réprésenter le filtre des bons plans
     sur la carte.*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //XML fichier fragment_filtres_carte
        View rootView = inflater.inflate(R.layout.fragment_filtres_carte, container, false);
        return rootView;
    }

    /* La méthode onClick permet de définir une action dès que l'utilisateur clique sur un filtre.*/
    @Override
    public void onClick(View v) {

    }
}

