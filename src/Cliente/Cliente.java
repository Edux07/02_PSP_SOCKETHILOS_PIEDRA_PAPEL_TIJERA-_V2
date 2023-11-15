package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    public static final int PUERTO = 2080;
    public static final String IP_SERVER = "localhost";

    public static void main(String[] args) throws SocketException {
        System.out.println("-------- Juego de Piedra-Papel-Tijera --------");

        InetSocketAddress direccionServer = new InetSocketAddress(IP_SERVER, PUERTO);

        try (Socket socketServer = new Socket(); Scanner sc = new Scanner(System.in)) {
            socketServer.connect(direccionServer);

            PrintStream salida = new PrintStream(socketServer.getOutputStream());
            InputStreamReader entrada = new InputStreamReader(socketServer.getInputStream());
            BufferedReader bf = new BufferedReader(entrada);

            for (int i = 0; i < 3; i++) {
                System.out.print("Ingresa tu elección (piedra, papel, tijera): ");
                String eleccion = sc.nextLine();

                salida.println(eleccion);

                System.out.println("Esperando respuesta del servidor...");
                String respuesta = bf.readLine();
                System.out.println("Resultado de la ronda: " + respuesta);
            }

            socketServer.close();

        } catch (UnknownHostException e) {
            System.err.println("No se puede conectar al servidor en la dirección " + IP_SERVER);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error de entrada/salida");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error -> " + e);
            e.printStackTrace();
        }
    }
}