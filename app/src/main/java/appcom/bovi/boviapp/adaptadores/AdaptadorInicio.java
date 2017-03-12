package appcom.bovi.boviapp.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import appcom.bovi.boviapp.R;
import appcom.bovi.boviapp.utils.ItemClickListener;

/**
 * Created by diego on 9/03/17.
 */

public class AdaptadorInicio extends RecyclerView.Adapter<AdaptadorInicio.ViewHolder> {


    static ItemClickListener clickListener;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // Campos respectivos de un item
        public TextView nombre;
        public ImageView imagen;

        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.nombre);
            imagen = (ImageView) v.findViewById(R.id.miniatura);

            v.setTag(v);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            //Log.i("posision","posicion:");
            if(clickListener != null){
                clickListener.onClick(view, getAdapterPosition()); //OnItemClickListener mItemClickListener;
            }
        }
    }

    public void setClickListener(ItemClickListener itemClickListener){
        this.clickListener = itemClickListener;
    }

    public AdaptadorInicio() {
    }

    @Override
    public int getItemCount() {
        return Funciones.FUNCIONES.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.funciones, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Funciones item = Funciones.FUNCIONES.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());

    }

}