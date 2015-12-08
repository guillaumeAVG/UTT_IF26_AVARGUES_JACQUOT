package fr.utt.if26_avargues_jacquot.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guillaume.if26_avargues_jacquot.R;

/**
 * Created by guillaume on 04/12/2015.
 */
public class FiltreFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_filtres_carte, container, false);

        return rootView;
    }
    @Override
    public void onClick(View v) {

    }
}

