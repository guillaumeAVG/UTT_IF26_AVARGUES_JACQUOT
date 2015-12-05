package fr.utt.if26_avargues_jacquot.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.guillaume.if26_avargues_jacquot.R;

import fr.utt.if26_avargues_jacquot.activity.MainActivity;
import fr.utt.if26_avargues_jacquot.activity.NouveauCompteActivity;
import fr.utt.if26_avargues_jacquot.activity.NouveauCompteActivitySuivant;

/**
 * A placeholder fragment containing a simple view.
 */
public class NouveauCompteActivityFragment extends Fragment implements View.OnClickListener{

    public NouveauCompteActivityFragment() {
    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.BT_suivant:
                Intent intent = new Intent(getActivity(), NouveauCompteActivitySuivant.class);
                startActivity(intent);
                break;
            case R.id.BT_annuler:
                Intent intent2 = new Intent(getActivity(), MainActivity.class);
                startActivity(intent2);
                break;
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nouveau_compte, container, false);
        //On récupere la vue souhaitée et on lui affecte le Listener
        view.findViewById(R.id.BT_suivant).setOnClickListener(this);
        view.findViewById(R.id.BT_annuler).setOnClickListener(this);
        return view;
    }
}
