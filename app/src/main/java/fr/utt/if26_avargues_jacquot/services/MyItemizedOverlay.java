package fr.utt.if26_avargues_jacquot.services;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import java.util.ArrayList;

import fr.utt.if26_avargues_jacquot.activity.MainActivity;

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
        Toast.makeText(main.ma.getApplicationContext(), toast, Toast.LENGTH_LONG).show();
        return(true);
    }
}