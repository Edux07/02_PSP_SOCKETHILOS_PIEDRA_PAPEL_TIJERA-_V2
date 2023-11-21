package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServidorHilo extends Thread {
	private Socket socketAlCliente;
	public int victoriasCliente=0;
	public int victoriasServidor=0;

	public ServidorHilo(Socket socketAlCliente) {
		this.socketAlCliente = socketAlCliente;
	}

	@Override
	public void run() {
		try {
			PrintStream salida = new PrintStream(socketAlCliente.getOutputStream());
			BufferedReader bf = new BufferedReader(new InputStreamReader(socketAlCliente.getInputStream()));

			while (victoriasCliente < 3 && victoriasServidor < 3) {
				System.out.println("Esperando elección del cliente...");
				
				String eleccionCliente = bf.readLine();
				System.out.println("Elección del cliente: " + eleccionCliente);

				String eleccionServidor = Aleatorio();
				System.out.println("Elección del servidor: " + eleccionServidor);

				String resultado = decidirGanador(eleccionCliente, eleccionServidor);
				System.out.println("Resultado de la ronda: " + resultado);

				salida.println(resultado);
				
				 if (resultado.equals("Ganaste")) {
	                    victoriasCliente++;
	                } else if (resultado.equals("Perdiste")) {
	                    victoriasServidor++;
	                }
	            }

	            if (victoriasCliente == 3) {
	                salida.println("¡Has ganado la partida!");
	            } else {
	                salida.println("¡Has perdido la partida!");
	            }
			

			socketAlCliente.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String Aleatorio() {
		String[] opciones = { "piedra", "papel", "tijera" };
		int indice = (int) (Math.random() * opciones.length);
		return opciones[indice];
	}

	private String decidirGanador(String eleccionCliente, String eleccionServidor) {

		if (eleccionCliente.equals(eleccionServidor)) {
			return "Empate";

		} else if ((eleccionCliente.equals("piedra") && eleccionServidor.equals("tijera"))
				|| (eleccionCliente.equals("papel") && eleccionServidor.equals("piedra"))
				|| (eleccionCliente.equals("tijera") && eleccionServidor.equals("papel"))) {

			return "Ganaste";

		} else {
			return "Perdiste";
		}
	}

}
