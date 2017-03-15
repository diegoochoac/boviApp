package appcom.bovi.boviapp.fragmentos;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import appcom.bovi.boviapp.R;
import appcom.bovi.boviapp.utils.OnFragmentInteractionListener;


public class FragmentoRegistro extends Fragment implements View.OnClickListener {

    private ImageView camara;
    private Button btnRegistrar;


    private OnFragmentInteractionListener mCallback = null;
    Uri uri = Uri.parse("");
    //Variables que se van hacia el MAIN
    public final static String SET_REGISTRO = "registro";


    public FragmentoRegistro() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragmento_registro, container, false);

        camara = (ImageView)view.findViewById(R.id.imageView);
        camara.setOnClickListener(this);
        //camara.setImageBitmap(imagen);

        btnRegistrar = (Button) view.findViewById(R.id.buttonAgregar);
        btnRegistrar.setOnClickListener(this);
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
    public void onClick(View view) {
        Log.i("Fragmento Inicio","Registro Posicion: "+view.toString());
        uri = Uri.parse(SET_REGISTRO+":"+view.toString());
        mCallback.onFragmentIteration(uri);
    }


}
