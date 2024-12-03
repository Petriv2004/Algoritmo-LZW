package cliente.modelo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class SocketCliente implements Runnable {

    private ArrayList<Integer> codificado;
    private ArrayList<String> lista;
    private HashMap<Integer, String> dicc;
    int indiceD;

    /* Constructor */
    public SocketCliente(ArrayList<String> ips) throws InterruptedException {
        Thread treadListener = new Thread(this);
        treadListener.start();
        lista = new ArrayList();
        codificado = new ArrayList();
        dicc = new HashMap();
        for (int i = 0; i < 256; i++) {
            dicc.put(i, (char) i + "");
        }
        indiceD = 256;
        leerTxt();
        codificar(ips);
    }

    /* Client:Data >> Socket >> Server */
    public void socket(String msg, String ip) {

        try {
            Socket client = new Socket(ip, 5000); // portSend 5000
            DataOutputStream outBuffer = new DataOutputStream(client.getOutputStream());
            outBuffer.writeUTF(msg);
            client.close();

        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(null, "Client: socket(1) : UnknownHostException: " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Client: socket(2) : IOException: " + e.getMessage());
        }
    }

    @Override
    /* Client: Listen */
    public void run() {
        ServerSocket serverSocket;
        Socket socket;
        DataInputStream inDataBuffer;

        try {
            serverSocket = new ServerSocket(5050); // portListen 5050

            while (true) {
                socket = serverSocket.accept();
                inDataBuffer = new DataInputStream(socket.getInputStream());
                String msg = inDataBuffer.readUTF();
                // System.out.print(msg + " ");
                socket.close();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Client run() : IOException: " + e.getMessage());
        }
//        } catch (InterruptedException ex) {
//            Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void leerTxt() {
        String nombreArchivo = "src\\config\\texto.txt";
        String salida = "";
        //ArrayList<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.length() > 0) {
                    lista.add(linea + "\n");
                } else {
                    lista.add("\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        while (!lista.isEmpty()) {
            salida += lista.remove(0);
        }
        String aux = salida.substring(0, salida.length() - 1);
        String texto[] = aux.split("");
        for (int i = 0; i < texto.length; i++) {
            lista.add(texto[i]);
        }
        lista.add("fin");
    }

    public void codificar(ArrayList<String> ip) throws InterruptedException {
        String PE, SE, PS;
        int index = 0;
        PE = lista.get(index);
        SE = lista.get(++index);
        while (!PE.equals("fin")) {
            PS = PE + SE;
            if (!dicc.containsValue(PS)) {
                if (!PS.contains("fin")) {
                    dicc.put(indiceD, PS);
                    indiceD++;
                }
                System.out.print(encKey(PE) + " ");
                for (int i = 0; i < ip.size(); i++) {
                    socket(encKey(PE) + "", ip.get(i));
                }
                Thread.sleep(500);
                PE = lista.get(index);
                if (PE.equals("fin")) {
                    break;
                }
                SE = lista.get(++index);
            } else {
                PE = PS;
                SE = lista.get(++index);
            }
        }
    }

    private int encKey(String valor) {
        for (Map.Entry<Integer, String> entry : dicc.entrySet()) {
            if (entry.getValue().equals(valor)) {
                return entry.getKey();
            }
        }
        return 0;
    }
}
