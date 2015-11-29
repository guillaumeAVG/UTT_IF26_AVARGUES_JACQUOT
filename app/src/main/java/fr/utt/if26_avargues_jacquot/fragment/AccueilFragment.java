package fr.utt.if26_avargues_jacquot.fragment;

import android.content.Intent;
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
public class AccueilFragment extends Fragment implements View.OnClickListener{
    LayoutInflater inflater;
    ViewGroup container;
    Bundle savedInstanceState;
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.IMGB_nouveaute1:
                    /* TODO
                    * Récupérer les login et password tapés par l'utilisateur dans les champs de texte
                    * */
                Toast.makeText(getActivity().getApplicationContext(), "Vous avez cliqué sur un bon plan.", Toast.LENGTH_LONG).show();
                break;

        }
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accueil, container, false);
        //On récupere la vue souhaitée et on lui affecte le Listener
        view.findViewById(R.id.IMGB_nouveaute1).setOnClickListener(this);
        return view;
    }
}
