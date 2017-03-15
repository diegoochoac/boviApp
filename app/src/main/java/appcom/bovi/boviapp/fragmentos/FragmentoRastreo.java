package appcom.bovi.boviapp.fragmentos;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appcom.bovi.boviapp.R;


public class FragmentoRastreo extends Fragment {

    public FragmentoRastreo() {
        // Required empty public constructor
    }

    public static FragmentoRastreo newInstance(String param1, String param2) {
        FragmentoRastreo fragment = new FragmentoRastreo();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmento_rastreo, container, false);

        return view;
    }

}
