package fr.utt.if26_avargues_jacquot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.guillaume.if26_avargues_jacquot.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fr.utt.if26_avargues_jacquot.services.GetOneBonPlanService;

/**
 * Created by guillaume on 20/12/2015.
 */
/* Cette classe définit une autre activité de l'application.
On créé donc l'activité qui permet d'afficher un bon plan*/
public class AffichageBonPlanActivity extends AppCompatActivity {

    String NomBonPlan;
    String bonPlanAuteur;
    String bonPlanDateAjout;
    String bonPlanDateDebut;
    String bonPlanDateFin;
    String bonPlanAdresse;
    String bonPlanDescription;
    String bonPlanType;


    /*La méthode onCreate permet de mettre en place l'écran d'affichage d'un bon plan grâce au XML.
    Il y a aussi la mise en place du toolBar*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*On dit que le XML activity_affichage_bon_plan doit être pris en compte pour l'activité d'affichage d'un bon plan*/
        setContentView(R.layout.activity_affichage_bon_plan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle objetbunble  = this.getIntent().getExtras();
        // récupération de la valeur
        NomBonPlan = objetbunble .getString("nom");

        try {
            getBonPlan();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        remplissageVue();

    }

    private void remplissageVue() {

        TextView titreBonPlan = (TextView) findViewById(R.id.TX_nomBonPlan);
        TextView texteAuteur = (TextView) findViewById(R.id.TX_auteur);
        TextView texteDateAjout = (TextView) findViewById(R.id.TX_descriptionDateAjoutBonPlan);
        TextView texteDateDebut = (TextView) findViewById(R.id.TX_descriptionDateDebutBonPlan);
        TextView texteDateFin = (TextView) findViewById(R.id.TX_descriptionDateFinBonPlan);
        TextView texteDescription = (TextView) findViewById(R.id.TX_texteDescription);
        TextView texteAdresse = (TextView) findViewById(R.id.TX_descriptionDeAdresse);
        TextView texteType= (TextView) findViewById(R.id.TX_descriptionTypeBonPlan);


        titreBonPlan.setText(NomBonPlan);
        texteAuteur.setText("Par: " + bonPlanAuteur);
        texteDescription.setText(bonPlanDescription);
        texteAdresse.setText(bonPlanAdresse);
        texteDateAjout.setText("Ajouté le: " + bonPlanDateAjout);
        texteDateDebut.setText("Du: " + bonPlanDateDebut);
        texteDateFin.setText("Au: " + bonPlanDateFin);
        texteType.setText(bonPlanType);

    }

    private void getBonPlan() throws IOException, JSONException {

        GetOneBonPlanService gobps = new GetOneBonPlanService();
        String JSONbonPlan = gobps.getBonPlan(NomBonPlan);

        JSONArray jsonArrayBonPlan  = new JSONArray(JSONbonPlan);

        //Si on a un bon plan à afficher
        if(jsonArrayBonPlan != null) {
            JSONObject bonPlan = (JSONObject) jsonArrayBonPlan.get(0);
            bonPlanAuteur = bonPlan.getString("auteur");
            bonPlanAdresse = bonPlan.getString("adresse");
            bonPlanDescription = bonPlan.getString("description");
            bonPlanDateAjout = bonPlan.getString("dateCreation");
            bonPlanDateDebut = bonPlan.getString("dateDebut");
            bonPlanDateFin = bonPlan.getString("dateFin");
            bonPlanType = bonPlan.getString("type");

        }

    }

}
