package appcom.bovi.boviapp.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appcom.bovi.boviapp.R;


public class FragmentoListado extends Fragment {

    public FragmentoListado() {
        // Required empty public constructor
    }

    public static FragmentoListado newInstance(String param1, String param2) {
        FragmentoListado fragment = new FragmentoListado();
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
        return inflater.inflate(R.layout.fragmento_listado, container, false);
    }

}
