package oso;

import oso.clients.ClienteOso;
import oso.server.ServidorOso;


public class ServerY2Clientes {
    public static void main(String[] args) {
        ServidorOso servidor = new ServidorOso(12000);
        servidor.start();
        
        for(int i = 0; i < 2; i++){
            ClienteOso cliente = new ClienteOso("localhost", 12000, i+1);
            cliente.start();
        }
    }
}
