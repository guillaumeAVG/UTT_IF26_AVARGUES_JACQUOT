package fr.utt.if26_avargues_jacquot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.guillaume.if26_avargues_jacquot.R;

import fr.utt.if26_avargues_jacquot.activity.MainActivity;

/**
 * Created by guillaume on 11/12/2015.
 */
/* Cette classe définit le fragment lorsqu'on créé un nouveau comptre.
Elle hérite de Fragment et implémente View.OnClickListener car cet écran possède des éléments qui sont cliquables.*/

public class NouveauCompteActivityFragment extends Fragment implements View.OnClickListener {

    /* La méthode onClick permet de définir une action dès que l'utilisateur clique sur le bouton valider ou annuler ou ajouter une image de profil.*/
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BT_valider:
                Toast.makeText(getActivity().getApplicationContext(), "Vous avez validé les informations", Toast.LENGTH_LONG).show();
                break;

            case R.id.BT_annuler:
                Intent intent2 = new Intent(getActivity(), MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.BT_ajouterUneImage:
                Intent intentImplicit = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intentImplicit);
                break;
        }
    }

    /* La méthode onCreateView permet de créer des vues: c'est à dire
     on dit quel fichier XML doit réprésenter l'écran de création d'un
     nouveau compte.*/
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nouveau_compte, container, false);
        //On récupere la vue souhaitée et on lui affecte le Listener
        // On créé des vues pour les boutons valider ou annuler ou ajouter une image
        view.findViewById(R.id.BT_valider).setOnClickListener(this);
        view.findViewById(R.id.BT_annuler).setOnClickListener(this);
        view.findViewById(R.id.BT_ajouterUneImage).setOnClickListener(this);
        return view;
    }
}
