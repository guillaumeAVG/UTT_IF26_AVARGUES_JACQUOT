package fr.utt.if26_avargues_jacquot.fragment;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.guillaume.if26_avargues_jacquot.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import fr.utt.if26_avargues_jacquot.activity.NouveauBonPlanActivity;
import fr.utt.if26_avargues_jacquot.services.GetBonsPlansService;
import fr.utt.if26_avargues_jacquot.services.MyItemizedOverlay;


/**
 * Created by guillaume on 26/11/2015.
 */
/* Cette classe définit le fragment de la carte
C'est à dire lorsque le menu tabs est sur: Carte.
Elle hérite de Fragment et implémente View.OnClickListener car cet écran possède des éléments qui sont cliquables:
c'est pour que l'utilisateur puisse ajouter un bon plan.*/

public class CarteFragment extends Fragment implements View.OnClickListener {

    /* La méthode onCreateView permet de créer des vues: c'est à dire
      on dit quel fichier XML doit réprésenter la page pour la carte.
      De plus, on ajoute les éléments qui sont cliquables pour
      permettre d'avoir des intéractions par la suite*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //On définit le XML a utiliser
        View rootView = inflater.inflate(R.layout.fragment_carte, container, false);
        //On définit une vue sur un élément cliquable
        rootView.findViewById(R.id.IMGB_ajouterBonPlan).setOnClickListener(this);
        //On ajoute la vue pour intégrer la carte
        MapView map = (MapView) rootView.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPQUESTOSM);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(14);
        // On met un place directement la vue sur la ville de Troyes
        GeoPoint startPoint = new GeoPoint(48.3, 4.0833);
        mapController.setCenter(startPoint);

        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(getContext(), map);
        mRotationGestureOverlay.setEnabled(true);
        map.setMultiTouchControls(true);
        map.getOverlays().add(mRotationGestureOverlay);

        try {
            putBonBlans(map);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return rootView;
    }

    /*La méthode setUserVisible permet d'afficher un message à l'utilisateur que sa carte est affichée.*/
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {

        }
    }

    /* La méthode onClick permet de définir une action dès que l'utilisateur clique sur l'icône d'ajout de bon plan.*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IMGB_ajouterBonPlan:

                // On met en place le passage entre les deux activités sur ce Listener
                // On passe de l'activité principale à l'activité d'ajout de bon plan.
                Intent intent = new Intent(getActivity(), NouveauBonPlanActivity.class);
                startActivity(intent);
                break;
        }

    }

    public void putBonBlans(MapView map) throws JSONException, IOException, MalformedURLException{

        //On récupère les bons plans depuis le webservice
        GetBonsPlansService gbps = new GetBonsPlansService();

        String bonsPlans = gbps.getCurrentBonsPlans();
        JSONArray jsonArrayBonsPlans  = new JSONArray(bonsPlans);

        if(jsonArrayBonsPlans != null) {
            for (int i=0; i<jsonArrayBonsPlans.length(); i++) {
                JSONObject bonPlan = (JSONObject) jsonArrayBonsPlans.get(i);
                String bonPlanNom = bonPlan.getString("nom");
                String bonPlanType = bonPlan.getString("type");
                Double bonPlanLongitude = bonPlan.getDouble("longitude");
                Double bonPlanLatitude = bonPlan.getDouble("latitude");
                addBPtoMap(map, bonPlanNom, bonPlanType, bonPlanLongitude, bonPlanLatitude);
            }

        }

    }

    public void addBPtoMap(MapView map, String bonPlanNom, String type, Double bonPlanLongitude, Double bonPlanLatitude) {
        // On ajoute une icône sur la carte
        Drawable marker = getDrawable(type);

        if(marker != null) {
            marker.setBounds(-marker.getIntrinsicWidth()/4, -marker.getIntrinsicHeight(), marker.getIntrinsicWidth()/4, 0);

            ResourceProxy resourceProxy = new DefaultResourceProxyImpl(getActivity().getApplicationContext());

            MyItemizedOverlay myItemizedOverlay = new MyItemizedOverlay(marker, resourceProxy);
            map.getOverlays().add(myItemizedOverlay);

            GeoPoint myPoint = new GeoPoint(bonPlanLatitude, bonPlanLongitude);
            myItemizedOverlay.addItem(myPoint, bonPlanNom, bonPlanNom);
        }
    }

    public Drawable getDrawable(String type) {
        Drawable marker = null;
        switch (type) {
            case "Agence de Transport":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.bus);
                break;
            case "Alimentation":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.alimentation);
                break;
            case "Assurance Maladie et Mutuelles":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.assurance_maladie);
                break;
            case "CAF":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.caf_jaune);
                break;
            case "Distributeur de billets":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.distributeurs);
                break;
            case "Emploi":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.emploi);
                break;
            case "Evènement":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.evenement_etudiant);
                break;
            case "Garage":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.garage);
                break;
            case "Hopital":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.hopital);
                break;
            case "Laverie":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.laveries);
                break;
            case "Mairie":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.mairie);
                break;
            case "Maison des étudiants":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.maison_des_etudiants);
                break;
            case "Médecin":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.medecin);
                break;
            case "Pharmacie":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.pharmacie);
                break;
            case "Réduction à court terme":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.court_terme_reduction);
                break;
            case "Réduction à long terme":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.long_terme_reduction);
                break;
            case "Salle de sport":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.salle_de_sport);
                break;
            case "Stade":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.stade);
                break;
            case "Station essence":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.stations_services);
                break;
            case "Terrain":
                marker = ContextCompat.getDrawable(getContext(), R.drawable.terrain);
                break;
            default:
                marker = null;
        }
        return marker;
    }

}
