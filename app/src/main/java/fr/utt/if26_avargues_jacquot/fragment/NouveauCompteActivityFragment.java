package fr.utt.if26_avargues_jacquot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
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
import java.net.MalformedURLException;

import fr.utt.if26_avargues_jacquot.activity.MainActivity;
import fr.utt.if26_avargues_jacquot.services.SignInService;

/**
 * Created by guillaume on 11/12/2015.
 */
/* Cette classe définit le fragment lorsqu'on créé un nouveau comptre.
Elle hérite de Fragment et implémente View.OnClickListener car cet écran possède des éléments qui sont cliquables.*/

public class NouveauCompteActivityFragment extends Fragment implements View.OnClickListener {

    EditText nom;
    EditText prenom;
    EditText dateNaissance;
    EditText adresseEmail;
    RadioGroup choixEcole;
    EditText numeroTelephone;
    EditText motDePasse;
    EditText motDePasseConfirm;


    /* La méthode onClick permet de définir une action dès que l'utilisateur clique sur le bouton valider ou annuler ou ajouter une image de profil.*/
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BT_valider:
                Boolean boolAddCompte = false;
                try {
                    boolAddCompte = addCompte();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(boolAddCompte) {
                    Toast.makeText(getActivity().getApplicationContext(), "Le compte a été ajouté. Vous pouvez vous connecter", Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent2);
                }
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

        nom = (EditText) view.findViewById(R.id.TF_nouveauCompteNom);
        prenom = (EditText) view.findViewById(R.id.TF_nouveauComptePrenom);
        dateNaissance = (EditText) view.findViewById(R.id.TF_nouveauCompteDateNaissance);
        adresseEmail = (EditText) view.findViewById(R.id.TF_nouveauCompteEmail);
        choixEcole = (RadioGroup) view.findViewById(R.id.RG_NouveauCompteEcole);
        numeroTelephone = (EditText) view.findViewById(R.id.TF_nouveauCompteTelephone);
        motDePasse = (EditText) view.findViewById(R.id.TF_nouveauCompteMotdePasse);
        motDePasseConfirm = (EditText) view.findViewById(R.id.TF_nouveauCompteMotdePasseConfirm);

        return view;
    }

    private boolean addCompte() throws IOException, JSONException {

        String StringNom = nom.getText().toString();
        String StringPrenom = prenom.getText().toString();
        String StringDateNaissance = dateNaissance.getText().toString();
        String StringAdresseEmail = adresseEmail.getText().toString();
        String StringEcole = getChoixEcole();
        String StringTelephone = numeroTelephone.getText().toString();
        Integer IntTelephone;
        if(StringTelephone.length() == 0) {IntTelephone = 0;}
        else { IntTelephone = Integer.valueOf(StringTelephone); }

        String StringMotDePasse = motDePasse.getText().toString();

        if(StringPrenom.length() == 0 || StringNom.length() == 0 || StringDateNaissance.length() == 0 || StringAdresseEmail.length() == 0 || StringEcole.length() == 0 || StringMotDePasse.length() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), "Veuillez compléter tous les champs", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!StringMotDePasse.equals(motDePasseConfirm.getText().toString())) {
            Toast.makeText(getActivity().getApplicationContext(), "Les mots de passe ne correspondent pas", Toast.LENGTH_LONG).show();
            return false;
        }
        if(IntTelephone.toString().length() != 10 && IntTelephone != 0 ) {
            Toast.makeText(getActivity().getApplicationContext(), "Erreur de numéro de téléphone", Toast.LENGTH_LONG).show();
            return false;
        }

        SignInService sis = new SignInService();
        String addAccountResponse = sis.addAccount(StringNom, StringPrenom, StringDateNaissance, StringAdresseEmail, StringEcole, IntTelephone, StringMotDePasse);

        if(addAccountResponse.equals("Already registered")) {
            Toast.makeText(getActivity().getApplicationContext(), "Ce compte existe déjà", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (addAccountResponse.equals("Success")){
            return true;
        }
        else {
            Toast.makeText(getActivity().getApplicationContext(), "Une erreur s'est produite. Merci de réessayer.", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private String getChoixEcole() {
        String retour = null;
        switch (choixEcole.getCheckedRadioButtonId()) {
            case R.id.RF_UTT:
                retour = "UTT";
                break;
            case R.id.RF_EPF:
                retour = "EPF";
                break;
            case R.id.RF_ESC:
                retour = "ESC";
                break;
            case R.id.RF_IUT:
                retour = "IUT";
                break;
            case R.id.RF_URCA:
                retour = "URCA";
                break;
        }
        return retour;
    }
}
