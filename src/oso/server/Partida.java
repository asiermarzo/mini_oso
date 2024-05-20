package oso.server;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import oso.common.Jugada;
import oso.common.Tablero;

public class Partida extends Thread{
    Tablero tablero;
    boolean turnoA;
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
            
            jugadorA.mandar( tablero );
            jugadorB.mandar( tablero );
            
            turnoA = Math.random() > 0.5; //turno al azar para A o B
            while( tablero.estaLleno() == false ){
                jugadorA.resetObjectOutput();
                jugadorB.resetObjectOutput();
                
                Jugador jugador = turnoA ? jugadorA : jugadorB;
                Jugador otroJugador = turnoA ? jugadorB : jugadorA;
                
                jugador.mandarTuTurno();
                Jugada jugada = jugador.leerJugada();
                
                if (tablero.puedo(jugada) == false){ //si la jugada es invalida, el jugador pierde la partida
                    jugador.puntos = -100;
                    break;
                }
                
                int osos = tablero.aplicar( jugada );
                jugador.puntos += osos;
                
                jugadorA.mandarPuntos(jugadorA.puntos, jugadorB.puntos);
                jugadorB.mandarPuntos(jugadorB.puntos, jugadorA.puntos);
                
                turnoA = ! turnoA;
                
                jugadorA.mandar( tablero );
                jugadorB.mandar( tablero );
            }
            
            if (jugadorA.puntos > jugadorB.puntos){
                
            }else{
                
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Partida.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            jugadorA.close();
            jugadorB.close();
        }
    }
    
    
    
    
}
