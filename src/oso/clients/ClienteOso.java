package oso.clients;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import oso.game.Tablero;
import oso.network.ConexionJugador;
import oso.network.PaquetePuntos;
import oso.network.PaqueteTuTurno;


public class ClienteOso extends Thread{
    private final int nClient;
    private final String host;
    private final int port;
    
    ConexionJugador jugador;
    OsoGUI gui;

    public ClienteOso(String host, int port) {
        this.host = host;
        this.port = port;
        nClient = 0;
    }
    
    public ClienteOso(String host, int port, int nClient) {
        this.host = host;
        this.port = port;
        this.nClient = nClient;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(host, port);
            jugador = new ConexionJugador(socket);
            jugador.open();
            
            gui = new OsoGUI(jugador);
            gui.setVisible(true);
            if (nClient != 0){
                gui.setTitle("Client " + nClient);
                gui.setLocation( (nClient-1)*gui.getWidth() , 0);
            }else{
                gui.setLocationRelativeTo(null);
            }
            
            Tablero tablero = jugador.leerTablero();
            gui.iniciarBotones(tablero.getxDim(), tablero.getyDim());
            gui.actualizarCasillas(tablero);
            gui.activarGUI(false);
            
            while(interrupted() == false){ //recepcion de mensajes
                Object msg = jugador.leerObjeto();
                if (msg instanceof PaqueteTuTurno){
                    PaqueteTuTurno turno = (PaqueteTuTurno)msg;
                    gui.activarGUI( true );
                }else if (msg instanceof PaquetePuntos){
                    PaquetePuntos puntos = (PaquetePuntos)msg;
                    gui.actualizarPuntos(puntos.getTusPuntos(), puntos.getOtrosPuntos());
                }else if (msg instanceof Tablero){
                    tablero = (Tablero)msg;
                    gui.actualizarCasillas(tablero);
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

    public OsoGUI getGui() {
        return gui;
    }
   
    
    
    public static void main(String[] args) {
        ClienteOso juegoRed = new ClienteOso("localhost", 12000);
        juegoRed.start();
    }
}
