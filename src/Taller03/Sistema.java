package Taller03;

import java.util.ArrayList;
import java.util.LinkedList;

public class Sistema {

	private ArrayList<Monstruo> monstruos;
	private ArrayList<Jugador> jugadores;
	private ArrayList<Minijuego> minijuegos;
	private LinkedList<ResultadoMinijuego> historial;

	public Sistema() {
		monstruos = new ArrayList<>();
		jugadores = new ArrayList<>();
		minijuegos = new ArrayList<>();
		historial = new LinkedList<>();
		minijuegos.add(new CarreraCementerio());
		minijuegos.add(new CazaCalabazas());
		minijuegos.add(new LanzamientoLapidas());
		minijuegos.add(new SobrevivirAmanecer());
	}

	public ArrayList<Monstruo> getMonstruos() {
		return monstruos;
	}

	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}

	public ArrayList<Minijuego> getMinijuegos() {
		return minijuegos;
	}

	public LinkedList<ResultadoMinijuego> getHistorial() {
		return historial;
	}

	//valida los datos y agrega un monstruo a la lista
	public boolean agregarMonstruo(String[] datos) {
		if (datos == null || datos.length < 1) {
			return false;
		}
		String tipo = datos[0].trim();
		int cantidadEsperada;
		if (tipo.equalsIgnoreCase("Zombi") || tipo.equalsIgnoreCase("Momia")) {
			cantidadEsperada = 6;
		} else if (tipo.equalsIgnoreCase("Vampiro") || tipo.equalsIgnoreCase("HombreLobo")) {
			cantidadEsperada = 7;
		} else {
			return false;
		}
		if (datos.length != cantidadEsperada) {
			return false;
		}

		try {
			int vida = Integer.parseInt(datos[1].trim());
			int velocidad = Integer.parseInt(datos[2].trim());
			int fuerza = Integer.parseInt(datos[3].trim());
			int resistencia = Integer.parseInt(datos[4].trim());
			int atributo1 = Integer.parseInt(datos[5].trim());
			int atributo2 = 0;
			if (cantidadEsperada == 7) {
				atributo2 = Integer.parseInt(datos[6].trim());
			}
			if (!enRango(vida, 1, 200) || !enRango(velocidad, 1, 100)
					|| !enRango(fuerza, 1, 100) || !enRango(resistencia, 1, 100)) {
				return false;
			}

			if (tipo.equalsIgnoreCase("Zombi") && enRango(atributo1, 1, 100)) {
				monstruos.add(new Zombi(vida, velocidad, fuerza, resistencia, atributo1));
			} else if (tipo.equalsIgnoreCase("Vampiro") && enRango(atributo1, 1, 100)
					&& enRango(atributo2, 1, 100)) {
				monstruos.add(new Vampiro(vida, velocidad, fuerza, resistencia, atributo1, atributo2));
			} else if (tipo.equalsIgnoreCase("Momia") && enRango(atributo1, 1, 300)) {
				monstruos.add(new Momia(vida, velocidad, fuerza, resistencia, atributo1));
			} else if (tipo.equalsIgnoreCase("HombreLobo") && enRango(atributo1, 1, 100)
					&& enRango(atributo2, 1, 4)) {
				monstruos.add(new HombreLobo(vida, velocidad, fuerza, resistencia, atributo1, atributo2));
			} else {
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	//valida los datos y agrega un jugador a la lista
	public boolean agregarJugador(String[] datos) {
		if (datos == null || datos.length != 2) {
			return false;
		}
		String nombre = datos[0].trim();
		if (nombre.equalsIgnoreCase("") || buscarJugador(nombre) != null) {
			return false;
		}
		try {
			int puntaje = Integer.parseInt(datos[1].trim());
			if (puntaje < 0) {
				return false;
			}
			jugadores.add(new Jugador(nombre, puntaje));
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	//busca un jugador por su nombre
	public Jugador buscarJugador(String nombre) {
		for (int i = 0; i < jugadores.size(); i++) {
			if (jugadores.get(i).getNombre().equalsIgnoreCase(nombre.trim())) {
				return jugadores.get(i);
			}
		}
		return null;
	}

	private boolean enRango(int numero, int minimo, int maximo) {
		return numero >= minimo && numero <= maximo;
	}
}
