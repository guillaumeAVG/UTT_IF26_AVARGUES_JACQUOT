package fr.utt.if26_avargues_jacquot.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.guillaume.if26_avargues_jacquot.R;

import fr.utt.if26_avargues_jacquot.activity.MainActivity;
import fr.utt.if26_avargues_jacquot.activity.NouveauCompteActivitySuivant;

/**
 * Created by guillaume on 05/12/2015.
 */
public class NouveauBonPlanFragment extends Fragment implements View.OnClickListener {


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BT_valider:
                Toast.makeText(getActivity().getApplicationContext(), "Vous avez validé les informations sur le bon plan", Toast.LENGTH_LONG).show();
                break;

            case R.id.BT_annuler:
                Intent intent2 = new Intent(getActivity(), MainActivity.class);
                startActivity(intent2);
                break;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nouveau_bon_plan, container, false);
        view.findViewById(R.id.BT_valider).setOnClickListener(this);
        view.findViewById(R.id.BT_annuler).setOnClickListener(this);
        return view;
    }
}