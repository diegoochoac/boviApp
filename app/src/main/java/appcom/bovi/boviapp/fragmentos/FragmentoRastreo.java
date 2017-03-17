package appcom.bovi.boviapp.fragmentos;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

import appcom.bovi.boviapp.R;


public class FragmentoRastreo extends SupportMapFragment {

    public FragmentoRastreo() {
        // Required empty public constructor
    }

    public static FragmentoRastreo newInstance() {
        return new FragmentoRastreo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragmento_rastreo, container, false);
        View root = super.onCreateView(inflater, container, savedInstanceState);

        return root;
    }

}
