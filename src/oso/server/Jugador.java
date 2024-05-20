package oso.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import oso.common.Jugada;
import oso.common.Tablero;


public class Jugador {
    int puntos;
    
    Socket socket;
    ObjectInputStream objectInput;
    ObjectOutputStream objectOutput;

    public Jugador(Socket socket) {
        this.socket = socket;
    }
    
    public void open() throws IOException{
        objectOutput = new ObjectOutputStream(socket.getOutputStream());
        objectInput = new ObjectInputStream(socket.getInputStream());
    }
    
    public void close(){
        try{ objectInput.close(); } catch(Exception ex){}
        try{ objectOutput.close(); } catch(Exception ex){}
        try{ socket.close(); } catch(Exception ex){}
    }

    public void mandar(Tablero tablero) throws IOException {
        objectOutput.writeObject( tablero );
    }

    public void mandarTuTurno() throws IOException {
        objectOutput.writeObject( new TuTurno() );
    }
    
    public void mandarPuntos(int tusPuntos, int otrosPuntos) throws IOException {
        objectOutput.writeObject( new Puntos(tusPuntos, otrosPuntos) );
    }
    
    public void mandarJugada(int x, int y, char letra) {
        try {
            Jugada jugada = new Jugada(x, y, letra);
            objectOutput.writeObject(jugada);
        } catch (IOException ex) {
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Jugada leerJugada() throws IOException, ClassNotFoundException {
       Jugada jugada = (Jugada)objectInput.readObject();
       return jugada;
    }

    public Tablero leerTablero() throws IOException, ClassNotFoundException {
       Tablero jugada = (Tablero)objectInput.readObject();
       return jugada;
    }
    
    public Object leerObjeto() throws IOException, ClassNotFoundException{
        return objectInput.readObject();
    }

    public void resetObjectOutput(){
        try {
            objectOutput.reset();
        } catch (IOException ex) {
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
