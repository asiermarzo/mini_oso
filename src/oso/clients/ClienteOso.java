package oso.clients;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import oso.common.Tablero;
import oso.server.FinPartida;
import oso.server.Jugador;
import oso.server.Puntos;
import oso.server.TuTurno;


public class ClienteOso extends Thread{
    private final String host;
    private final int port;
    
    Jugador jugador;
    OsoGUI gui;

    public ClienteOso(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(host, port);
            jugador = new Jugador(socket);
            jugador.open();
            
            Tablero tablero = jugador.leerTablero();
            
            gui = new OsoGUI(jugador);
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
            gui.iniciarBotones(tablero.getxDim(), tablero.getyDim());
            gui.actualizar(tablero);
            gui.activar(false);
            
            while(interrupted() == false){
                Object msg = jugador.leerObjeto();
                if (msg instanceof TuTurno){
                    TuTurno turno = (TuTurno)msg;
                    gui.activar( true );
                }else if (msg instanceof FinPartida){
                    FinPartida fin = (FinPartida)msg;
                    gui.terminar();
                }else if (msg instanceof Puntos){
                    Puntos puntos = (Puntos)msg;
                    gui.actualizarPuntos(puntos.getTusPuntos(), puntos.getOtrosPuntos());
                }else if (msg instanceof Tablero){
                    tablero = (Tablero)msg;
                    gui.actualizar(tablero);
                }else{
                    System.out.println("Mensaje no identificado " + msg.getClass().getName());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ClienteOso.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{ jugador.close(); }catch(Exception ex){}
        }
        gui.terminar();
    }
   
    
    public static void main(String[] args) {
        ClienteOso juegoRed = new ClienteOso("localhost", 12000);
        juegoRed.start();
    }
}
