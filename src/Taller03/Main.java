// Integrante1: Alvaro Elgueta - Rut: 21.806.097-8 - Carrera: ICCI
// Integrante2: Catalina Fica - Rut: 21.779.663-6 - Carrera: ICCI

package Taller03;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	private static Sistema sistema = new Sistema();
	private static Scanner s;

	public static void main(String[] args) {
		System.out.println("===== SPOOKY SEASON =====");
		if (!cargarMonstruos()) {
			System.out.println("No existen monstruos validos para iniciar el programa");
			return;
		}
		cargarRegistros();
		System.out.println("Monstruos cargados: " + sistema.getMonstruos().size());
		System.out.println("Jugadores cargados: " + sistema.getJugadores().size() + "\r\n");
		s = new Scanner(System.in);
		boolean continuar = true;
		while (continuar) {
			iniciarSesion();
			continuar = menuPrincipal();
		}
		s.close();
	}

	//solicita un nombre valido e inicia la sesion
	private static void iniciarSesion() {
		while (true) {
			System.out.print("Nombre de usuario: ");
			String nombre = s.nextLine();
			if (sistema.iniciarSesion(nombre)) {
				if (sistema.getJugadorActual() == null) {
					System.out.println("Bienvenido, " + sistema.getNombreJugadorActual()
							+ ". Eres un jugador nuevo.\r\n");
				} else {
					System.out.println("Bienvenido de vuelta, " + sistema.getNombreJugadorActual()
							+ ". Tu record actual es de "
							+ sistema.getJugadorActual().getPuntajeMaximo() + " puntos.\r\n");
				}
				return;
			}
			System.out.println("El nombre no puede estar vacio ni contener ;");
		}
	}

	//se encarga de mostrar el menu principal
	private static boolean menuPrincipal() {
		while (true) {
			System.out.println("===== MENU PRINCIPAL =====\r\n"
					+ "1) Jugar un minijuego\r\n"
					+ "2) Ver catalogo de monstruos\r\n"
					+ "3) Ver historial de la sesion\r\n"
					+ "4) Ver ranking de jugadores\r\n"
					+ "5) Terminar sesion\r\n"
					+ "6) Salir\r\n");
			int opcion = leerOpcion();
			switch (opcion) {
			case 1: {
				jugarMinijuego();
				break;
			}
			case 2: {
				mostrarCatalogo();
				break;
			}
			case 3: {
				mostrarHistorial();
				break;
			}
			case 4: {
				mostrarRanking();
				break;
			}
			case 5: {
				terminarSesion();
				return true;
			}
			case 6: {
				terminarSesion();
				System.out.println("Hasta luego...");
				return false;
			}
			default: {
				System.out.println("Debe ingresar una opcion entre 1 y 6\r\n");
				break;
			}
			}
		}
	}

	//termina la sesion y guarda los registros
	private static void terminarSesion() {
		System.out.println("Sesion terminada. Puntaje total: "
				+ sistema.getPuntajeSesion() + " puntos.");
		System.out.println(sistema.finalizarSesion());
		if (guardarRegistros()) {
			System.out.println("Registros guardados correctamente\r\n");
		} else {
			System.out.println("No se pudieron guardar los registros\r\n");
		}
	}

	//se encarga del flujo para jugar un minijuego
	private static void jugarMinijuego() {
		if (!sistema.hayMinijuegosPendientes()) {
			System.out.println("Ya se jugaron todos los minijuegos de esta sesion\r\n");
			return;
		}
		Minijuego minijuego = seleccionarMinijuego();
		if (minijuego == null) {
			return;
		}
		ArrayList<Monstruo> disponibles = sistema.obtenerMonstruosDisponibles();
		if (disponibles.isEmpty()) {
			System.out.println("No quedan monstruos disponibles en esta sesion\r\n");
			return;
		}
		Monstruo monstruo = seleccionarMonstruo(disponibles);
		if (monstruo == null) {
			return;
		}

		double potencial = minijuego.calcularPotencial(monstruo);
		System.out.println("\r\nTe transformaste en " + monstruo.getTipo());
		System.out.println(minijuego.getDescripcion());
		System.out.println("Potencial: " + redondear(potencial));
		String habilidad = seleccionarHabilidad(monstruo);
		if (habilidad == null) {
			return;
		}

		if (!habilidad.equalsIgnoreCase("Ninguna")) {
			System.out.println(usarHabilidad(monstruo, habilidad));
			if (sistema.habilidadEfectiva(minijuego, habilidad)) {
				System.out.println("La habilidad fue efectiva. Multiplicador: 1.5");
			} else {
				System.out.println("La habilidad no tuvo efecto. Multiplicador: 1.0");
			}
		} else {
			System.out.println("No se utilizo ninguna habilidad. Multiplicador: 1.0");
		}

		ResultadoMinijuego resultado = sistema.jugarMinijuego(minijuego, monstruo, habilidad);
		if (resultado != null) {
			System.out.println("Puntaje obtenido: " + resultado.getPuntaje() + " puntos\r\n");
		}
	}

	//muestra los minijuegos y retorna el seleccionado
	private static Minijuego seleccionarMinijuego() {
		while (true) {
			System.out.println("\r\n===== MINIJUEGOS =====");
			for (int i = 0; i < sistema.getMinijuegos().size(); i++) {
				Minijuego minijuego = sistema.getMinijuegos().get(i);
				ResultadoMinijuego resultado = sistema.buscarResultado(minijuego);
				String estado = "PENDIENTE";
				if (resultado != null) {
					estado = "JUGADO - " + resultado.getPuntaje() + " puntos";
				}
				System.out.println((i + 1) + ") " + minijuego.getNombre() + " [" + estado + "]");
			}
			System.out.println("0) Volver");
			int opcion = leerOpcion();
			if (opcion == 0) {
				return null;
			}
			if (opcion >= 1 && opcion <= sistema.getMinijuegos().size()) {
				Minijuego seleccionado = sistema.getMinijuegos().get(opcion - 1);
				if (!sistema.minijuegoJugado(seleccionado)) {
					return seleccionado;
				}
				System.out.println("Ese minijuego ya fue jugado en esta sesion");
			} else {
				System.out.println("Debe seleccionar un minijuego valido");
			}
		}
	}

	//muestra los monstruos disponibles y retorna el seleccionado
	private static Monstruo seleccionarMonstruo(ArrayList<Monstruo> disponibles) {
		while (true) {
			System.out.println("\r\n===== MONSTRUOS DISPONIBLES =====");
			for (int i = 0; i < disponibles.size(); i++) {
				System.out.println((i + 1) + ") " + disponibles.get(i).getInformacion());
			}
			System.out.println("0) Volver");
			int opcion = leerOpcion();
			if (opcion == 0) {
				return null;
			}
			if (opcion >= 1 && opcion <= disponibles.size()) {
				return disponibles.get(opcion - 1);
			}
			System.out.println("Debe seleccionar un monstruo valido");
		}
	}

	//muestra las habilidades y retorna la seleccionada
	private static String seleccionarHabilidad(Monstruo monstruo) {
		ArrayList<String> habilidades = new ArrayList<>();
		if (monstruo instanceof Mordedor) {
			habilidades.add("Morder");
		}
		if (monstruo instanceof Volador) {
			habilidades.add("Volar");
		}
		if (monstruo instanceof Regenerable) {
			habilidades.add("Regenerarse");
		}
		if (monstruo instanceof Furioso) {
			habilidades.add("Enfurecerse");
		}

		while (true) {
			System.out.println("\r\n===== HABILIDADES =====");
			for (int i = 0; i < habilidades.size(); i++) {
				System.out.println((i + 1) + ") " + habilidades.get(i));
			}
			System.out.println((habilidades.size() + 1) + ") No usar habilidad");
			System.out.println("0) Volver");
			int opcion = leerOpcion();
			if (opcion == 0) {
				return null;
			}
			if (opcion >= 1 && opcion <= habilidades.size()) {
				return habilidades.get(opcion - 1);
			}
			if (opcion == habilidades.size() + 1) {
				return "Ninguna";
			}
			System.out.println("Debe seleccionar una habilidad valida");
		}
	}

	//ejecuta el metodo de la interfaz seleccionada
	private static String usarHabilidad(Monstruo monstruo, String habilidad) {
		if (habilidad.equalsIgnoreCase("Morder")) {
			return ((Mordedor) monstruo).morder();
		} else if (habilidad.equalsIgnoreCase("Volar")) {
			return ((Volador) monstruo).volar();
		} else if (habilidad.equalsIgnoreCase("Regenerarse")) {
			return ((Regenerable) monstruo).regenerarse();
		} else if (habilidad.equalsIgnoreCase("Enfurecerse")) {
			return ((Furioso) monstruo).enfurecerse();
		}
		return "";
	}

	private static double redondear(double numero) {
		return Math.round(numero * 100.0) / 100.0;
	}

	//muestra todos los monstruos y su estado en la sesion
	private static void mostrarCatalogo() {
		System.out.println("\r\n===== CATALOGO DE MONSTRUOS =====");
		for (int i = 0; i < sistema.getMonstruos().size(); i++) {
			Monstruo monstruo = sistema.getMonstruos().get(i);
			String estado = "DISPONIBLE";
			if (sistema.monstruoUsado(monstruo)) {
				estado = "USADO";
			}
			System.out.println((i + 1) + ") " + monstruo.getInformacion()
					+ " | Habilidades: " + obtenerHabilidades(monstruo) + " | " + estado);
		}
		System.out.println();
	}

	//retorna las habilidades que tiene un monstruo
	private static String obtenerHabilidades(Monstruo monstruo) {
		String habilidades = "";
		if (monstruo instanceof Mordedor) {
			habilidades += "Morder";
		}
		if (monstruo instanceof Volador) {
			habilidades += separarHabilidad(habilidades) + "Volar";
		}
		if (monstruo instanceof Regenerable) {
			habilidades += separarHabilidad(habilidades) + "Regenerarse";
		}
		if (monstruo instanceof Furioso) {
			habilidades += separarHabilidad(habilidades) + "Enfurecerse";
		}
		return habilidades;
	}

	private static String separarHabilidad(String habilidades) {
		if (habilidades.equalsIgnoreCase("")) {
			return "";
		}
		return ", ";
	}

	//muestra los resultados de la sesion actual
	private static void mostrarHistorial() {
		System.out.println("\r\n===== HISTORIAL DE LA SESION =====");
		if (sistema.getHistorial().isEmpty()) {
			System.out.println("Aun no se ha jugado ningun minijuego\r\n");
			return;
		}
		for (int i = 0; i < sistema.getHistorial().size(); i++) {
			ResultadoMinijuego resultado = sistema.getHistorial().get(i);
			System.out.println((i + 1) + ") " + resultado.getMinijuego().getNombre()
					+ " | " + resultado.getMonstruo().getTipo() + " | Habilidad: "
					+ resultado.getHabilidad() + " | " + resultado.getPuntaje() + " puntos");
		}
		System.out.println("Puntaje acumulado: " + sistema.getPuntajeSesion() + " puntos\r\n");
	}

	//muestra el ranking ordenado por puntaje y nombre
	private static void mostrarRanking() {
		ArrayList<Jugador> ranking = sistema.obtenerRanking();
		System.out.println("\r\n===== RANKING DE JUGADORES =====");
		if (ranking.isEmpty()) {
			System.out.println("No hay jugadores registrados\r\n");
			return;
		}
		for (int i = 0; i < ranking.size(); i++) {
			System.out.println((i + 1) + ") " + ranking.get(i).getNombre() + " - "
					+ ranking.get(i).getPuntajeMaximo() + " puntos");
		}
		System.out.println();
	}

	//lee una opcion numerica
	private static int leerOpcion() {
		try {
			System.out.print("Ingrese opcion: ");
			return Integer.parseInt(s.nextLine().trim());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	//se encarga de cargar los monstruos desde el archivo
	private static boolean cargarMonstruos() {
		try {
			Scanner lector = new Scanner(new File("Monstruos.txt"));
			int numeroLinea = 0;
			while (lector.hasNextLine()) {
				numeroLinea++;
				String linea = lector.nextLine();
				String[] datos = linea.split(";", -1);
				if (!sistema.agregarMonstruo(datos)) {
					System.out.println("Linea " + numeroLinea + " descartada de Monstruos.txt: " + linea);
				}
			}
			lector.close();
			return !sistema.getMonstruos().isEmpty();
		} catch (FileNotFoundException e) {
			System.out.println("No se encontro Monstruos.txt");
			return false;
		}
	}

	//se encarga de cargar los jugadores desde el archivo
	private static void cargarRegistros() {
		try {
			Scanner lector = new Scanner(new File("Registros.txt"));
			int numeroLinea = 0;
			while (lector.hasNextLine()) {
				numeroLinea++;
				String linea = lector.nextLine();
				String[] datos = linea.split(";", -1);
				if (!sistema.agregarJugador(datos)) {
					System.out.println("Linea " + numeroLinea + " descartada de Registros.txt: " + linea);
				}
			}
			lector.close();
		} catch (FileNotFoundException e) {
			System.out.println("No se encontro Registros.txt, se creara al guardar");
		}
	}

	//reescribe el archivo con los records actuales
	private static boolean guardarRegistros() {
		try {
			BufferedWriter escritor = new BufferedWriter(new FileWriter("Registros.txt"));
			for (int i = 0; i < sistema.getJugadores().size(); i++) {
				Jugador jugador = sistema.getJugadores().get(i);
				escritor.write(jugador.getNombre() + ";" + jugador.getPuntajeMaximo());
				escritor.newLine();
			}
			escritor.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
