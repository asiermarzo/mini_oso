package oso.server;

import oso.network.Jugador;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import oso.game.Jugada;
import oso.game.Tablero;

public class Partida extends Thread{
    Tablero tablero;
    boolean esTurnoA;
    Jugador jugadorA;
    Jugador jugadorB;

    Partida(int xDim, int yDim, Socket socketA, Socket socketB) {
        tablero = new Tablero(xDim, yDim);
        jugadorA = new Jugador(socketA);
        jugadorB = new Jugador(socketB);
    }

    @Override
    public void run() {
        try {
            jugadorA.open();
            jugadorB.open();     
            
            jugadorA.mandarTablero( tablero );
            jugadorB.mandarTablero( tablero );
            
            esTurnoA = Math.random() > 0.5; //turno al azar para A o B
            while( tablero.estaLleno() == false ){
                jugadorA.resetObjectOutput();
                jugadorB.resetObjectOutput();
                
                Jugador jugador = esTurnoA ? jugadorA : jugadorB;
                
                jugador.mandarTuTurno();
                Jugada jugada = jugador.leerJugada();
                
                if ( tablero.puedo(jugada) ){ 
                    int osos = tablero.aplicar( jugada );
                    jugador.puntos += osos;
                }
                
                jugadorA.mandarPuntos(jugadorA.puntos, jugadorB.puntos);
                jugadorB.mandarPuntos(jugadorB.puntos, jugadorA.puntos);
                
                esTurnoA = ! esTurnoA;
                
                jugadorA.mandarTablero( tablero );
                jugadorB.mandarTablero( tablero );
            }
                        
        } catch (Exception ex) {
            Logger.getLogger(Partida.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            jugadorA.close();
            jugadorB.close();
        }
    }
    
    
    
    
}
