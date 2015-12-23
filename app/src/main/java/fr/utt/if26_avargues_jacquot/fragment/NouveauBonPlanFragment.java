package fr.utt.if26_avargues_jacquot.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.guillaume.if26_avargues_jacquot.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.regex.Pattern;

import fr.utt.if26_avargues_jacquot.activity.MainActivity;
import fr.utt.if26_avargues_jacquot.services.AddBonPlanService;

/**
 * Created by guillaume on 05/12/2015.
 */
/* Cette classe définit le fragment lorsqu'on ajoute un nouveau bon plan.
Elle hérite de Fragment et implémente View.OnClickListener car cet écran possède des éléments qui sont cliquables:
c'est pour que l'utilisateur puisse ajouter un bon plan.*/

public class NouveauBonPlanFragment extends Fragment implements View.OnClickListener {

    //Les attributs de l'ajout d'un bon plan
    EditText TF_nom;
    EditText TF_description;
    EditText TF_adresse;
    EditText TF_dateDebutDeValidite;
    EditText TF_dateFinDeValidite;
    RadioGroup RG_type;
    String type;

    /* La méthode onClick permet de définir une action dès que l'utilisateur clique sur le bouton valider ou annuler.*/
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BT_valider:
                Boolean addAction = false;
                try {
                    addAction = onValiderAction();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(addAction) {
                    Toast.makeText(getActivity().getApplicationContext(), "Merci. Nous avons bien ajouté votre bon plan.", Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent2);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Erreur. Veuillez réessayer", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.BT_annuler:
                Intent intent2 = new Intent(getActivity(), MainActivity.class);
                startActivity(intent2);
                break;
        }
    }

    /* La méthode onValiderAction permet de valider les informations si tous les
    * TextField sont complétés par l'utilisateur. De récupérer les informations et
    * de créer le nouveau bon plan*/
    @TargetApi(Build.VERSION_CODES.M)
    public boolean onValiderAction() throws IOException, JSONException {
        String STRING_nom = TF_nom.getText().toString();
        String STRING_description = TF_description.getText().toString();
        String STRING_adresse = TF_adresse.getText().toString();
        String STRING_dateDebutDeValidite = TF_dateDebutDeValidite.getText().toString();
        String STRING_dateFinDeValidite;
        STRING_dateFinDeValidite = TF_dateFinDeValidite.getText().toString();

        if(STRING_dateDebutDeValidite == null || STRING_dateDebutDeValidite.length() != 10 || STRING_dateDebutDeValidite.contains("-") == false || STRING_dateDebutDeValidite.contains("/") == true || Pattern.compile("/^[a-zA-Z]+$/").matcher(STRING_dateDebutDeValidite).find() == true) {
            Toast.makeText(getActivity().getApplicationContext(), "Erreur. Veuillez vérifier le format de date de début.", Toast.LENGTH_LONG).show();
            return false;
        }
        if(STRING_dateFinDeValidite == null || STRING_dateFinDeValidite == "" || STRING_dateFinDeValidite.length() == 0) {
            STRING_dateFinDeValidite = "";
        }
        else if(STRING_dateFinDeValidite.length() != 10 || STRING_dateFinDeValidite.contains("-") == false || STRING_dateFinDeValidite.contains("/") == true || Pattern.compile("/^[a-zA-Z]+$/").matcher(STRING_dateFinDeValidite).find() == true) {
            Toast.makeText(getActivity().getApplicationContext(), "Erreur. Veuillez vérifier le format de date de fin.", Toast.LENGTH_LONG).show();
            return false;
        }



        MainActivity main = new MainActivity();
        SharedPreferences settings = main.ma.getSharedPreferences("StudenN3_storage", 0);
        String token = settings.getString("token", "");

        if (STRING_adresse != null && STRING_description != null && STRING_nom != null) {
            setTypeBonPlan();
            if (type != "undefined") {
                AddBonPlanService addBonPlanService = new AddBonPlanService();
                String response = addBonPlanService.addBonPlan(token, STRING_nom, STRING_adresse, STRING_description, type, STRING_dateDebutDeValidite, STRING_dateFinDeValidite);
                if (response == "Internal error" || response == "Undefined error") {
                    return false;
                } else if (response == "Address error") {
                    Toast.makeText(getActivity().getApplicationContext(), "Erreur. Veuillez vérifier l'adresse du bon plan.", Toast.LENGTH_LONG).show();
                    return false;
                } else return true;
            } else return false;
        } else return false;
    }


    /**
     * La méthode setTypeBonPlan permet de savoir quel type de nouveau bon plan l'utilisateur
     * veut rajouter, à partir de la liste qu'il a à sa disposition.
     */
    public void setTypeBonPlan() {
        switch (RG_type.getCheckedRadioButtonId()) {
            case R.id.RB_agencesDeTransports:
                type = "Agence de Transport";
                break;
            case R.id.RB_alimentations:
                type = "Alimentation";
                break;
            case R.id.RB_assuranceMaladieEtMutuelles:
                type = "Assurance Maladie et Mutuelles";
                break;
            case R.id.RB_caf:
                type = "CAF";
                break;
            case R.id.RB_distributionsDeBillets:
                type = "Distributeur de billets";
                break;
            case R.id.RB_emploi:
                type = "Emploi";
                break;
            case R.id.RB_evenements:
                type = "Evènement";
                break;
            case R.id.RB_garages:
                type = "Garage";
                break;
            case R.id.RB_hopitaux:
                type = "Hopital";
                break;
            case R.id.RB_laveries:
                type = "Laverie";
                break;
            case R.id.RB_mairies:
                type = "Mairie";
                break;
            case R.id.RB_maisonDesEtudiants:
                type = "Maison des étudiants";
                break;
            case R.id.RB_medecins:
                type = "Médecin";
                break;
            case R.id.RB_pharmacies:
                type = "Pharmacie";
                break;
            case R.id.RB_reductionsACourtTerme:
                type = "Réduction à court terme";
                break;
            case R.id.RB_reductionsALongTerme:
                type = "Réduction à long terme";
                break;
            case R.id.RB_salleDeSport:
                type = "Salle de sport";
                break;
            case R.id.RB_stades:
                type = "Stade";
                break;
            case R.id.RB_stationsEssences:
                type = "Station essence";
                break;
            case R.id.RB_terrains:
                type = "Terrain";
                break;
            default:
                type = "undefined";
                break;
        }
    }


    /* La méthode onCreateView permet de créer des vues: c'est à dire
     on dit quel fichier XML doit réprésenter l'écran d'ajout de bon
     plan.*/
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nouveau_bon_plan, container, false);
        //On récupere la vue souhaitée et on lui affecte le Listener
        // On créé des vues pour les boutons valider ou annuler
        view.findViewById(R.id.BT_valider).setOnClickListener(this);
        view.findViewById(R.id.BT_annuler).setOnClickListener(this);

        // On récupère les EditText complétés par l'utilisateur
        TF_nom = (EditText) view.findViewById(R.id.TF_nomBP);
        TF_adresse = (EditText) view.findViewById(R.id.TF_adresseBP);
        TF_description = (EditText) view.findViewById(R.id.TF_descriptionBP);
        TF_dateDebutDeValidite = (EditText) view.findViewById(R.id.TF_dateDebutDeValiditeBP);
        TF_dateFinDeValidite = (EditText) view.findViewById(R.id.TF_dateFinDeValiditeBP);
        RG_type = (RadioGroup) view.findViewById(R.id.choixTypeBP);

        return view;
    }
}