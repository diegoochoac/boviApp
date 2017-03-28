package appcom.bovi.boviapp.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appcom.bovi.boviapp.R;
import appcom.bovi.boviapp.objetos.Registro;

/**
 * Created by diego on 11/03/17.
 */

public class AdaptadorListado extends RecyclerView.Adapter<AdaptadorListado.ViewHolder>{

    List<Registro> registros = new ArrayList<>();

    public AdaptadorListado(List<Registro> registros) {
        this.registros = registros;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_list_listado, parent, false);
        return new AdaptadorListado.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Registro registro = registros.get(position);
        holder.Nombre.setText("Nombre: "+registro.getNombre());
        holder.Raza.setText("Raza: "+registro.getRaza());
        holder.Edad.setText("Edad: "+registro.getEdad()+" Años");
    }

    @Override
    public int getItemCount() {
        return registros.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView Nombre, Raza, Edad;

        public ViewHolder(View itemView) {
            super(itemView);
            Nombre = (TextView) itemView.findViewById(R.id.textNombre);
            Raza = (TextView) itemView.findViewById(R.id.textRaza);
            Edad = (TextView) itemView.findViewById(R.id.textEdad);
        }
    }
}
