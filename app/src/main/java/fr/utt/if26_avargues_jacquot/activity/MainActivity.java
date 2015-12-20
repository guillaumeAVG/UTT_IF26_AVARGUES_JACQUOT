package fr.utt.if26_avargues_jacquot.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.guillaume.if26_avargues_jacquot.R;

import java.util.ArrayList;
import java.util.List;

import fr.utt.if26_avargues_jacquot.fragment.AccueilFragment;
import fr.utt.if26_avargues_jacquot.fragment.CarteFragment;
import fr.utt.if26_avargues_jacquot.fragment.CompteLoginFragment;

/**
 * Created by guillaume on 26/11/2015.
 */
/* Cette classe définit l'activité principale de l'application.
On va mettre en place les différentes barres de navigations pour pouvoir changer d'écran dans l'application*/

public class MainActivity extends AppCompatActivity {

    /*Voici les attributs de cette classe*/
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static MainActivity ma;

    /*Voici les différentes méthodes de la classe*/
    /*La méthode onCreate permet de mettre en place l'écran d'accueil grâce au XML.
    Il y a aussi la mise en place du toolBar.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*On dit que le XML activity_main doit être pris en compte pour l'activité principale*/
        setContentView(R.layout.activity_main);

        // On met en place les tabs.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        ma = this;
    }

    /* Méthodes pour mettre en place les tabs. (3 catégories: Accueil, Carte, Compte).
    On appelle les autres fichiers java pour chaque différentes tabs*/
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AccueilFragment(), "Accueil");
        adapter.addFragment(new CarteFragment(), "Carte");
        adapter.addFragment(new CompteLoginFragment(), "Compte");
        viewPager.setAdapter(adapter);
    }

    // Méthodes ViewPagerAdapter pour faire glisser l'écran rapidement.
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        // Méthodes pour obtenir la position.
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        // Méthodes pour obtenir la taille.
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        // Méthode pour ajouter un Fragment
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        //Méthode qui obtient la position du titre
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    /*Méthode qui hausse le menu.
     Elle ajoute des éléments à la barre d'action, si c'est présent. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Méthode qui détermine les Items sélectionnés
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Simplifié si déclaration
        if (id == R.id.action_settings) {
            return true;
        }
        // La barre d'action sera gérée automatiquement les clics sur le Accueil
        return super.onOptionsItemSelected(item);
    }


}