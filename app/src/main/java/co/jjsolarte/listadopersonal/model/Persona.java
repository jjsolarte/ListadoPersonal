package co.jjsolarte.listadopersonal.model;

import io.realm.RealmObject;

public class Persona extends RealmObject {

    String nombre;
    int edaad;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdaad() {
        return edaad;
    }

    public void setEdaad(int edaad) {
        this.edaad = edaad;
    }
}
