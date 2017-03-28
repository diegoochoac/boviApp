package appcom.bovi.boviapp.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import appcom.bovi.boviapp.R;
import appcom.bovi.boviapp.adaptadores.AdaptadorListado;
import appcom.bovi.boviapp.notifications.PushNotificationsAdapter;
import appcom.bovi.boviapp.objetos.Registro;


public class FragmentoListado extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayout mNoMessagesView;
    private AdaptadorListado mAdaptadorListado;

    List<Registro> registros = new ArrayList<>();

    public FragmentoListado() {
        // Required empty public constructor
    }

    public static FragmentoListado newInstance() {
        FragmentoListado fragment = new FragmentoListado();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            registros =  (List<Registro>) getArguments().getSerializable("DATA");
            Log.i("FragmentoList","DATA:"+registros.size());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragmento_listado, container, false);
        mAdaptadorListado = new AdaptadorListado(registros);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler);
        mNoMessagesView = (LinearLayout) root.findViewById(R.id.noListado);
        mRecyclerView.setAdapter(mAdaptadorListado);

        mAdaptadorListado.notifyDataSetChanged();


        if(registros.size()>1){
            mNoMessagesView.setVisibility(root.GONE);
        }

        return root;
    }

}
