package fr.utt.if26_avargues_jacquot.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.guillaume.if26_avargues_jacquot.R;

/**
 * Created by guillaume on 29/11/2015.
 */
/* Cette classe définit le fragment lorsqu'on affiche les détails d'un bon plan.
Elle hérite de Fragment.*/

public class AffichageFragment extends Fragment implements View.OnClickListener {

    /* La méthode onClick permet de définir une action dès que l'utilisateur clique sur une icône.*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IMGB_likeCommentaire1:
                Toast.makeText(getActivity().getApplicationContext(), "Vous aimez le commentaire 1", Toast.LENGTH_LONG).show();
                break;
            case R.id.IMGB_likeCommentaire2:
                Toast.makeText(getActivity().getApplicationContext(), "Vous aimez le commentaire 2", Toast.LENGTH_LONG).show();
                break;
            case R.id.IMGB_likeCommentaire3:
                Toast.makeText(getActivity().getApplicationContext(), "Vous aimez le commentaire 3", Toast.LENGTH_LONG).show();
                break;
            case R.id.IMGB_dislikeCommentaire1:
                Toast.makeText(getActivity().getApplicationContext(), "Vous n'aimez pas le commentaire 1", Toast.LENGTH_LONG).show();
                break;
            case R.id.IMGB_dislikeCommentaire2:
                Toast.makeText(getActivity().getApplicationContext(), "Vous n'aimez pas le commentaire 2", Toast.LENGTH_LONG).show();
                break;
            case R.id.IMGB_dislikeCommentaire3:
                Toast.makeText(getActivity().getApplicationContext(), "Vous n'aimez pas le commentaire 3", Toast.LENGTH_LONG).show();
                break;
        }
    }

    /* La méthode onCreateView permet de créer des vues: c'est à dire
      on dit quel fichier XML doit réprésenter l'écran d'affichage
      des informations d'un bon plan.*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //XML fragment_affichage_bon_plan
        View rootView = inflater.inflate(R.layout.fragment_affichage_bon_plan, container, false);
        rootView.findViewById(R.id.IMGB_likeCommentaire1).setOnClickListener(this);
        rootView.findViewById(R.id.IMGB_likeCommentaire2).setOnClickListener(this);
        rootView.findViewById(R.id.IMGB_likeCommentaire3).setOnClickListener(this);
        rootView.findViewById(R.id.IMGB_dislikeCommentaire1).setOnClickListener(this);
        rootView.findViewById(R.id.IMGB_dislikeCommentaire2).setOnClickListener(this);
        rootView.findViewById(R.id.IMGB_dislikeCommentaire3).setOnClickListener(this);

        return rootView;
    }


}
