package Taller03;

public class Jugador {

	private String nombre;
	private int puntajeMaximo;

	public Jugador(String nombre, int puntajeMaximo) {
		this.nombre = nombre;
		this.puntajeMaximo = puntajeMaximo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntajeMaximo() {
		return puntajeMaximo;
	}

	public void setPuntajeMaximo(int puntajeMaximo) {
		this.puntajeMaximo = puntajeMaximo;
	}
}
