package cliente.interfaz;

import cliente.controlador.Controlador;
import java.util.ArrayList;
import java.util.Scanner;

public class InterfazAppCliente {

    /* Relaciones */
    private Controlador ctrl;

    public InterfazAppCliente(Controlador ctrl) throws InterruptedException {
        this.ctrl = ctrl;

    }

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        int can;
        String ip;
        ArrayList<String> ips = new ArrayList();
        System.out.print("A cuántas IP quiere enviar: ");
        can = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < can; i++) {
            System.out.print("Ingrese la IP " + (i + 1) + ": ");
            ip = sc.nextLine();
            ips.add(ip);
        }

        new InterfazAppCliente(new Controlador(ips));
    }
}
