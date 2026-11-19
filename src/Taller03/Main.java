// Integrante1: Alvaro Elgueta - Rut: 21.806.097-8 - Carrera: ICCI
// Integrante2: Catalina Fica - Rut: 21.779.663-6 - Carrera: ICCI

package Taller03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	private static Sistema sistema = new Sistema();

	public static void main(String[] args) {
		System.out.println("===== SPOOKY SEASON =====");
		if (!cargarMonstruos()) {
			System.out.println("No existen monstruos validos para iniciar el programa");
			return;
		}
		cargarRegistros();
		System.out.println("Monstruos cargados: " + sistema.getMonstruos().size());
		System.out.println("Jugadores cargados: " + sistema.getJugadores().size());
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
}
