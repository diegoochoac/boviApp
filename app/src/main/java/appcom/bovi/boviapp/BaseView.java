package appcom.bovi.boviapp;

/**
 * Created by diego on 11/03/17.
 */

/**
 * Interfaz de comportamiento general de vistas
 */
public interface BaseView<T> {
    void setPresenter(T presenter);
}