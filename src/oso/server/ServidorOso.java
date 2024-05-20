package oso.server;

import java.net.ServerSocket;
import java.net.Socket;


public class ServidorOso extends Thread{
    public final static int TABLERO_XDIM = 5, TABLERO_YDIM = 3;
    
    final int port;

    public ServidorOso(int serverPort) {
        this.port = serverPort;
    }

    @Override
    public void run() {
        try( ServerSocket serverSocket = new ServerSocket(port); ){
        System.out.println("Started server on port " + port);
            //cada 2 clientes los metemos a una partida
            while( ! interrupted() ){
                Socket socketA = null;
                Socket socketB = null;
                try{
                    socketA = serverSocket.accept();
                    socketB = serverSocket.accept();
                    
                    Partida partida = new Partida(TABLERO_XDIM, TABLERO_YDIM, socketA, socketB);
                    partida.start();
                }catch(Exception ex){
                    ex.printStackTrace();
                    try{ socketA.close(); } catch(Exception ex1){}
                    try{ socketB.close(); } catch(Exception ex1){} 
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServidorOso servidor = new ServidorOso(12000);
        servidor.start();
    }
}
