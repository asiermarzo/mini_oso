package oso.network;

import java.io.Serializable;

public class PaquetePuntos implements Serializable{
    int tusPuntos;
    int otrosPuntos;

    public PaquetePuntos(int tusPuntos, int otrosPuntos) {
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
