package appcom.bovi.boviapp.adaptadores;

import java.util.ArrayList;
import java.util.List;

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
        FUNCIONES.add(new Funciones("Registro", R.drawable.registro));
        FUNCIONES.add(new Funciones("Listado",R.drawable.registro));
        FUNCIONES.add(new Funciones("Alertas",R.drawable.registro));
        FUNCIONES.add(new Funciones("Rastreo",R.drawable.ubicacion));

    }
    public String getNombre() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }
}
