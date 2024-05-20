package oso.server;

import java.io.Serializable;

public class Puntos implements Serializable{
    int tusPuntos;
    int otrosPuntos;

    public Puntos(int tusPuntos, int otrosPuntos) {
        this.tusPuntos = tusPuntos;
        this.otrosPuntos = otrosPuntos;
    }

    public int getTusPuntos() {
        return tusPuntos;
    }

    public void setTusPuntos(int tusPuntos) {
        this.tusPuntos = tusPuntos;
    }

    public int getOtrosPuntos() {
        return otrosPuntos;
    }

    public void setOtrosPuntos(int otrosPuntos) {
        this.otrosPuntos = otrosPuntos;
    }
    
    
}
