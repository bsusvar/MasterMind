package masterMind;

import java.util.Arrays;

import java.util.Scanner;

public class MasterMind {

	public static void main(String[] args) {

		String respuestaUsuario = "s";
		// Bucle que realiza la partida siempre que el usuario quiera continuar
		// escribiendo 's' (sí)

		System.out.println("¡Bienvenido/a! ¿Te atreves a desafiar a la mente maestra?");
		int longitudCodigo;
		do {
			System.out.println("Indica la longitud del código secreto (mínimo de 4 y máximo de 9 dígitos): ");
			Scanner input = new Scanner(System.in);
			longitudCodigo = input.nextInt();
		} while (longitudCodigo < 4 || longitudCodigo > 9);

		// Confirmación de longitud del código
		System.out.println("Todo listo. ¡Comenzamos!: ");
		do {

			char[] codigoSecreto = mezclarNumeros(longitudCodigo);

			// Introducción al juego

			// Comprobación de la respuesta del usuario
			System.out.println("Intenta adivinar los " + longitudCodigo + " dígitos secretos: ");
			int contadorIntentos = 10;
			char[] codigoUsuario;
			do {
				Scanner input = new Scanner(System.in);
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

				if (!usuarioGanador(codigoUsuario, codigoSecreto) && usuarioSinIntentos(contadorIntentos)) {
					System.out.println("Te quedan " + contadorIntentos + " intentos: ");
				}

			} while (!usuarioGanador(codigoUsuario, codigoSecreto) && usuarioSinIntentos(contadorIntentos));

			// Fin de la partida (usuario ganador)
			respuestaUsuario = generarFinPartida(codigoUsuario, codigoSecreto, contadorIntentos);
		}

		while (respuestaUsuario.equals("s"));
	}

	// Método para mezclar los números

	private static char[] mezclarNumeros(int longitudCodigo) {

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
		char[] codigoSecreto = Arrays.copyOfRange(codigo, 0, longitudCodigo);
		// System.out.println(codigoSecreto);

		return codigoSecreto;
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

	// Método que genera el fin de partida si el usuario gana o si se ha quedado sin
	// intentos
	private static String generarFinPartida(char[] codigoUsuario, char[] codigoSecreto, int contadorIntentos) {
		Scanner input = new Scanner(System.in);
		// Fin de la partida (usuario ganador)
		if (usuarioGanador(codigoUsuario, codigoSecreto) == true) {
			System.out.println("¡Has ganado! ");
		}

		// Fin de la partida (usuario sin intentos restantes)
		else if (usuarioSinIntentos(contadorIntentos) == false) {
			String codigoSolucion = new String(codigoSecreto);
			System.out.println("Te has quedado sin intentos. La combinación correcta era " + codigoSolucion + ".");
		}
		System.out.println("¿Quieres jugar otra partida? Escribe 's' (sí) o 'n' (no): ");
		String respuestaUsuario = input.nextLine();
		respuestaUsuario.toLowerCase();
		if (respuestaUsuario.equals("n")) {
			System.out.println("Vale, ¡hasta la próxima partida! :-)");
		}
		return respuestaUsuario;
	}

}