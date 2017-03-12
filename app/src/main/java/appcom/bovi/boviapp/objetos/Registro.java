package appcom.bovi.boviapp.objetos;

/**
 * Created by diego on 12/03/17.
 */

public class Registro {

    public String nombre;
    public int edad;
    public String dueño;

    public Registro(){

    }

    public Registro(String nombre, int edad, String dueño) {
        this.nombre = nombre;
        this.edad = edad;
        this.dueño = dueño;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDueño() {
        return dueño;
    }

    public void setDueño(String dueño) {
        this.dueño = dueño;
    }
}
