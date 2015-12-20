package fr.utt.if26_avargues_jacquot.fragment;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.guillaume.if26_avargues_jacquot.R;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import fr.utt.if26_avargues_jacquot.activity.NouveauBonPlanActivity;
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

        // On ajoute une icône sur la carte
        Drawable marker = ContextCompat.getDrawable(getContext(), R.drawable.bus);
        int markerWidth = marker.getIntrinsicWidth();
        int markerHeight = marker.getIntrinsicHeight();
        marker.setBounds(0, markerHeight, markerWidth, 0);

        ResourceProxy resourceProxy = new DefaultResourceProxyImpl(getActivity().getApplicationContext());

        MyItemizedOverlay myItemizedOverlay = new MyItemizedOverlay(marker, resourceProxy);
        map.getOverlays().add(myItemizedOverlay);

        Geocoder geoCoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> address = null;
        try {
            address = geoCoder.getFromLocationName("Universite de technologies de troyes, Troyes, France", 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        double latitude = address.get(0).getLatitude();
        double longitude = address.get(0).getLongitude();

        GeoPoint myPoint1 = new GeoPoint(latitude, longitude);
        myItemizedOverlay.addItem(myPoint1, "myPoint1", "myPoint1");
        return rootView;
    }

    /*La méthode setUserVisible permet d'afficher un message à l'utilisateur que sa carte est affichée.*/
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            Toast.makeText(getActivity().getApplicationContext(), "Carte affichée", Toast.LENGTH_LONG).show();
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
}
