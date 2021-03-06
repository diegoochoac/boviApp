package appcom.bovi.boviapp.adaptadores;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import appcom.bovi.boviapp.R;

/**
 * Created by diego on 9/03/17.
 */

public class Funciones {

    private String nombre;
    private int idDrawable;



    public Funciones(String nombre, int idDrawable) {
        this.nombre = nombre;
        this.idDrawable = idDrawable;
    }

    public static final List<Funciones> FUNCIONES = new ArrayList<Funciones>();



    static {
        FUNCIONES.add(new Funciones("Registro", R.drawable.ic_registro));
        FUNCIONES.add(new Funciones("Listado",R.drawable.ic_listado));
        FUNCIONES.add(new Funciones("Notificaciones",R.drawable.ic_notificacion));
        FUNCIONES.add(new Funciones("Rastreo",R.drawable.ic_ubicacion));

    }
    public String getNombre() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }


}
