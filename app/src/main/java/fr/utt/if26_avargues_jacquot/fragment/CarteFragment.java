package fr.utt.if26_avargues_jacquot.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.guillaume.if26_avargues_jacquot.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapController;
import org.osmdroid.api.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import fr.utt.if26_avargues_jacquot.activity.MainActivity;
import fr.utt.if26_avargues_jacquot.activity.NouveauBonPlanActivity;
import fr.utt.if26_avargues_jacquot.services.CheckTokenService;
import fr.utt.if26_avargues_jacquot.services.GetBonsPlansService;
import fr.utt.if26_avargues_jacquot.services.MyItemizedOverlay;


/**
 * Created by guillaume on 26/11/2015.
 */
/* Cette classe définit le fragment de la carte
C'est à dire lorsque le menu tabs est sur: Carte.
Elle hérite de Fragment et implémente View.OnClickListener car cet écran possède des éléments qui sont cliquables:
c'est pour que l'utilisateur puisse ajouter un bon plan.*/

public class CarteFragment extends Fragment implements View.OnClickListener{

    MapView map;

    /* La méthode onCreateView permet de créer des vues: c'est à dire
      on dit quel fichier XML doit réprésenter la page pour la carte.
      De plus, on ajoute les éléments qui sont cliquables pour
      permettre d'avoir des intéractions par la suite*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //On définit le XML a utiliser
        View rootView = inflater.inflate(R.layout.fragment_carte, container, false);
        //On met en place le listener sur les éléments souhaités
        rootView.findViewById(R.id.IMGB_ajouterBonPlan).setOnClickListener(this);
        rootView.findViewById(R.id.CB_agencesDeTransports).setOnClickListener(this);
        rootView.findViewById(R.id.CB_alimentations).setOnClickListener(this);
        rootView.findViewById(R.id.CB_assuranceMaladieEtMutuelles).setOnClickListener(this);
        rootView.findViewById(R.id.CB_caf).setOnClickListener(this);
        rootView.findViewById(R.id.CB_distributionsDeBillets).setOnClickListener(this);
        rootView.findViewById(R.id.CB_emploi).setOnClickListener(this);
        rootView.findViewById(R.id.CB_evenements).setOnClickListener(this);
        rootView.findViewById(R.id.CB_garages).setOnClickListener(this);
        rootView.findViewById(R.id.CB_hopitaux).setOnClickListener(this);
        rootView.findViewById(R.id.CB_laveries).setOnClickListener(this);
        rootView.findViewById(R.id.CB_mairies).setOnClickListener(this);
        rootView.findViewById(R.id.CB_maisonDesEtudiants).setOnClickListener(this);
        rootView.findViewById(R.id.CB_medecins).setOnClickListener(this);
        rootView.findViewById(R.id.CB_pharmacies).setOnClickListener(this);
        rootView.findViewById(R.id.CB_reductionsACourtTerme).setOnClickListener(this);
        rootView.findViewById(R.id.CB_reductionsALongTerme).setOnClickListener(this);
        rootView.findViewById(R.id.CB_salleDeSport).setOnClickListener(this);
        rootView.findViewById(R.id.CB_stades).setOnClickListener(this);
        rootView.findViewById(R.id.CB_stationsEssences).setOnClickListener(this);
        rootView.findViewById(R.id.CB_terrains).setOnClickListener(this);
        //On défini quel élément représente la carte sur l'interface
        map = (MapView) rootView.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPQUESTOSM);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(13);
        // On met un place directement la vue sur la ville de Troyes
        GeoPoint startPoint = new GeoPoint(48.3, 4.0833);
        mapController.setCenter(startPoint);

        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(getContext(), map);
        mRotationGestureOverlay.setEnabled(true);
        map.setMultiTouchControls(true);
        map.getOverlays().add(mRotationGestureOverlay);

        try {
            putBonsPlans(map);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return rootView;
    }

    /**
     * La méthode onClick permet de définir une action dès que l'utilisateur clique sur un des éléments défini dans la méhode
     * @param v Vue
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IMGB_ajouterBonPlan:
                Boolean connecte = false;
                try {
                   connecte  = this.checkToken();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(!connecte) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    alertDialog.setTitle("Connexion requise");
                    alertDialog.setMessage("Vous devez être connecté pour ajouter un bon plan.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else {
                    // On met en place le passage entre les deux activités sur ce Listener
                    // On passe de l'activité principale à l'activité d'ajout de bon plan.
                    Intent intent = new Intent(getActivity(), NouveauBonPlanActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.CB_agencesDeTransports :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_agencesDeTransports), "Agence de Transport");
                break;
            case R.id.CB_alimentations :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_alimentations), "Alimentation");
                break;
            case R.id.CB_assuranceMaladieEtMutuelles :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_assuranceMaladieEtMutuelles), "Assurance Maladie et Mutuelles");
                break;
            case R.id.CB_caf :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_caf), "CAF");
                break;
            case R.id.CB_distributionsDeBillets :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_distributionsDeBillets), "Distributeur de billets");
                break;
            case R.id.CB_emploi :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_emploi), "Emploi");
                break;
            case R.id.CB_evenements :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_evenements), "Evènement");
                break;
            case R.id.CB_garages :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_garages), "Garage");
                break;
            case R.id.CB_hopitaux :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_hopitaux), "Hopital");
                break;
            case R.id.CB_laveries :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_laveries), "Laverie");
                break;
            case R.id.CB_mairies :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_mairies), "Mairie");
                break;
            case R.id.CB_maisonDesEtudiants :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_maisonDesEtudiants), "Maison des étudiants");
                break;
            case R.id.CB_medecins :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_medecins), "Médecin");
                break;
            case R.id.CB_pharmacies :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_pharmacies), "Laverie");
                break;
            case R.id.CB_reductionsACourtTerme :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_reductionsACourtTerme), "Réduction à court terme");
                break;
            case R.id.CB_reductionsALongTerme :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_reductionsALongTerme), "Réduction à long terme");
                break;
            case R.id.CB_terrains :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_terrains), "Terrain");
                break;
            case R.id.CB_salleDeSport :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_salleDeSport), "Salle de sport");
                break;
            case R.id.CB_stades :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_stades), "Stade");
                break;
            case R.id.CB_stationsEssences :
                ShowOrHideType((CheckBox) v.findViewById(R.id.CB_stationsEssences), "Station essence");
                break;

        }

    }

    /**
     * Permet de cacher tous les éléments, sur la carte, en fonction de leur type
     * @param type Type du bon plan (ex : CAF, Réduction à court terme, ...)
     */
    private void hideFromMap(String type) {

        for(int i = 1 ; i < map.getOverlays().size(); i++) {
            MyItemizedOverlay overlay = (MyItemizedOverlay) map.getOverlays().get(i);
            String categorie = overlay.getItem(0).getSnippet();

            if(categorie.equals(type)) {
                Drawable marker = ContextCompat.getDrawable(getContext(), R.drawable.transparent);
                Bitmap bitmap = ((BitmapDrawable) marker).getBitmap();
                Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 1, 1, true));
                overlay.getItem(0).setMarker(d);
            }

        }

    }

    /**
     * Permet de cacher ou montrer des éléments de la carte en fonction de la catégorie du bon plan.
     * Ce choix est effectué par l'utilisateur à l'aide des checkbox des filtres
     * @param CB Checkbox dont l'état est à observer
     * @param type Type du bon plan (ex : CAF, Réduction à court terme, ...)
     */
    private void ShowOrHideType(CheckBox CB, String type) {
        if(!CB.isChecked())
            hideFromMap(type);
        else
            showOnMap(type);
    }

    /**
     * Permet de montrer tous les éléments, sur la carte, en fonction de leur type
     * @param type Type du bon plan (ex : CAF, Réduction à court terme, ...)
     */
    private void showOnMap(String type) {

        for(int i = 1 ; i < map.getOverlays().size(); i++) {
            MyItemizedOverlay overlay = (MyItemizedOverlay) map.getOverlays().get(i);
            String categorie = overlay.getItem(0).getSnippet();

            if(categorie.equals(type)) {
                Drawable d = getDrawable(type);
                overlay.getItem(0).setMarker(d);
            }

        }

    }

    /**
     * Permet de savoir si l'utilisateur est connecté en vérifiant le token en mémoire.
     * @return True si l'utilisateur est correctement authentifié, sinon false.
     * @throws IOException
     * @throws JSONException
     */
    public boolean checkToken() throws IOException, JSONException {
        SharedPreferences settings = getContext().getSharedPreferences("StudenN3_storage", 0);
        String token = settings.getString("token", "");

        CheckTokenService cts = new CheckTokenService();
        Boolean checkToken = cts.validateToken(token);

        if (checkToken) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Méthode permettant d'ajouter les bons plans sur la carte, à partir des données en base
     * @param map Carte présente sur l'écran
     * @throws JSONException
     * @throws IOException
     * @throws MalformedURLException
     */
    public void putBonsPlans(MapView map) throws JSONException, IOException, MalformedURLException{

        //On récupère les bons plans depuis le webservice
        GetBonsPlansService gbps = new GetBonsPlansService();
        String bonsPlans = gbps.getCurrentBonsPlans();
        JSONArray jsonArrayBonsPlans  = new JSONArray(bonsPlans);

        //Si on a des bons plans à afficher
        if(jsonArrayBonsPlans != null) {
            for (int i=0; i<jsonArrayBonsPlans.length(); i++) {
                //Pour chaque bon plan, on récupère son titre, son type et les coordonnées pour l'afficher sur la carte
                JSONObject bonPlan = (JSONObject) jsonArrayBonsPlans.get(i);
                String bonPlanNom = bonPlan.getString("nom");
                String bonPlanType = bonPlan.getString("type");
                Double bonPlanLongitude = bonPlan.getDouble("longitude");
                Double bonPlanLatitude = bonPlan.getDouble("latitude");
                addBPtoMap(map, bonPlanNom, bonPlanType, bonPlanLongitude, bonPlanLatitude);
            }

        }

    }

    /**
     * Méthode permettant d'afficher l'icône du bon plan sur la carte
     * @param map Carte présente sur l'écran
     * @param bonPlanNom Titre du bon plan
     * @param type Type du bon plan
     * @param bonPlanLongitude Longitude du bon plan
     * @param bonPlanLatitude Latitude du bon plan
     */
    public void addBPtoMap(MapView map, String bonPlanNom, String type, Double bonPlanLongitude, Double bonPlanLatitude) {
        // On ajoute une icône sur la carte
        Drawable marker = getDrawable(type);

        if (marker != null) {
            marker.setBounds(-marker.getIntrinsicWidth() / 4, -marker.getIntrinsicHeight(), marker.getIntrinsicWidth() / 4, 0);

            ResourceProxy resourceProxy = new DefaultResourceProxyImpl(getActivity().getApplicationContext());

            MyItemizedOverlay myItemizedOverlay = new MyItemizedOverlay(marker, resourceProxy);
            map.getOverlays().add(myItemizedOverlay);

            GeoPoint myPoint = new GeoPoint(bonPlanLatitude, bonPlanLongitude);
            myItemizedOverlay.addItem(myPoint, bonPlanNom, type);
        }
    }

    /**
     * Récupère la bonne icône en fonction du bon plan
     * @param type Type de bon plan
     * @return Drawable Icone du bon plan correspondant à son type
     */
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
        Bitmap bitmap = ((BitmapDrawable) marker).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));
        return d;
    }
}
