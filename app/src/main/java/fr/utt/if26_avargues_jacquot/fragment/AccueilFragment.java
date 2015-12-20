package fr.utt.if26_avargues_jacquot.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.guillaume.if26_avargues_jacquot.R;

/**
 * Created by guillaume on 26/11/2015.
 */
/* Cette classe définit le fragment de l'accueil.
C'est à dire lorsque le menu tabs est sur: Accueil.
Elle hérite de Fragment et implémente View.OnClickListener car cet écran possède des éléments qui sont cliquables:
c'est pour que l'utilisateur puisse voir les différents bons plans détaillés.*/

public class AccueilFragment extends Fragment implements View.OnClickListener {
    /* La méthode onClick permet de définir une action dès que l'utilisateur clique sur une icône.*/
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.IMGB_nouveaute1:
                    /*On ajoute un Toast pour signaler à l'utilisateur qu'il a bien cliqué sur un bon plan*/
                Toast.makeText(getActivity().getApplicationContext(), "Vous avez cliqué sur un bon plan.", Toast.LENGTH_LONG).show();
                break;

        }
    }

    /* La méthode onCreateView permet de créer des vues: c'est à dire
      on dit quel fichier XML doit réprésenter la page d'accueil.
      De plus, on ajoute les éléments qui sont cliquables pour
      permettre d'avoir des intéractions par la suite*/
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accueil, container, false);
        //On récupere la vue souhaitée et on lui affecte le Listener
        view.findViewById(R.id.IMGB_nouveaute1).setOnClickListener(this);
        return view;
    }
}
