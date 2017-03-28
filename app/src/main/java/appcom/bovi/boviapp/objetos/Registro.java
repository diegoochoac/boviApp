package appcom.bovi.boviapp.objetos;

import java.io.Serializable;

/**
 * Created by diego on 12/03/17.
 */

public class Registro implements Serializable{

    public String nombre, raza, dueño, ubicacion;
    public int edad, peso;

    public Registro(){

    }

    public Registro(String nombre, int edad, int peso, String raza, String dueño, String ubicacion) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.raza = raza;
        this.dueño = dueño;
        this.ubicacion = ubicacion;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
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
