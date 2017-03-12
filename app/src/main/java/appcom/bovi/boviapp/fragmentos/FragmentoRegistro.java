package appcom.bovi.boviapp.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import appcom.bovi.boviapp.R;



public class FragmentoRegistro extends Fragment implements View.OnClickListener {

    private ImageView camara;


    public FragmentoRegistro() {
        // Required empty public constructor
    }

    public static FragmentoRegistro newInstance(String param1, String param2) {
        FragmentoRegistro fragment = new FragmentoRegistro();
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
        View view = inflater.inflate(R.layout.fragmento_registro, container, false);

        camara = (ImageView)view.findViewById(R.id.imageView);
        camara.setOnClickListener(this);


        return view;

    }

    @Override
    public void onClick(View view) {

    }






    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

}
