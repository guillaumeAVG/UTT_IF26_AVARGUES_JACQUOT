package fr.utt.if26_avargues_jacquot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.guillaume.if26_avargues_jacquot.R;

/**
 * Created by guillaume on 09/12/2015.
 */
/* Cette classe définit une autre activité de l'application.
On créé donc l'activité qui permet de créer un nouveau compte*/
public class NouveauCompteActivity extends AppCompatActivity {

    /*La méthode onCreate permet de mettre en place l'écran pour créer un nouveau compte grâce au XML.
     Puis il y a aussi la mise en place du toolBar*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*On dit que le XML activity_nouveau_compte doit être pris en compte pour l'activité de création d'un nouveau compte*/
        setContentView(R.layout.activity_nouveau_compte);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
