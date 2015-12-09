package fr.utt.if26_avargues_jacquot.fragment;


import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import java.util.ArrayList;
import fr.utt.if26_avargues_jacquot.activity.NouveauBonPlanActivity;
import fr.utt.if26_avargues_jacquot.services.MyItemizedOverlay;


/**
 * Created by guillaume on 26/11/2015.
 */
public class CarteFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_carte, container, false);
        rootView.findViewById(R.id.IMGB_ajouterBonPlan).setOnClickListener(this);
        // rootView.findViewById(R.id.IMGB_filtre).setOnClickListener(this);
        MapView map = (MapView) rootView.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPQUESTOSM);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(14);
        GeoPoint startPoint = new GeoPoint(48.3, 4.0833);
        mapController.setCenter(startPoint);

        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(getContext(), map);
        mRotationGestureOverlay.setEnabled(true);
        map.setMultiTouchControls(true);
        map.getOverlays().add(mRotationGestureOverlay);

        Drawable marker = ContextCompat.getDrawable(getContext(), R.drawable.bus);
        int markerWidth = marker.getIntrinsicWidth();
        int markerHeight = marker.getIntrinsicHeight();
        marker.setBounds(0, markerHeight, markerWidth, 0);

        ResourceProxy resourceProxy = new DefaultResourceProxyImpl(getActivity().getApplicationContext());

        MyItemizedOverlay myItemizedOverlay = new MyItemizedOverlay(marker, resourceProxy);
        map.getOverlays().add(myItemizedOverlay);

        GeoPoint myPoint1 = new GeoPoint(48.3, 4.0833);
        myItemizedOverlay.addItem(myPoint1, "myPoint1", "myPoint1");
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            Toast.makeText(getActivity().getApplicationContext(), "Carte affichée", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IMGB_ajouterBonPlan:

                // On met en place le passage entre les deux activités sur ce Listener
                Intent intent = new Intent(getActivity(), NouveauBonPlanActivity.class);
                startActivity(intent);
                break;
           /* case R.id.IMGB_filtre:

                // On met en place le passage entre les deux activités sur ce Listener
                Intent intent2 = new Intent(getActivity(), FiltreActivity.class);
                startActivity(intent2);
                break;*/
        }

    }
}
