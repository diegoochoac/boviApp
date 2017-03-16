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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import appcom.bovi.boviapp.R;
import appcom.bovi.boviapp.utils.OnFragmentInteractionListener;


public class FragmentoRegistro extends Fragment implements View.OnClickListener {

    private ImageView imagCamara;
    private EditText editNombre,editEdad, editPeso, editDias, editDueño, editUbicacion;
    private TextView texRaza;
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

        imagCamara = (ImageView)view.findViewById(R.id.imageView);
        imagCamara.setOnClickListener(this);
        //imagCamara.setImageBitmap(imagen);
        editNombre = (EditText)view.findViewById(R.id.editTextNombre);
        editEdad = (EditText)view.findViewById(R.id.editTextEdad);
        editPeso = (EditText)view.findViewById(R.id.editTextPeso);
        editDias = (EditText)view.findViewById(R.id.editTextDias) ;
        editDueño = (EditText)view.findViewById(R.id.editTextDueño);
        editUbicacion = (EditText)view.findViewById(R.id.editTextUbicacion);
        texRaza = (TextView)view.findViewById(R.id.textViewRaza) ;
        texRaza.setOnClickListener(this);
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
        Log.i("Fragmento Inicio","Registro Posicion: "+view.getId());
        switch (view.getId()) {
            case R.id.imageView:
                uri = Uri.parse(SET_REGISTRO+":"+"FOTO");
                break;

            case R.id.textViewRaza:
                uri = Uri.parse(SET_REGISTRO+":"+"RAZA");
                break;

            case R.id.buttonAgregar:
                uri = Uri.parse(SET_REGISTRO+":"+"AGREGAR");
                break;

        }
        mCallback.onFragmentIteration(uri);
    }


}
