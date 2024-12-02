package masterMind;

import java.util.Arrays;

import java.util.Scanner;

public class MasterMind {

	public static void main(String[] args) {

		// Inicio de la partida
		System.out.println("¡Bienvenido/a! ¿Te atreves a desafiar a la mente maestra?");

		// Bucle que realiza la partida siempre que el usuario quiera continuar
		// escribiendo 's' (sí)
		String respuestaUsuario = "s";
		do {

			// Establecimiento de la longitud del código por el usuario
			Scanner input = new Scanner(System.in);
			int longitudCodigo;

			do {
				System.out.println("Indica la longitud del código secreto (mínimo de 4 y máximo de 10 dígitos): ");
				longitudCodigo = pedirDigitosUsuario();
			} while (longitudCodigo < 4 || longitudCodigo > 10);

			// Establecimiento del número de intentos por el usuario
			int contadorIntentos;
			do {
				System.out.println("Indica cuántos intentos quieres tener (máximo 10): ");
				contadorIntentos = pedirDigitosUsuario();
			} while (contadorIntentos < 1 || contadorIntentos > 10);

			// Confirmación de establecimiento de ambos parámetros
			System.out.println("Todo listo. ¡Comenzamos!: ");

			// Uso del método que combina los números que se van a jugar
			char[] codigoSecreto = mezclarNumeros(longitudCodigo);

			// Comprobación de la respuesta del usuario
			System.out.println("Intenta adivinar los " + longitudCodigo + " dígitos secretos. ");
			input = new Scanner(System.in);

			char[] codigoUsuario;

			// Bucle que crea intentos siempre que el usuario no haya ganado o se haya
			// quedado sin intentos
			do {
				String inputUsuario;
				do {
					System.out.println("Escribe tu combinación: ");
					inputUsuario = input.nextLine();
				} while (inputUsuario.length() < longitudCodigo || inputUsuario.length() > longitudCodigo
						|| comprobarCadenaSoloDigitos(inputUsuario) == false);
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
					System.out.println("Te quedan " + contadorIntentos + " intentos. ");
				}

			} while (!usuarioGanador(codigoUsuario, codigoSecreto) && usuarioSinIntentos(contadorIntentos));

			// Fin de la partida (usuario ganador)
			respuestaUsuario = generarFinPartida(codigoUsuario, codigoSecreto, contadorIntentos);
		}

		while (respuestaUsuario.toLowerCase().equals("s"));
	}

	// Método que recorre la respuesta del usuario y devuelve un resultado booleano
	private static boolean comprobarCadenaSoloDigitos(String respuestaUsuario) {
		for (int i = 0; i < respuestaUsuario.length(); i++) {
			if (respuestaUsuario.charAt(i) < 48 || respuestaUsuario.charAt(i) > 57) {
				return false;
			}
		}
		return true;
	}

	// Método para comprobar si el usuario ha introducido solo números y no letras
	// (a partir del método booleano anterior)
	private static int pedirDigitosUsuario() {
		Scanner input = new Scanner(System.in);
		String respuestaUsuarioString = input.nextLine();
		while (comprobarCadenaSoloDigitos(respuestaUsuarioString) == false) {
			System.out.println("Tu respuesta (" + respuestaUsuarioString
					+ ") no es válida porque no contiene únicamente números. Inténtalo de nuevo: ");
			respuestaUsuarioString = input.nextLine();
		}
		int respuestaUsuarioInt = Integer.parseInt(respuestaUsuarioString);
		return respuestaUsuarioInt;
	}

	// Método para mezclar los números
	private static char[] mezclarNumeros(int longitudCodigo) {

		// Creación de un String con los números
		String lista = ("0123456789");
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

		String respuestaUsuario;
		do {
			System.out.println("¿Quieres jugar otra partida? Escribe 's' (sí) o 'n' (no): ");
			respuestaUsuario = input.nextLine();
		} while (!respuestaUsuario.toLowerCase().equals("s") && !respuestaUsuario.toLowerCase().equals("n"));
		if (respuestaUsuario.equals("n")) {
			System.out.println("Vale, ¡hasta la próxima partida! :-)");
		}
		return respuestaUsuario;
	}

}