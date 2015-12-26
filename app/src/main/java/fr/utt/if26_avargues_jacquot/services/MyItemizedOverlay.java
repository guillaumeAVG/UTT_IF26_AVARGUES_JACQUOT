package fr.utt.if26_avargues_jacquot.services;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import java.util.ArrayList;

import fr.utt.if26_avargues_jacquot.activity.AffichageBonPlanActivity;
import fr.utt.if26_avargues_jacquot.activity.MainActivity;
import fr.utt.if26_avargues_jacquot.activity.NouveauBonPlanActivity;

/**
 * Classe permettant de gérer l'overlay par dessus la carte permettant d'afficher les bons plans sur la carte.
 */
public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {

    private ArrayList<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();

    public MyItemizedOverlay(Drawable pDefaultMarker,
                             ResourceProxy pResourceProxy) {
        super(pDefaultMarker, pResourceProxy);
    }

    public void addItem(GeoPoint p, String title, String snippet) {
        OverlayItem newItem = new OverlayItem(title, snippet, p);
        overlayItemList.add(newItem);
        populate();
    }

    @Override
    public boolean onSnapToItem(int arg0, int arg1, Point arg2, IMapView arg3) {
        return false;
    }

    @Override
    protected OverlayItem createItem(int arg0) {
        return overlayItemList.get(arg0);
    }

    @Override
    public int size() {
        return overlayItemList.size();
    }


    @Override
    protected boolean onTap(int i) {
        GeoPoint  gpoint = (GeoPoint) overlayItemList.get(i).getPoint();
        double lat = gpoint.getLatitudeE6()/1e6;
        double lon = gpoint.getLongitudeE6()/1e6;
        String toast = "Title: "+overlayItemList.get(i).getTitle();
        toast += "\nText: "+overlayItemList.get(i).getSnippet();
        toast += 	"\nSymbol coordinates: Lat = "+lat+" Lon = "+lon+" (microdegrees)";
        MainActivity main = new MainActivity();
        //création de notre item
        Intent defineIntent = new Intent(main.ma.getApplicationContext(), AffichageBonPlanActivity.class);
        // objet qui vas nous permettre de passe des variables ici la variable passInfo
        Bundle objetbunble = new Bundle();
        objetbunble .putString("nom", overlayItemList.get(i).getTitle().toString());
        // on passe notre objet a notre activities
        defineIntent.putExtras(objetbunble);
        defineIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // on appelle notre activité
        main.ma.getApplicationContext().startActivity(defineIntent);



        return(true);
    }
}