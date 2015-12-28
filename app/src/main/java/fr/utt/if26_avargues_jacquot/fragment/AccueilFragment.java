package fr.utt.if26_avargues_jacquot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guillaume.if26_avargues_jacquot.R;

import org.osmdroid.util.GeoPoint;

import fr.utt.if26_avargues_jacquot.activity.AffichageBonPlanActivity;
import fr.utt.if26_avargues_jacquot.activity.MainActivity;

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
                MainActivity main = new MainActivity();
                //création de notre item
                Intent defineIntent = new Intent(main.ma.getApplicationContext(), AffichageBonPlanActivity.class);
                // objet qui vas nous permettre de passe des variables ici la variable passInfo
                Bundle objetbunble = new Bundle();
                objetbunble .putString("nom", "Memphis coff");
                // on passe notre objet a notre activities
                defineIntent.putExtras(objetbunble);
                defineIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // on appelle notre activité
                main.ma.getApplicationContext().startActivity(defineIntent);
                break;
            case R.id.IMGB_nouveaute2:
                MainActivity main2 = new MainActivity();
                //création de notre item
                Intent defineIntent2 = new Intent(main2.ma.getApplicationContext(), AffichageBonPlanActivity.class);
                // objet qui vas nous permettre de passe des variables ici la variable passInfo
                Bundle objetbunble2 = new Bundle();
                objetbunble2 .putString("nom", "magasin bio");
                // on passe notre objet a notre activities
                defineIntent2.putExtras(objetbunble2);
                defineIntent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // on appelle notre activité
                main2.ma.getApplicationContext().startActivity(defineIntent2);
                break;
            case R.id.IMGB_nouveaute3:
                MainActivity main3 = new MainActivity();
                //création de notre item
                Intent defineIntent3 = new Intent(main3.ma.getApplicationContext(), AffichageBonPlanActivity.class);
                // objet qui vas nous permettre de passe des variables ici la variable passInfo
                Bundle objetbunble3 = new Bundle();
                objetbunble3 .putString("nom", "Stade de l'aube");
                // on passe notre objet a notre activities
                defineIntent3.putExtras(objetbunble3);
                defineIntent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // on appelle notre activité
                main3.ma.getApplicationContext().startActivity(defineIntent3);
                break;
            case R.id.IMGB_plusConsultes1:
                Intent intent4 = new Intent(getActivity(), AffichageBonPlanActivity.class);
                startActivity(intent4);
                break;
            case R.id.IMGB_plusConsultes2:
                Intent intent5 = new Intent(getActivity(), AffichageBonPlanActivity.class);
                startActivity(intent5);
                break;
            case R.id.IMGB_plusConsultes3:
                Intent intent6 = new Intent(getActivity(), AffichageBonPlanActivity.class);
                startActivity(intent6);
                break;
            case R.id.IMGB_plusCommentes1:
                Intent intent7 = new Intent(getActivity(), AffichageBonPlanActivity.class);
                startActivity(intent7);
                break;
            case R.id.IMGB_plusCommentes2:
                Intent intent8 = new Intent(getActivity(), AffichageBonPlanActivity.class);
                startActivity(intent8);
                break;
            case R.id.IMGB_plusCommentes3:
                Intent intent9 = new Intent(getActivity(), AffichageBonPlanActivity.class);
                startActivity(intent9);
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
        view.findViewById(R.id.IMGB_nouveaute2).setOnClickListener(this);
        view.findViewById(R.id.IMGB_nouveaute3).setOnClickListener(this);
        view.findViewById(R.id.IMGB_plusConsultes1).setOnClickListener(this);
        view.findViewById(R.id.IMGB_plusConsultes2).setOnClickListener(this);
        view.findViewById(R.id.IMGB_plusConsultes3).setOnClickListener(this);
        view.findViewById(R.id.IMGB_plusCommentes1).setOnClickListener(this);
        view.findViewById(R.id.IMGB_plusCommentes2).setOnClickListener(this);
        view.findViewById(R.id.IMGB_plusCommentes3).setOnClickListener(this);
        return view;
    }
}
