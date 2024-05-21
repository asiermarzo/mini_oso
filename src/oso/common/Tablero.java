package oso.common;

import java.io.Serializable;

public class Tablero implements Serializable{
    public static final char CHAR_VACIO = ' ';

    char[][] casillas;
    int xDim, yDim;

    public Tablero(int xDim, int yDim) {
        if (xDim <= 0 || yDim <= 0) {
            throw new IllegalArgumentException("Las dimensiones tienen que se mayores de 0");
        }
        
        this.xDim = xDim;
        this.yDim = yDim;
        casillas = new char[xDim][yDim];
        limpiar();
    }

    public int getxDim() {
        return xDim;
    }

    public int getyDim() {
        return yDim;
    }
    
    public char letraEn(int x, int y){
        return casillas[x][y];
    }
    
    public boolean usadaEn(int x, int y){
        return Character.isUpperCase( casillas[x][y] );
    }

    public void limpiar() {
        for (int x = 0; x < xDim; ++x) 
            for (int y = 0; y < yDim; ++y) 
                casillas[x][y] = CHAR_VACIO;
    }

    public boolean estaLleno() {
        for (int x = 0; x < xDim; ++x) 
            for (int y = 0; y < yDim; ++y) 
                if (casillas[x][y] == CHAR_VACIO) 
                    return false;
        return true;
    }

    public boolean puedo(Jugada jugada) {
        final int x = jugada.getX();
        final int y = jugada.getY();
        
        if (x < 0 || x >= xDim || y < 0 || y >= yDim) 
            return false;
        
        return casillas[x][y] == CHAR_VACIO;
    }

    final static int[][] DIRS8 = {{-1, -1}, {0, -1}, {1, -1}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {-1, 0}};
    final static int[][] DIRS4 = {{0, 1}, {1, 1}, {1, 0}, {-1, 1}};
    public int aplicar(Jugada jugada) {
        final int x = jugada.getX();
        final int y = jugada.getY();
        final char letra = jugada.getLetra();
        
        casillas[x][y] = letra;
        
        int osos = 0;
        if (letra == 'o') { //o>so en cualquiera de las 8 direcciones
            for (int[] v : DIRS8) {
                if (comprobarYUsar2Casillas(
                        x + v[0] * 1, y + v[1] * 1, 's',
                        x + v[0] * 2, y + v[1] * 2, 'o')) {
                    osos += 1;
                }
            }
        }else if (letra == 's') { //o<s>o en cualquiera de 4 direcciones (derecha, derecha-arriba, arriba, izquierda-arriba)
            for (int[] v : DIRS4) {
                if (comprobarYUsar2Casillas(
                        x - v[0], y - v[1], 'o',
                        x + v[0], y + v[1], 'o')) {
                    osos += 1;
                }
            }
        }

        if (osos >= 1) 
            marcarUsada(x, y);
        
        return osos;
    }

    boolean comprobarYUsar2Casillas(int x1, int y1, char letra1, int x2, int y2, char letra2) {
        if (estaDentroYEsLetra(x1, y1, letra1) && estaDentroYEsLetra(x2, y2, letra2)) {
            marcarUsada(x1, y1);
            marcarUsada(x2, y2);
            return true;
        }
        return false;
    }

    void marcarUsada(int x, int y) {
        final char letra = casillas[x][y];
        casillas[x][y] = Character.toUpperCase(letra);
    }

    boolean estaDentroYEsLetra(int x, int y, char letter) {
        return x >= 0 && x < xDim
                && y >= 0 && y < yDim
                && casillas[x][y] == letter;
    }

    public void imprimir() {
        for (int x = 0; x < xDim; ++x) {
            String l = "";
            for (int y = 0; y < yDim; ++y) {
                l += "[" + casillas[x][y] + "]";
            }
            System.out.println(l);
        }
    }


}
