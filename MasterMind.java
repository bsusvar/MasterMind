package masterMind;

import java.util.Arrays;

import java.util.Scanner;

public class MasterMind {

	public static void main(String[] args) {

		// Creación de un String con los números
		String lista = ("123456789");
		char[] codigo = lista.toCharArray();

		// Mezcla aleatoria de los números (caracteres) del array intercambiando sus
		// posiciones
		int i = 0;
		int j = 0;
		char temp = 0;
		for (i = 0; i < codigo.length - 1; i++) {
			j = (int) (Math.random() * codigo.length);
			temp = codigo[i];
			codigo[i] = codigo[j];
			codigo[j] = temp;
		}

		// Selección de los 4 dígitos del código secreto
		char[] codigoSecreto = Arrays.copyOfRange(codigo, 0, 4);
		System.out.println(codigoSecreto);

		// Introducción al juego
		Scanner input = new Scanner(System.in);
		System.out.println(
				"¡Bienvenido/a! ¿Te atreves a desafiar a la mente maestra? Intenta adivinar los 4 dígitos secretos: ");
		String respuestaUsuario = "s";

		// Bucle que realiza la partida siempre que el usuario quiera continuar
		// escribiendo 's' (sí)
		do {
			// Comprobación de la respuesta del usuario
			int contadorIntentos = 10;
			char[] codigoUsuario;
			do {
				String inputUsuario = input.nextLine();
				contadorIntentos--;

				// Eliminación de los posibles espacios que pueda escribir el usuario
				inputUsuario = inputUsuario.replace(" ", "");

				// Conversión del String de usuario a char
				codigoUsuario = inputUsuario.toCharArray();

				int[] codigoUsuarioInt = convertirAEnteros(codigoUsuario);
				int[] codigoSecretoInt = convertirAEnteros(codigoSecreto);

				System.out.println("Dígitos acertados en la posición correcta: "
						+ contarExactos(codigoUsuarioInt, codigoSecretoInt) + "\n" + "Dígitos acertados cercanos: "
						+ contarCercanos(codigoUsuarioInt, codigoSecretoInt));

				if (!usuarioGanador(codigoUsuario, codigoSecreto)) {
					System.out.println("Te quedan " + contadorIntentos + " intentos: ");
				}

			} while (!usuarioGanador(codigoUsuario, codigoSecreto) && usuarioSinIntentos(contadorIntentos));

			// Fin de la partida (usuario ganador)
			if (usuarioGanador(codigoUsuario, codigoSecreto) == true) {
				System.out.println("¡Has ganado! ¿Quieres jugar otra partida? Escribe 's' (sí) o 'n' (no): ");
				respuestaUsuario = input.nextLine();
				finPartida(respuestaUsuario);
			}

			// Fin de la partida (usuario sin intentos restantes)
			else if (usuarioSinIntentos(contadorIntentos) == false) {
				System.out.println(
						"Te has quedado sin intentos. ¿Quieres jugar otra partida? Escribe 's' (sí) o 'n' (no): ");
				respuestaUsuario = input.nextLine();
				finPartida(respuestaUsuario);
			}
		}

		while (respuestaUsuario.equals("s"));
	}

	// Método para convertir a arrays de enteros
	private static int[] convertirAEnteros(char[] arrayCaracteres) {
		int[] arrayEnteros = new int[arrayCaracteres.length];
		for (int i = 0; i < arrayCaracteres.length; i++) {
			String arrayCaracteresString = String.valueOf(arrayCaracteres[i]);
			arrayEnteros[i] = Integer.parseInt(arrayCaracteresString);
		}
		return arrayEnteros;
	}

	// Método para comprobar si el input de usuario es igual que el código secreto
	private static boolean usuarioGanador(char[] codigoUsuario, char[] codigoSecreto) {
		if (Arrays.equals(codigoUsuario, codigoSecreto)) {
			return true;
		} else {
			return false;
		}

	}

	// Método para comprobar el número de intentos restantes del usuario
	private static boolean usuarioSinIntentos(int contadorIntentos) {
		if (contadorIntentos != 0) {
			return true;
		} else {
			return false;
		}

	}

	// Método para contar los números del usuario acertados y en las posiciones
	// exactas
	private static int contarExactos(int[] codigoUsuario, int[] codigoSecreto) {
		int contadorAciertos = 0;
		for (int i = 0; i < codigoUsuario.length; i++) {
			if (codigoUsuario[i] == codigoSecreto[i]) {
				contadorAciertos++;
			}
		}
		return contadorAciertos;
	}

	// Método para contar los números del usuario acertados pero en posiciones
	// incorrectas
	private static int contarCercanos(int[] codigoUsuario, int[] codigoSecreto) {
		int contadorAciertos = 0;
		String codigoSecretoString = Arrays.toString(codigoSecreto);
		for (int i = 0; i < codigoUsuario.length; i++) {
			String digitoUsuario = String.valueOf(codigoUsuario[i]);
			if (codigoSecretoString.contains(digitoUsuario) && codigoUsuario[i] != codigoSecreto[i]) {
				contadorAciertos++;
			}
		}
		return contadorAciertos;
	}

	// Método que detiene la partida si el usuario no quiere continuar jugando
	private static String finPartida(String respuestaUsuario) {
		respuestaUsuario.toLowerCase();
		if (respuestaUsuario.equals("n")) {
			System.out.println("De acuerdo, ¡hasta la próxima partida! :-)");
		}
		return respuestaUsuario;
	}

}
