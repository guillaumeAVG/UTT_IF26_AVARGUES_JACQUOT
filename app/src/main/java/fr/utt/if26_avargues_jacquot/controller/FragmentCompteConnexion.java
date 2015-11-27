package fr.utt.if26_avargues_jacquot.controller;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.guillaume.if26_avargues_jacquot.R;

import fr.utt.if26_avargues_jacquot.fragment.CompteConnecteFragment;

/**
 * Created by guillaume on 27/11/2015.
 */
public class FragmentCompteConnexion extends Fragment implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.BT_validation:
                    // On met en place le passage entre les deux activités sur ce Listener
                    Intent intent2 = new Intent(this.getActivity(), CompteConnecteFragment.class);
                    startActivity(intent2);
                    Toast.makeText(getActivity().getApplicationContext(), "Vous avez cliqué sur le bouton Me", Toast.LENGTH_SHORT).show();
                    break;
            }
        }


        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_compte_login, container, false);
            //On récupere la vue souhaitée et on lui affecte le Listener
            view.findViewById(R.id.BT_validation).setOnClickListener(this);
            return view;
        }
    }

