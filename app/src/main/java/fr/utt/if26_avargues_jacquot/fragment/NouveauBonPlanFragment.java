package fr.utt.if26_avargues_jacquot.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.guillaume.if26_avargues_jacquot.R;

import fr.utt.if26_avargues_jacquot.activity.MainActivity;

/**
 * Created by guillaume on 05/12/2015.
 */
public class NouveauBonPlanFragment extends Fragment implements View.OnClickListener {

    EditText TF_nom;
    EditText TF_description;
    EditText TF_adresse;
    RadioGroup RG_type;
    String type;


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BT_valider:
                Toast.makeText(getActivity().getApplicationContext(), "Apui sur bouton valider.", Toast.LENGTH_LONG).show();
                break;

            case R.id.BT_annuler:
                Intent intent2 = new Intent(getActivity(), MainActivity.class);
                startActivity(intent2);
                break;
        }
    }


    public void getTypeBonPlan() {
        switch (RG_type.getCheckedRadioButtonId()) {
            case R.id.RB_agencesDeTransports:
                type = "Agence de Transport";
                break;
            case R.id.RB_alimentations:
                type= "Alimentation";
                break;
            case R.id.RB_assuranceMaladieEtMutuelles:
                type = "Assurance Maladie et Mutuelles";
                break;
            case R.id.RB_caf:
                type = "CAF";
                break;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nouveau_bon_plan, container, false);
        view.findViewById(R.id.BT_valider).setOnClickListener(this);
        view.findViewById(R.id.BT_annuler).setOnClickListener(this);

        TF_nom = (EditText) view.findViewById(R.id.TF_nomBP);
        TF_adresse = (EditText) view.findViewById(R.id.TF_adresseBP);
        TF_description = (EditText) view.findViewById(R.id.TF_descriptionBP);
        RG_type = (RadioGroup) view.findViewById(R.id.choixTypeBP);

        return view;
    }
}