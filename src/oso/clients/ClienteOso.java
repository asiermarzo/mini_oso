package oso.clients;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import oso.common.Tablero;
import oso.server.Jugador;
import oso.server.Puntos;
import oso.server.TuTurno;


public class ClienteOso extends Thread{
    private final int nClient;
    private final String host;
    private final int port;
    
    Jugador jugador;
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
            jugador = new Jugador(socket);
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
            gui.actualizar(tablero);
            gui.activar(false);
            
            while(interrupted() == false){ //recepcion de mensajes
                Object msg = jugador.leerObjeto();
                if (msg instanceof TuTurno){
                    TuTurno turno = (TuTurno)msg;
                    gui.activar( true );
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

    public OsoGUI getGui() {
        return gui;
    }
   
    
    
    public static void main(String[] args) {
        ClienteOso juegoRed = new ClienteOso("localhost", 12000);
        juegoRed.start();
    }
}
