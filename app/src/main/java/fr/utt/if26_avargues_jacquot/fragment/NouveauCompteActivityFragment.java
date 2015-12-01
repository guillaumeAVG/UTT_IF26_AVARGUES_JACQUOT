package fr.utt.if26_avargues_jacquot.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guillaume.if26_avargues_jacquot.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class NouveauCompteActivityFragment extends Fragment {

    public NouveauCompteActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nouveau_compte, container, false);
    }
}
