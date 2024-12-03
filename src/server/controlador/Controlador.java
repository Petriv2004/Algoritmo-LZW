package server.controlador;

import server.modelo.SocketServer;

public class Controlador {
    // Atributos

    // Relaciones 	
    private SocketServer server;

    // Constructor	
    public Controlador() {
        server = new SocketServer();
    }

    // Recibe las referencias de los objetos a controlar en la interfaz	
    public void conectar() {
    }

    // --------------------------------------------------------------	
    // Implementacion de los requerimientos de usuario.	
    // --------------------------------------------------------------
    public void socket(String msg) {
        server.run();
    }

    public SocketServer getServer() {
        return server;
    }

}
