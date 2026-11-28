package Taller03;
//CATALINA FICA 21.779.663-6
//AlVARO ELGUETA 
import java.util.ArrayList;
import java.util.LinkedList;

public class Sistema {

	private ArrayList<Monstruo> monstruos;
	private ArrayList<Jugador> jugadores;
	private ArrayList<Minijuego> minijuegos;
	private LinkedList<ResultadoMinijuego> historial;
	private ArrayList<Monstruo> monstruosUsados;
	private ArrayList<Minijuego> minijuegosJugados;
	private String nombreJugadorActual;
	private Jugador jugadorActual;
	private int puntajeSesion;

	public Sistema() {
		monstruos = new ArrayList<>();
		jugadores = new ArrayList<>();
		minijuegos = new ArrayList<>();
		historial = new LinkedList<>();
		monstruosUsados = new ArrayList<>();
		minijuegosJugados = new ArrayList<>();
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

	public String getNombreJugadorActual() {
		return nombreJugadorActual;
	}

	public Jugador getJugadorActual() {
		return jugadorActual;
	}

	public int getPuntajeSesion() {
		return puntajeSesion;
	}

	//registra el puntaje obtenido al finalizar la sesion
	public String finalizarSesion() {
		if (historial.isEmpty()) {
			return "No se jugaron minijuegos, por lo tanto no se modifico el record.";
		}
		if (jugadorActual == null) {
			jugadorActual = new Jugador(nombreJugadorActual, puntajeSesion);
			jugadores.add(jugadorActual);
			return "Jugador registrado con " + puntajeSesion + " puntos.";
		}
		if (puntajeSesion > jugadorActual.getPuntajeMaximo()) {
			int recordAnterior = jugadorActual.getPuntajeMaximo();
			jugadorActual.setPuntajeMaximo(puntajeSesion);
			return "Nuevo record: " + puntajeSesion + " puntos. Record anterior: "
					+ recordAnterior + " puntos.";
		}
		return "No se supero el record de " + jugadorActual.getPuntajeMaximo() + " puntos.";
	}

	//inicia una sesion y reinicia sus datos
	public boolean iniciarSesion(String nombre) {
		if (nombre == null || nombre.trim().equalsIgnoreCase("") || nombre.contains(";")) {
			return false;
		}
		nombreJugadorActual = nombre.trim();
		jugadorActual = buscarJugador(nombreJugadorActual);
		puntajeSesion = 0;
		historial.clear();
		monstruosUsados.clear();
		minijuegosJugados.clear();
		return true;
	}

	public boolean monstruoUsado(Monstruo monstruo) {
		return monstruosUsados.contains(monstruo);
	}

	public boolean minijuegoJugado(Minijuego minijuego) {
		return minijuegosJugados.contains(minijuego);
	}

	//retorna los monstruos que aun no se han usado
	public ArrayList<Monstruo> obtenerMonstruosDisponibles() {
		ArrayList<Monstruo> disponibles = new ArrayList<>();
		for (int i = 0; i < monstruos.size(); i++) {
			if (!monstruoUsado(monstruos.get(i))) {
				disponibles.add(monstruos.get(i));
			}
		}
		return disponibles;
	}

	public boolean hayMinijuegosPendientes() {
		return minijuegosJugados.size() < minijuegos.size();
	}

	//busca el resultado de un minijuego dentro de la sesion
	public ResultadoMinijuego buscarResultado(Minijuego minijuego) {
		for (int i = 0; i < historial.size(); i++) {
			if (historial.get(i).getMinijuego() == minijuego) {
				return historial.get(i);
			}
		}
		return null;
	}

	public boolean habilidadEfectiva(Minijuego minijuego, String habilidad) {
		return minijuego.getHabilidadPremiada().equalsIgnoreCase(habilidad);
	}

	//calcula y registra el resultado de un minijuego
	public ResultadoMinijuego jugarMinijuego(Minijuego minijuego, Monstruo monstruo,
			String habilidad) {
		if (minijuego == null || monstruo == null || habilidad == null
				|| minijuegoJugado(minijuego) || monstruoUsado(monstruo)) {
			return null;
		}
		double potencial = minijuego.calcularPotencial(monstruo);
		double multiplicador = 1.0;
		if (habilidadEfectiva(minijuego, habilidad)) {
			multiplicador = 1.5;
		}
		int puntaje = (int) Math.round(potencial * 10 * multiplicador);
		ResultadoMinijuego resultado = new ResultadoMinijuego(minijuego, monstruo,
				habilidad, puntaje);
		historial.add(resultado);
		minijuegosJugados.add(minijuego);
		monstruosUsados.add(monstruo);
		puntajeSesion += puntaje;
		return resultado;
	}

	//retorna una copia de los jugadores ordenada por puntaje y nombre
	public ArrayList<Jugador> obtenerRanking() {
		ArrayList<Jugador> ranking = new ArrayList<>();
		for (int i = 0; i < jugadores.size(); i++) {
			ranking.add(jugadores.get(i));
		}
		for (int i = 0; i < ranking.size() - 1; i++) {
			for (int j = 0; j < ranking.size() - 1 - i; j++) {
				Jugador actual = ranking.get(j);
				Jugador siguiente = ranking.get(j + 1);
				boolean cambiar = actual.getPuntajeMaximo() < siguiente.getPuntajeMaximo();
				if (actual.getPuntajeMaximo() == siguiente.getPuntajeMaximo()
						&& actual.getNombre().compareToIgnoreCase(siguiente.getNombre()) > 0) {
					cambiar = true;
				}
				if (cambiar) {
					ranking.set(j, siguiente);
					ranking.set(j + 1, actual);
				}
			}
		}
		return ranking;
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
