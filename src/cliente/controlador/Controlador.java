package cliente.controlador;

import cliente.modelo.SocketCliente;
import java.util.ArrayList;

public class Controlador {
    // Atributos

    // Relaciones 	
    private SocketCliente cliente;
    //private Codificador cod;
    // Constructor	

    public Controlador(ArrayList<String> ips) throws InterruptedException {
        cliente = new SocketCliente(ips);
    }

    // Recibe las referencias de los objetos a controlar en la interfaz	
    public void conectar() {
    }

    // --------------------------------------------------------------	
    // Implementacion de los requerimientos de usuario.	
    // --------------------------------------------------------------
    public void socket(String msg, String ips) {
        cliente.socket(msg, ips);
    }

//    public Codificador getCod() {
//        return cod;
//    }
    public SocketCliente getCliente() {
        return cliente;
    }
    
}
