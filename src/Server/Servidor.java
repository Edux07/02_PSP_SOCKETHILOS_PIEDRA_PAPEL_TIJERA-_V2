package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	public static final int PUERTO = 2080;

	public static void main(String[] args) throws IOException {
		System.out.println("-------- Servidor Juego de Piedra-Papel-Tijera --------");

		try (ServerSocket server = new ServerSocket(PUERTO)) {
			System.out.println("Esperando conexiones en el puerto " + PUERTO);

			while (true) {
				Socket socketAlCliente = server.accept();
				System.out.println("Nuevo cliente conectado.");

				new ServidorHilo(socketAlCliente).start();
			}
		} catch (Exception e) {
			System.err.println("Error en el servidor -> " + e);
			e.printStackTrace();
		}
	}
}
