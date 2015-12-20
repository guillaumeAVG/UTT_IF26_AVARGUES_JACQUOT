package fr.utt.if26_avargues_jacquot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.guillaume.if26_avargues_jacquot.R;

/**
 * Created by guillaume on 20/12/2015.
 */
/* Cette classe définit une autre activité de l'application.
On créé donc l'activité qui permet d'afficher un bon plan*/
public class AffichageBonPlanActivity extends AppCompatActivity {

    /*La méthode onCreate permet de mettre en place l'écran d'affichage d'un bon plan grâce au XML.
    Il y a aussi la mise en place du toolBar*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*On dit que le XML activity_affichage_bon_plan doit être pris en compte pour l'activité d'affichage d'un bon plan*/
        setContentView(R.layout.activity_affichage_bon_plan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
