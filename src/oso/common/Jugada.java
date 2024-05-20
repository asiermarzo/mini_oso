package oso.common;

import java.io.Serializable;


public class Jugada implements Serializable{
    int x,y;
    char letra;

    public Jugada(int x, int y, char letra) {
        this.x = x;
        this.y = y;
        this.letra = letra;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getLetra() {
        return letra;
    }

    public void setLetra(char letra) {
        this.letra = letra;
    }
    
    
}
