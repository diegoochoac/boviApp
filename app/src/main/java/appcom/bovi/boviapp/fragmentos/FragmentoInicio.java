package appcom.bovi.boviapp.fragmentos;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appcom.bovi.boviapp.adaptadores.AdaptadorInicio;
import appcom.bovi.boviapp.R;
import appcom.bovi.boviapp.utils.ItemClickListener;
import appcom.bovi.boviapp.utils.OnFragmentInteractionListener;

public class FragmentoInicio extends Fragment implements ItemClickListener {

    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private AdaptadorInicio adaptador;

    private OnFragmentInteractionListener mCallback = null;
    Uri uri = Uri.parse("");
    //Variables que se van hacia el MAIN
    public final static String SET_INICIO = "Inicio";

    public FragmentoInicio() {
        // Required empty public constructor
    }

    public static FragmentoInicio newInstance(String param1, String param2) {
        FragmentoInicio fragment = new FragmentoInicio();
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
        View view = inflater.inflate(R.layout.fragmento_inicio, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        reciclador.setLayoutManager(layoutManager);

        adaptador = new AdaptadorInicio();
        reciclador.setAdapter(adaptador);
        adaptador.setClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "El Activity debe implementar la interfaz FragmentIterationListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Fragmento Inicio","onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Fragmento Inicio","onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Fragmento Inicio","onDestroy");
    }

    @Override
    public void onClick(View view, int position) {
        Log.i("Fragmento Inicio","Registro Posicion: "+position);
        uri = Uri.parse(SET_INICIO+":"+position);
        mCallback.onFragmentIteration(uri);
    }
}
