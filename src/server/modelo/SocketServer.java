package server.modelo;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class SocketServer implements Runnable {

    private HashMap<Integer, String> diccionario;
    private int indice;
    private int PE, SE;
    private String PS;

    /* Constructor */
    public SocketServer() {
        Thread treadListener = new Thread(this);
        treadListener.start();
        diccionario = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            diccionario.put(i, (char) i + "");
        }
        indice = 256;
        PE = -1;
        SE = -1;
        PS = "";
    }

    /* Server:Data >> Socket >> Client */
//    public static void socket(String msg) {
//        try {
//            Socket server = new Socket("192.168.10.16", 5050); // portSend 5050
//            DataOutputStream outBuffer = new DataOutputStream(server.getOutputStream());
//            outBuffer.writeUTF(msg);
//            
//            server.close();
//        } catch (UnknownHostException e) {
//            JOptionPane.showMessageDialog(null, "Server : socket() : UnknownHostException: " + e.getMessage());
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(null, "Server : socket() : IOException: " + e.getMessage());
//        }
//    }
    @Override
    /* Server: Listen */
    public void run() {
        ServerSocket serverSocket;
        Socket socket;
        DataInputStream inDataBuffer;

        try {
            serverSocket = new ServerSocket(5000); // portListen 5000

            while (true) {
                socket = serverSocket.accept();
                inDataBuffer = new DataInputStream(socket.getInputStream());
                String msg = inDataBuffer.readUTF();
                String deco = decodificar(msg);
                if (deco.length() == 1) {
                    System.out.print("\u001B[30m" + deco);
                } else {
                    System.out.print("\u001B[31m" + deco + "\u001B[0m");
                }
                Thread.sleep(500);
                socket.close();
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Server : run (): IOException: " + e.getMessage());
        } catch (InterruptedException ex) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public static void response(String msg) {
//        socket(msg);
//    }
    public String decodificar(String entrada) {
        entrada = entrada.trim();
        if (!entrada.isEmpty()) {
            if (PE == -1 && SE == -1) {
                PE = Integer.parseInt(entrada);
                //System.out.print(diccionario.get(PE));
                return diccionario.get(PE);
            } else if (SE == -1) {
                SE = Integer.parseInt(entrada);
                PS = diccionario.get(PE) + diccionario.get(SE).charAt(0);
                if (!diccionario.containsValue(PS)) {
                    diccionario.put(indice, PS);
                    indice++;
                }
                //System.out.print(diccionario.get(SE));
                return diccionario.get(SE);
            } else {
                PE = SE;
                SE = Integer.parseInt(entrada);
                if (diccionario.get(SE) == null) {
                    PS = diccionario.get(PE) + diccionario.get(PE).charAt(0);
                } else {
                    PS = diccionario.get(PE) + diccionario.get(SE).charAt(0);
                }

                if (!diccionario.containsValue(PS)) {
                    diccionario.put(indice, PS);
                    indice++;
                }
                //System.out.print(diccionario.get(SE));
                return diccionario.get(SE);
            }
        }
        return null;
    }
}
